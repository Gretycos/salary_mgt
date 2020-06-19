package com.jxdinfo.salary.PermissionManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.permission.service.IPermissionService;
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
     */
    @ResponseBody
    @RequestMapping("select")
    public Map<String,Object> getSelection(){
        Map<String,Object> map = new HashMap<>();
        List<Integer> staffIdList = whiteListService.selectStaffId();
        List<String> staffNameList = whiteListService.selectStaffName();
        List<String> departmentNameList = whiteListService.selectDepartmentName();
        List<String> permissionNameList = whiteListService.selectPermissionName();
        map.put("staffIdList", staffIdList);
        map.put("staffNameList", staffNameList);
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
    public Map<String,Object> getAllDepartmentAndPermission(){
        Map<String,Object> map = new HashMap<>();
        List<Permission> permissionList = permissionService.getAllPermission();
        Wrapper<Department> wrapper = new EntityWrapper<>();
        List<Department> departmentList = departmentService.selectList(wrapper);
        map.put("permissionList", permissionList);
        map.put("departmentList", departmentList);
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
    public Object showBySelect(String staffId,String staffName,String departmentName, String permissionName,
                               @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                               @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<WhiteList> page = new Page<>(pageNumber, pageSize);
        Wrapper<WhiteList> ew = new EntityWrapper<>();
        if(staffId.length()>0)
            ew.where("a.STAFF_ID = {0}", Integer.valueOf(staffId));
        if(staffName.length()>0)
            ew.where("d.STAFF_NAME = {0}", staffName);
        if(departmentName.length()>0)
            ew.where("DEPARTMENT_NAME = {0}", departmentName);
        if(permissionName.length()>0)
            ew.where("PERMISSION_NAME = {0}", permissionName);

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
        ew.where("a.STAFF_ID like {0} or d.STAFF_NAME like {1}", condition,condition);
        //输出ew
        System.out.println(ew.getSqlSegment());

        Map<String, Object> result = new HashMap<>(5);
        List<WhiteList> list = whiteListService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增薪资权限管理--白名单维护
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/whiteList/add", type = BussinessLogType.INSERT, value = "新增薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:add")
    @ResponseBody
    public Object add(WhiteList whiteList) {
        whiteListService.insert(whiteList);
        return SUCCESS_TIP;
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
        whiteList.setDepartmentId(department.getDepartmentId());
        whiteList.setPermissionId(permission.getPermissionId());

        // 然后根据三个主键找到并修改
        Wrapper<WhiteList> wrapper = new EntityWrapper<>();
        wrapper.where("STAFF_ID = {0}",staffId);
        wrapper.where("DEPARTMENT_ID = {0}",oldDepartmentId);
        wrapper.where("PERMISSION_ID = {0}",oldPermissionId);
        whiteListService.update(whiteList,wrapper);
        return SUCCESS_TIP;
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
