package com.jxdinfo.salary.PermissionManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.permission.service.IPermissionService;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.service.IStaffService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.jxdinfo.hussar.core.log.LogObjectHolder;
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.PermissionManagement.service.IWhiteListService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 薪资权限管理--白名单维护控制器
 *
 * @author 王明凯
 * @Date 2020-06-16 14:45:03
 */
@Controller
@RequestMapping("/whiteList")
public class WhiteListController extends BaseController {

    private String PREFIX = "/salary/PermissionManagement/whiteList/";

    @Autowired
    private IWhiteListService whiteListService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IStaffService staffService;




    /**
     * 跳转到薪资权限管理--白名单维护首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/whiteList/view", type = BussinessLogType.QUERY, value = "白名单维护")
    @RequiresPermissions("whiteList:view")
    public String index() {
        return PREFIX + "whiteList.html";
    }

    /**
     *  分别 获取白名单列表里面的值 最终结果是distinct的形式 去掉重复的的项
     *
     *  修改 将staffID和name的list注释掉了 如果想要继续展示staffId和staffName的下拉框 取消注释即可
     */
    @ResponseBody
    @RequestMapping("/select")
    public Map<String,Object> getSelection(){
        Map<String,Object> map = new HashMap<>();
//        List<Integer> staffIdList = whiteListService.selectStaffId();
//        List<String> staffNameList = whiteListService.selectStaffName();
//        map.put("staffIdList", staffIdList);
//        map.put("staffNameList", staffNameList);
        Wrapper<WhiteList> wrapper = new EntityWrapper<>();
        wrapper.where("DEPARTURE_TIME is null");

        // 获取当前登录账号的ID
        try {
            ShiroUser user = ShiroKit.getUser();
            Long staffId = Long.parseLong(user.getAccount());
            //根据当前ID返回它能查询的内容
            Wrapper<Staff> s_w = new EntityWrapper<>();
            s_w.where("STAFF_ID = {0}",staffId);
            Staff staff = staffService.selectOne(s_w);
            if (staff.getDepartment().getDepartmentName().equals("财务部")){
                //如果是财务部的部长 则只能查看财务部权限情况 即白名单里拥有权限的员工是财务部的员工
                // 财务部的departmentId = 12
                wrapper.where("c.DEPARTMENT_ID = {0}", 12);
                // 财务部的
            }else {
                // 不是财务部长
                if (staff.getDepartment().getDepartmentName().equals("人力资源部")){
                    //如果是人力资源部的部长 则只能查看人力资源部权限情况
                    // 人力资源部的departmentId = 10
                    wrapper.where("c.DEPARTMENT_ID = {0}", 10);
                }else {
                    //都不是的话就是超级管理员了 可以查看全部的
                }
            }

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }


        List<String> departmentNameList = whiteListService.selectDepartmentName(wrapper);
        List<String> permissionNameList = whiteListService.selectPermissionName(wrapper);

        map.put("departmentNameList", departmentNameList);
        map.put("permissionNameList", permissionNameList);
        return map;
    }

    /**
     *  得到所有的部门信息 和权限信息
     *  getAllDepartmentAndPermission
     */
    @ResponseBody
    @RequestMapping("getAllDepartmentAndPermission")
    public Object getAllDepartmentAndPermission(
            @RequestParam(value = "modify_id",required = false,defaultValue = "") String modify_id){

        Map<String,Object> map = new HashMap<>();

        if (!modify_id.equals("")){
            // 如果输入了要修改的员工的id
             map = (Map<String,Object>)getStaffById(Integer.parseInt(modify_id));
        }else {
            // 如果没有输入 则是在初始化 则根据当前登录用户的身份进行展示
            Wrapper<Department> wrapper = new EntityWrapper<>();
            List<Department> departmentList = departmentService.selectList(wrapper);
            Wrapper<Permission> wrapper2 = new EntityWrapper<>();
            // 获取当前登录账号的ID
            try {
                ShiroUser user = ShiroKit.getUser();
                Long staffId = Long.parseLong(user.getAccount());
                //根据当前ID返回它能查询的内容
                Wrapper<Staff> s_w = new EntityWrapper<>();
                s_w.where("STAFF_ID = {0}",staffId);
                Staff staff = staffService.selectOne(s_w);
                if (staff.getDepartment().getDepartmentName().equals("财务部")){
                    //如果是财务部的部长 则只能查看财务部权限情况
                    // 财务部的的权限ID是00-03 其中03管理权是部长才有的 只有超级管理员可以设置
                    wrapper2.where("PERMISSION_ID <",3);
                }else {
                    // 不是财务部长
                    if (staff.getDepartment().getDepartmentName().equals("人力资源部")){
                        //如果是人力资源部的部长 则只能查看人力资源部权限情况
                        // 人力资源部的权限ID是 10-16 其中16管理权是部长才有的 只有超级管理员可以设置
                        wrapper2.where("PERMISSION_ID >={0} and PERMISSION_ID < {1}",10,16);
                    }else {
                        //都不是的话就是超级管理员了 可以查看全部的
                    }
                }

            }catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
            List<Permission> permissionList = permissionService.selectList(wrapper2);
            map.put("permissionList", permissionList);
            map.put("departmentList", departmentList);
        }

        return map;
    }




    /**
     *  得到所有的部门信息 和权限信息
     *  getAllDepartmentAndPermission
     */
    @ResponseBody
    @RequestMapping("getAllStaff")
    public Map<String,Object> getAllStaff(){
        Map<String,Object> map = new HashMap<>();
        Wrapper<Staff> wrapper = new EntityWrapper<>();
        wrapper.where("DEPARTURE_TIME is null");

        // 获取当前登录账号的ID
        try {
            ShiroUser user = ShiroKit.getUser();
            Long staffId = Long.parseLong(user.getAccount());
            //根据当前ID返回它能查询的内容
            Wrapper<Staff> s_w = new EntityWrapper<>();
            s_w.where("STAFF_ID = {0}",staffId);
            Staff staff = staffService.selectOne(s_w);
            if (staff.getDepartment().getDepartmentName().equals("财务部")){
                //如果是财务部的部长 则只能查看财务人员
                // 财务部的DEPARTMENT_ID是12
                wrapper.where("DEPARTMENT_ID = {0}",12);
            }else {
                // 不是财务部长
                if (staff.getDepartment().getDepartmentName().equals("人力资源部")){
                    //如果是人力资源部的部长 则只能查看人力资源部人员
                    // 人力资源部的DEPARTMENT_ID是10
                    wrapper.where("DEPARTMENT_ID = {0}",10);
                }else {
                    //都不是的话就是超级管理员了 可以得到财务部和人力资源部所有的员工 以及其他部门的部长
                    wrapper.where("(DEPARTMENT_ID = {0} or DEPARTMENT_ID = {1} or POSITION_ID = {2} or POSITION_ID = {3})", 10,12,1,2);

                }
            }

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        List<Staff> staffList = staffService.selectList(wrapper);
        map.put("staffList", staffList);
        return map;
    }

    /**
     * 根据员工工号得到员工姓名 该员工身份可以拥有的权限列表 和管理的部门列表
     * 返回值是map对象 包含 staff  permissionList 和 departmentList
     */
    @ResponseBody
    @RequestMapping("getStaffById")
    public Object getStaffById(Integer staffId){
        Map<String,Object> map = new HashMap<>();
        Wrapper<Staff> wrapper = new EntityWrapper<>();
        wrapper.where("STAFF_ID = {0}",staffId);
        Staff staff = staffService.selectOne(wrapper);
        String s_dname = staff.getDepartment().getDepartmentName();
        String s_pname = staff.getPosition().getPositionName();
        Wrapper<Permission> pw = new EntityWrapper<>();
        Wrapper<Department> dw = new EntityWrapper<>();
        if (s_dname.equals("财务部")){
            if (s_pname.equals("部门经理")){
                // 如果该员工是财务部部长的话 权限ID 00-03
                pw.where("PERMISSION_ID >={0} and PERMISSION_ID <={1}",0,3);
            }else {
                // 如果该员工是财务部员工的话 权限ID 00-02
                pw.where("PERMISSION_ID >={0} and PERMISSION_ID <={1}",0,2);
            }

        }else {
            if (s_dname.equals("人力资源部")){
                if (s_pname.equals("部门经理")){
                    // 如果该员工是人力资源部部长的话 权限ID 10-16
                    pw.where("PERMISSION_ID >={0} and PERMISSION_ID <={1}",10,16);
                }else {
                    // 如果该员工是人力资源部员工的话 权限ID 10-15
                    pw.where("PERMISSION_ID >={0} and PERMISSION_ID <={1}",10,15);
                }

            }else {
                if (s_dname.equals("高管层")){
                    // 如果是高管层 有所有部门的所有权限
                }else {
                    // 其他部的部长 只有本部门的 查看工资的权限id = 0
                    dw.where("DEPARTMENT_NAME = {0}", s_dname);
                    pw.where("PERMISSION_id = {0}", 0);
                }
            }
        }

        List<Permission> permissionList = permissionService.selectList(pw);
        List<Department> departmentList = departmentService.selectList(dw);
        map.put("permissionList", permissionList);
        map.put("departmentList", departmentList);
        map.put("staff", staff);
        return map;
    }


    /**
     * 根据员工姓名得到员工工号列表 如果列表中只有一个员工的话 则也得到该员工的  能够管理的部门 和 权限 列表
     * 因为可能会有同名的员工
     */
    @ResponseBody
    @RequestMapping("getStaffsByName")
    public Object getStaffsByName(String staffName){
        Map<String,Object> map = new HashMap<>();
        Wrapper<Staff> wrapper = new EntityWrapper<>();
        wrapper.where("STAFF_NAME = {0}",staffName);
        wrapper.where("DEPARTURE_TIME is null");

        // 获取当前登录账号的ID
        try {
            ShiroUser user = ShiroKit.getUser();
            Long staffId = Long.parseLong(user.getAccount());
            //根据当前ID返回它能查询的内容
            Wrapper<Staff> s_w = new EntityWrapper<>();
            s_w.where("STAFF_ID = {0}",staffId);
            Staff staff = staffService.selectOne(s_w);
            if (staff.getDepartment().getDepartmentName().equals("财务部")){
                //如果是财务部的部长 则只能查看财务人员
                // 财务部的DEPARTMENT_ID是12
                wrapper.where("DEPARTMENT_ID = {0}",12);
            }else {
                // 不是财务部长
                if (staff.getDepartment().getDepartmentName().equals("人力资源部")){
                    //如果是人力资源部的部长 则只能查看人力资源部人员
                    // 人力资源部的DEPARTMENT_ID是10
                    wrapper.where("DEPARTMENT_ID = {0}",10);
                }else {
                    //都不是的话就是超级管理员了 可以查看部长 和 财务部 人力资源部的
                    wrapper.where("(DEPARTMENT_ID = {0} or DEPARTMENT_ID = {1} or POSITION_ID = {2} or POSITION_ID = {3})", 10,12,1,2);
                }
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        List<Staff> staffList = staffService.selectList(wrapper);
        if (staffList.size()==1){
            //  如果列表只有一个员工 则 去得到他的权限列表 和管理部门的列表
            Staff staff = staffList.get(0);
            String s_dname = staff.getDepartment().getDepartmentName();
            String s_pname = staff.getPosition().getPositionName();
            Wrapper<Permission> pw = new EntityWrapper<>();
            Wrapper<Department> dw = new EntityWrapper<>();
            if (s_dname.equals("财务部")){
                if (s_pname.equals("部门经理")){
                    // 如果该员工是财务部部长的话 权限ID 00-03
                    pw.where("PERMISSION_ID >= {0} and PERMISSION_ID <= {1}",0,3);
                }else {
                    // 如果该员工是财务部员工的话 权限ID 00-02
                    pw.where("PERMISSION_ID >= {0} and PERMISSION_ID <= {1}",0,2);
                }

            }else {
                if (s_dname.equals("人力资源部")){
                    if (s_pname.equals("部门经理")){
                        // 如果该员工是人力资源部部长的话 权限ID 10-16
                        pw.where("PERMISSION_ID >= {0} and PERMISSION_ID <= {1}",10,16);
                    }else {
                        // 如果该员工是人力资源部员工的话 权限ID 10-15
                        pw.where("PERMISSION_ID >= {0} and PERMISSION_ID <= {1}",10,15);
                    }

                }else {
                    if (s_dname.equals("高管层")){
                        // 如果是高管层 有所有部门的所有权限
                    }else {
                        // 其他部的部长 只有本部门的 查看工资的权限id = 0
                        dw.where("DEPARTMENT_NAME = {0}", s_dname);
                        pw.where("PERMISSION_id = {0}", 0);
                    }
                }
            }

            List<Permission> permissionList = permissionService.selectList(pw);
            List<Department> departmentList = departmentService.selectList(dw);
            map.put("permissionList", permissionList);
            map.put("departmentList", departmentList);
            map.put("staffList", staffList);
        }

        return map;
    }

    /**
     *  实现了根据前端传来的查询条件（可能是员工工号或者是员工的姓名）进行查询 并且返回结果
     */
    @ResponseBody
    @RequestMapping("/searchByCondition")
    public List<WhiteList> searchByCondition(String search_condition){
        String condition = "%"+search_condition+"%";
        return whiteListService.searchByCondition(condition);
    }


    /**
     *
     * 根据下拉选择框的值筛选数据 返回结果
     */
    @ResponseBody
    @RequestMapping("/showBySelect")
    public Object showBySelect(@RequestParam(value="staffId", defaultValue="") String staffId,
                               @RequestParam(value="staffName", defaultValue="") String staffName,
                               @RequestParam(value="departmentName", defaultValue="")String departmentName,
                               @RequestParam(value="permissionName", defaultValue="") String permissionName,
                               @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                               @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<WhiteList> page = new Page<>(pageNumber, pageSize);
        Wrapper<WhiteList> ew = new EntityWrapper<>();
        ew.where("d.DEPARTURE_TIME is null");

        if(staffId.length()>0)
            ew.where("a.STAFF_ID = {0}", Integer.valueOf(staffId));
        if(staffName.length()>0)
            ew.where("d.STAFF_NAME = {0}", staffName);
        if(departmentName.length()>0)
            ew.where("DEPARTMENT_NAME = {0}", departmentName);
        if(permissionName.length()>0)
            ew.where("PERMISSION_NAME = {0}", permissionName);


        // 获取当前登录账号的ID
        try {
            ShiroUser user = ShiroKit.getUser();
            Long userStaffId = Long.parseLong(user.getAccount());
            //根据当前ID返回它能查询的内容
            Wrapper<Staff> s_w = new EntityWrapper<>();
            s_w.where("STAFF_ID = {0}",userStaffId);
            Staff staff = staffService.selectOne(s_w);
            if (staff.getDepartment().getDepartmentName().equals("财务部")){
                //如果是财务部的部长 则只能查看财务部权限情况 即黑名单中StaffId是财务人员
                // 财务部的DEPARTMENT_ID是12
                ew.where("d.DEPARTMENT_ID = {0}",12);
            }else {
                // 不是财务部长
                if (staff.getDepartment().getDepartmentName().equals("人力资源部")){
                    //如果是人力资源部的部长 则只能查看人力资源部权限情况 即黑名单中StaffId是HR
                    // 人力资源部的DEPARTMENT_ID是10
                    ew.where("d.DEPARTMENT_ID = {0}",10);
                }else {
                    //都不是的话就是超级管理员了 可以查看全部的
                }
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        // 控制台输出ew
        System.out.println(ew.getSqlSegment());
        Map<String, Object> result = new HashMap<>(5);
        List<WhiteList> list = whiteListService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;

    }


    /**
     * 跳转到添加薪资权限管理--白名单维护
     */
    @RequestMapping("/whiteList_add")
    @BussinessLog(key = "/whiteList/whiteList_add", type = BussinessLogType.INSERT, value = "跳转到添加薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:whiteList_add")
    public String whiteListAdd() {
        return PREFIX + "whiteList_add.html";
    }



    /**
     *  返回修改白名单权限的页面（IFrame）
     *  参数有六个 正好是WhiteList对象的属性 使用WhiteList接收
     */
    @RequestMapping(value = "/whiteList_update",method = RequestMethod.GET)
    @BussinessLog(key = "/whiteList/whiteList_update", type = BussinessLogType.MODIFY, value = "跳转到修改薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:whiteList_update")
    public String whiteListUpdate(WhiteList whiteList,Model model) {
//        String staffId, String departmentId,String permissionId, String staffName,
        System.out.println("=========="+whiteList);
        model.addAttribute("item",whiteList);
        return PREFIX + "whiteList_edit.html";
    }


    /**
     * 获取薪资权限管理--白名单维护列表
     * 添加了根据查询条件进行返回 没有查询条件时返回所有数据
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/whiteList/list", type = BussinessLogType.QUERY, value = "获取薪资权限管理--白名单维护列表")
    @RequiresPermissions("whiteList:list")
    @ResponseBody
    public Object list(@RequestParam(value = "search_condition",required = false,defaultValue = "") String search_condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        String condition = "%"+search_condition+"%";
        System.out.println("in WhiteListController condition: "+condition);

        Page<WhiteList> page = new Page<>(pageNumber, pageSize);
        Wrapper<WhiteList> ew = new EntityWrapper<>();
        ew.where("(a.STAFF_ID like {0} or d.STAFF_NAME like {1})", condition,condition);
        ew.where("d.DEPARTURE_TIME is null");

        // 获取当前登录账号的ID
        try {
            ShiroUser user = ShiroKit.getUser();
            Long staffId = Long.parseLong(user.getAccount());
            //根据当前ID返回它能查询的内容
            Wrapper<Staff> s_w = new EntityWrapper<>();
            s_w.where("STAFF_ID = {0}",staffId);
            Staff staff = staffService.selectOne(s_w);
            System.out.println("========当前用户的信息：=========");
            System.out.println(staff);
            if (staff.getDepartment().getDepartmentName().equals("财务部")){
                //如果是财务部的部长 则只能查看财务部权限情况 即黑名单中StaffId是财务人员
                // 财务部的DEPARTMENT_ID是12
                ew.where("d.DEPARTMENT_ID = {0}",12);
            }else {
                // 不是财务部长
                if (staff.getDepartment().getDepartmentName().equals("人力资源部")){
                    //如果是人力资源部的部长 则只能查看人力资源部权限情况 即黑名单中StaffId是HR
                    // 人力资源部的DEPARTMENT_ID是10
                    ew.where("d.DEPARTMENT_ID = {0}",10);
                }else {
                    //都不是的话就是超级管理员了 可以查看全部的权限授予情况

                }
            }

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        //输出ew
        System.out.println(ew.getSqlSegment());

        Map<String, Object> result = new HashMap<>(5);
        List<WhiteList> list = whiteListService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增权限白名单
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/whiteList/add", type = BussinessLogType.INSERT, value = "新增薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:add")
    @ResponseBody
    public Object add(WhiteList param) {
        //先根据部门和权限的名称得到部门和权限的ID
        Wrapper<Department> w1 = new EntityWrapper<>();
        w1.where("DEPARTMENT_NAME = {0}", param.getDepartmentName());
        Wrapper<Permission> w2 = new EntityWrapper<>();
        w2.where("PERMISSION_NAME = {0}", param.getPermissionName());
        Department department = departmentService.selectOne(w1);
        Permission permission = permissionService.selectOne(w2);
        param.setDepartmentId(department.getDepartmentId());
        param.setPermissionId(permission.getPermissionId());

        // 然后查询表中是否已经有改数据了
        Wrapper<WhiteList> wrapper = new EntityWrapper<>();
        wrapper.where("STAFF_ID = {0}",param.getStaffId());
        wrapper.where("DEPARTMENT_ID = {0}",param.getDepartmentId());
        wrapper.where("PERMISSION_ID = {0}",param.getPermissionId());
        List<WhiteList> lists = whiteListService.selectList(wrapper);
        System.out.println("=================要插入的数据是："+param);
        System.out.println(lists);
        if (lists.isEmpty()||lists==null){
            //如果没有改数据可以插入 否则不可以
            System.out.println("可以插入该条数据");
            //将param（WhiteList对象添加到表中）
            boolean res = whiteListService.insert(param);
            return res;
        }else {
            return "exist";
        }

    }

    /**
     * 删除功能
     * 传入的要删除的list列表信息
     * 实现根据主键 批量删除
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/whiteList/delete", type = BussinessLogType.DELETE, value = "删除薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:delete")
    @ResponseBody
    public Object delete(String delete_list) {
        // 参数delete_list是一个JSON字符串 下面将其转化为List
        System.out.println("=========delete_list:  "+ delete_list);
//        List<WhiteList> list = new ArrayList<WhiteList>();
//        JSONObject.parseArray(delete_list, WhiteList.class);

        // 先变成JSON数组对象 然后转化成list
        JSONArray delete_list_arr = JSONArray.fromObject(delete_list);
        List<WhiteList> list = (List<WhiteList>) JSONArray.toCollection(delete_list_arr, WhiteList.class);

        System.out.println("==========打印delete_list样例："+list.get(0));
        whiteListService.deleteBatchByIds(list);
        return SUCCESS_TIP;
    }

    /**
     *  更新白名单的权限
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/whiteList/update", type = BussinessLogType.MODIFY, value = "修改薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:update")
    @ResponseBody
    public Object update(WhiteList whiteList) {
        Integer staffId = whiteList.getStaffId();
        Integer oldDepartmentId = whiteList.getDepartmentId();
        Integer oldPermissionId = whiteList.getPermissionId();

        // 首先根据部门名称查找到部门的ID 权限名称查找权限ID
        Wrapper<Department> wrapper1 = new EntityWrapper<>();
        wrapper1.where("DEPARTMENT_NAME = {0}", whiteList.getDepartmentName());
        Department department = departmentService.selectOne(wrapper1);
        Wrapper<Permission> wrapper2 = new EntityWrapper<>();
        wrapper2.where("PERMISSION_NAME = {0}", whiteList.getPermissionName());
        Permission permission = permissionService.selectOne(wrapper2);
        //设置新的ID
        whiteList.setDepartmentId(department.getDepartmentId());
        whiteList.setPermissionId(permission.getPermissionId());

        // 然后查询表中是否已经有修改后的数据了如果有则不能修改
        Wrapper<WhiteList> wrapper0 = new EntityWrapper<>();
        wrapper0.where("STAFF_ID = {0}",whiteList.getStaffId());
        wrapper0.where("DEPARTMENT_ID = {0}",whiteList.getDepartmentId());
        wrapper0.where("PERMISSION_ID = {0}",whiteList.getPermissionId());
        System.out.println("=================修改后的数据是："+whiteList);

        List<WhiteList> lists = whiteListService.selectList(wrapper0);
        if (lists.isEmpty()||lists==null){
            System.out.println("==============可以修改该条数据");
            // 然后根据三个主键找到并修改
            Wrapper<WhiteList> wrapper = new EntityWrapper<>();
            wrapper.where("STAFF_ID = {0}",staffId);
            wrapper.where("DEPARTMENT_ID = {0}",oldDepartmentId);
            wrapper.where("PERMISSION_ID = {0}",oldPermissionId);
            boolean res = whiteListService.update(whiteList, wrapper);
            return res;
        }else {
            return "exist";
        }

    }

    /**
     * 薪资权限管理--白名单维护详情
     */
    @RequestMapping(value = "/detail/{whiteListId}")
    @BussinessLog(key = "/whiteList/detail", type = BussinessLogType.QUERY, value = "薪资权限管理--白名单维护详情")
    @RequiresPermissions("whiteList:detail")
    @ResponseBody
    public Object detail(@PathVariable("whiteListId") String whiteListId) {
        return whiteListService.selectById(whiteListId);
    }
}
