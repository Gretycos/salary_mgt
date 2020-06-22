package com.jxdinfo.salary.PermissionManagement.controller;

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
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.PermissionManagement.service.IBlackListService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 薪资权限管理---黑名单维护控制器
 *
 * @author 王明凯
 * @Date 2020-06-16 15:35:16
 */
@Controller
@RequestMapping("/blackList")
public class BlackListController extends BaseController {

    private String PREFIX = "/salary/PermissionManagement/blackList/";

    @Autowired
    private IBlackListService blackListService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IStaffService staffService;

    /**
     * 跳转到薪资权限管理---黑名单维护首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/blackList/view", type = BussinessLogType.QUERY, value = "黑名单维护")
    @RequiresPermissions("blackList:view")
    public String index() {
        return PREFIX + "blackList.html";
    }


    /**
     *  根据当前用户的身份获取
     *  分别 获取黑名单列表里面的值 最终结果是distinct的形式 去掉重复的的项
     */
    @ResponseBody
    @RequestMapping("/select")
    public Map<String,Object> getSelection(){
        Map<String,Object> map = new HashMap<>();
//        List<Integer> staffIdList = blackListService.selectStaffId();
//        List<String> staffNameList = blackListService.selectStaffName();
//        map.put("staffIdList", staffIdList);
//        map.put("staffNameList", staffNameList);

        Wrapper<BlackList> wrapper = new EntityWrapper<>();
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


        List<String> departmentNameList = blackListService.selectDepartmentName(wrapper);
        List<String> permissionNameList = blackListService.selectPermissionName(wrapper);

        map.put("departmentNameList", departmentNameList);
        map.put("permissionNameList", permissionNameList);
        return map;

    }

    /**
     *  根据当前用户的身份 是人力资源部长还是财务部长或者超级管理员
     *  得到所有的部门信息 和权限信息
     *  getAllDepartmentAndPermission
     */
    @ResponseBody
    @RequestMapping("/getAllDepartmentAndPermission")
    public Map<String,Object> getAllDepartmentAndPermission(
            @RequestParam(value = "modify_id",required = false,defaultValue = "") String modify_id){
        Map<String,Object> map = new HashMap<>();

        if (!modify_id.equals("")){
            // 如果输入了要修改的员工的id
            map = (Map<String,Object>)getStaffById(Integer.parseInt(modify_id));
        }else {
            Wrapper<Department> wrapper1 = new EntityWrapper<>();
            List<Department> departmentList = departmentService.selectList(wrapper1);
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
     *  根据当前登录用户的身份 是财务部 人力资源部 还是超级管理员
     *  得到所有的部门信息 和权限信息
     *  getAllDepartmentAndPermission
     */
    @ResponseBody
    @RequestMapping("/getAllStaff")
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
                    //都不是的话就是超级管理员了 可以查看财务部和人力资源部全部的人工 以及所有部门的部长
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
     * 根据当前用户的身份选择 如果是财务部长 只能选择财务部员工的
     * 根据四个下拉选择框的值筛选、展示数据
     */
    @ResponseBody
    @RequestMapping("/showBySelect")
    public Object showBySelect(@RequestParam(value="staffId", defaultValue="") String staffId,
                               @RequestParam(value="staffName", defaultValue="") String staffName,
                               @RequestParam(value="departmentName", defaultValue="")String departmentName,
                               @RequestParam(value="permissionName", defaultValue="") String permissionName,
                                @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                                @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<BlackList> page = new Page<>(pageNumber, pageSize);
        Wrapper<BlackList> ew = new EntityWrapper<>();
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

        System.out.println(ew.getSqlSegment());
        Map<String, Object> result = new HashMap<>(5);
        List<BlackList> list = blackListService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;

    }


    /**
     *   跳转新增黑名单页面
     */
    @RequestMapping("/blackList_add")
    @BussinessLog(key = "/blackList/blackList_add", type = BussinessLogType.INSERT, value = "跳转到添加薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:blackList_add")
    public String blackListAdd() {
        return PREFIX + "blackList_add.html";
    }

    /**
     * 用于生成黑名单权限修改页面的
     * 接收传来的blackList对象(实际上传的是BlackList的六个属性)
     */
    @RequestMapping(value = "/blackList_update",method = RequestMethod.GET)
    @BussinessLog(key = "/blackList/blackList_update", type = BussinessLogType.MODIFY, value = "跳转到修改薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:blackList_update")
    public String blackListUpdate(BlackList blackList, Model model) {
        model.addAttribute("item",blackList);
        return PREFIX + "blackList_edit.html";
    }

    /**
     * 获取薪资权限管理---黑名单维护列表
     *  新增根据条件进行查询 条件：员工姓名或者工号
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/blackList/list", type = BussinessLogType.QUERY, value = "获取薪资权限管理---黑名单维护列表")
    @RequiresPermissions("blackList:list")
    @ResponseBody
    public Object list(@RequestParam(value = "search_condition",required = false,defaultValue = "") String search_condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        String condition = "%"+search_condition+"%";
        System.out.println("in BlackListController condition: "+condition);

        Page<BlackList> page = new Page<>(pageNumber, pageSize);
        Wrapper<BlackList> ew = new EntityWrapper<>();
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
                    //都不是的话就是超级管理员了 可以查看全部的权限授情况
//                    ew.where("(d.DEPARTMENT_ID = {0} or d.DEPARTMENT_ID = {1} )",10,12);
                }
            }

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>(5);
        List<BlackList> list = blackListService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增薪资权限管理---黑名单维护
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/blackList/add", type = BussinessLogType.INSERT, value = "新增薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:add")
    @ResponseBody
    public Object add(BlackList param) {
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
        Wrapper<BlackList> wrapper = new EntityWrapper<>();
        wrapper.where("STAFF_ID = {0}",param.getStaffId());
        wrapper.where("DEPARTMENT_ID = {0}",param.getDepartmentId());
        wrapper.where("PERMISSION_ID = {0}",param.getPermissionId());
        System.out.println("=================要插入的数据是："+param);

        List<BlackList> lists = blackListService.selectList(wrapper);
        if (lists.isEmpty()||lists==null){
            //如果没有改数据可以插入 否则不可以
            System.out.println("可以插入该条数据");
            //将param（WhiteList对象添加到表中）
            boolean res = blackListService.insert(param);
            return res;
        }else {
            return "exist";
        }
    }

    /**
     * 删除功能
     * 传入的参数是一个JSON格式的字符串 将其转化成List 然后 利用Mybatis进行批量删除
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/blackList/delete", type = BussinessLogType.DELETE, value = "删除薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:delete")
    @ResponseBody
    public Object delete(String delete_list) {
        // 输出delete_list
        System.out.println("=========delete_list:  "+ delete_list);
        //将delete_list转换成JSON数组对象
        JSONArray delete_list_arr = JSONArray.fromObject(delete_list);
        //将JSON数组对象转换为List
        List<BlackList> list = (List<BlackList>) JSONArray.toCollection(delete_list_arr, BlackList.class);

        System.out.println("==========打印list样例："+list.get(0));
        blackListService.deleteBatchByIds(list);
        return SUCCESS_TIP;
    }

    /**
     * 修改薪资权限管理---黑名单维护
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/blackList/update", type = BussinessLogType.MODIFY, value = "修改薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:update")
    @ResponseBody
    public Object update(BlackList blackList) {
        //保存原来的信息 用于后面找到该条数据
        Integer staffId = blackList.getStaffId();
        Integer oldDepartmentId = blackList.getDepartmentId();
        Integer oldPermissionId = blackList.getPermissionId();

        // 首先根据部门名称查找到部门的ID 权限名称查找权限ID
        Wrapper<Department> wrapper1 = new EntityWrapper<>();
        wrapper1.where("DEPARTMENT_NAME = {0}", blackList.getDepartmentName());
        Department department = departmentService.selectOne(wrapper1);
        Wrapper<Permission> wrapper2 = new EntityWrapper<>();
        wrapper2.where("PERMISSION_NAME = {0}", blackList.getPermissionName());
        Permission permission = permissionService.selectOne(wrapper2);
        //设置新的部门和权限ID
        blackList.setDepartmentId(department.getDepartmentId());
        blackList.setPermissionId(permission.getPermissionId());
        System.out.println("=================修改后的数据是："+blackList);

        // 非常重要的一步 要判断表中是否已经存在修改后的数据了
        Wrapper<BlackList> w = new EntityWrapper<>();
        w.where("STAFF_ID = {0}",blackList.getStaffId());
        w.where("DEPARTMENT_ID = {0}",blackList.getDepartmentId());
        w.where("PERMISSION_ID = {0}",blackList.getPermissionId());
        List<BlackList> lists = blackListService.selectList(w);

        if (lists.isEmpty()||lists==null){
            System.out.println("==============可以修改该条数据");
            // 然后根据三个主键找到并修改
            Wrapper<BlackList> wrapper = new EntityWrapper<>();
            wrapper.where("STAFF_ID = {0}",staffId);
            wrapper.where("DEPARTMENT_ID = {0}",oldDepartmentId);
            wrapper.where("PERMISSION_ID = {0}",oldPermissionId);
            boolean res = blackListService.update(blackList, wrapper);
            return res;
        }else {
            return "exist";
        }
    }

    /**
     * 薪资权限管理---黑名单维护详情
     */
    @RequestMapping(value = "/detail/{blackListId}")
    @BussinessLog(key = "/blackList/detail", type = BussinessLogType.QUERY, value = "薪资权限管理---黑名单维护详情")
    @RequiresPermissions("blackList:detail")
    @ResponseBody
    public Object detail(@PathVariable("blackListId") String blackListId) {
        return blackListService.selectById(blackListId);
    }
}
