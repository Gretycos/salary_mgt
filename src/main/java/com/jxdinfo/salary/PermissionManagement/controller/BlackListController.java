package com.jxdinfo.salary.PermissionManagement.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.jxdinfo.hussar.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
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
     *  分别 获取黑名单列表里面的值 最终结果是distinct的形式 去掉重复的的项
     */
    @ResponseBody
    @RequestMapping("select")
    public Map<String,Object> getSelection(){
        Map<String,Object> map = new HashMap<>();
        List<Integer> staffIdList = blackListService.selectStaffId();
        List<String> staffNameList = blackListService.selectStaffName();
        List<String> departmentNameList = blackListService.selectDepartmentName();
        List<String> permissionNameList = blackListService.selectPermissionName();
        map.put("staffIdList", staffIdList);
        map.put("staffNameList", staffNameList);
        map.put("departmentNameList", departmentNameList);
        map.put("permissionNameList", permissionNameList);
        return map;

    }

    /**
     * 根据四个下拉选择框的值筛选、展示数据
     */
    @ResponseBody
    @RequestMapping("/showBySelect")
    public Object showBySelect(String staffId,String staffName,String departmentName, String permissionName,
                                @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                                @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<BlackList> page = new Page<>(pageNumber, pageSize);
        Wrapper<BlackList> ew = new EntityWrapper<>();
        if(staffId.length()>0)
            ew.where("a.STAFF_ID = {0}", Integer.valueOf(staffId));
        if(staffName.length()>0)
            ew.where("d.STAFF_NAME = {0}", staffName);
        if(departmentName.length()>0)
            ew.where("DEPARTMENT_NAME = {0}", departmentName);
        if(permissionName.length()>0)
            ew.where("PERMISSION_NAME = {0}", permissionName);

        System.out.println(ew.getSqlSegment());
        Map<String, Object> result = new HashMap<>(5);
        List<BlackList> list = blackListService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;

    }


    /**
     * 跳转到添加薪资权限管理---黑名单维护
     */
    @RequestMapping("/blackList_add")
    @BussinessLog(key = "/blackList/blackList_add", type = BussinessLogType.INSERT, value = "跳转到添加薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:blackList_add")
    public String blackListAdd() {
        return PREFIX + "blackList_add.html";
    }

    /**
     * 跳转到修改薪资权限管理---黑名单维护
     */
    @RequestMapping("/blackList_update/{blackListId}")
    @BussinessLog(key = "/blackList/blackList_update", type = BussinessLogType.MODIFY, value = "跳转到修改薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:blackList_update")
    public String blackListUpdate(@PathVariable String blackListId, Model model) {
        BlackList blackList = blackListService.selectById(blackListId);
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
        ew.where("a.STAFF_ID like {0} or d.STAFF_NAME like {1}", condition,condition);

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
    public Object add(BlackList blackList) {
        blackListService.insert(blackList);
        return SUCCESS_TIP;
    }

    /**
     * 删除薪资权限管理---黑名单维护
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/blackList/delete", type = BussinessLogType.DELETE, value = "删除薪资权限管理---黑名单维护")
    @RequiresPermissions("blackList:delete")
    @ResponseBody
    public Object delete(@RequestParam String blackListId) {
        blackListService.deleteById(blackListId);
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
        BlackList blackList1 = blackListService.selectById(blackList);
        LogObjectHolder.me().set(blackList1);
        blackListService.updateById(blackList);
        return SUCCESS_TIP;
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
