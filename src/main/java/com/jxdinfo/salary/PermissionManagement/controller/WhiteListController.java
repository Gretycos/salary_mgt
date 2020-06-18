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
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.PermissionManagement.service.IWhiteListService;
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
     * 跳转到添加薪资权限管理--白名单维护
     */
    @RequestMapping("/whiteList_add")
    @BussinessLog(key = "/whiteList/whiteList_add", type = BussinessLogType.INSERT, value = "跳转到添加薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:whiteList_add")
    public String whiteListAdd() {
        return PREFIX + "whiteList_add.html";
    }

    /**
     * 跳转到修改薪资权限管理--白名单维护
     */
    @RequestMapping("/whiteList_update/{whiteListId}")
    @BussinessLog(key = "/whiteList/whiteList_update", type = BussinessLogType.MODIFY, value = "跳转到修改薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:whiteList_update")
    public String whiteListUpdate(@PathVariable String whiteListId, Model model) {
        WhiteList whiteList = whiteListService.selectById(whiteListId);
        model.addAttribute("item",whiteList);
        return PREFIX + "whiteList_edit.html";
    }

    /**
     * 获取薪资权限管理--白名单维护列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/whiteList/list", type = BussinessLogType.QUERY, value = "获取薪资权限管理--白名单维护列表")
    @RequiresPermissions("whiteList:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        System.out.println("+++++++++++++++++++"+pageNumber);
        System.out.println("+++++++++++++++++++"+pageSize);
        Page<WhiteList> page = new Page<>(pageNumber, pageSize);
        Wrapper<WhiteList> ew = new EntityWrapper<>();
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
     * 删除薪资权限管理--白名单维护
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/whiteList/delete", type = BussinessLogType.DELETE, value = "删除薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:delete")
    @ResponseBody
    public Object delete(@RequestParam String whiteListId) {
        whiteListService.deleteById(whiteListId);
        return SUCCESS_TIP;
    }

    /**
     * 修改薪资权限管理--白名单维护
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/whiteList/update", type = BussinessLogType.MODIFY, value = "修改薪资权限管理--白名单维护")
    @RequiresPermissions("whiteList:update")
    @ResponseBody
    public Object update(WhiteList whiteList) {
        WhiteList whiteList1 = whiteListService.selectById(whiteList);
        LogObjectHolder.me().set(whiteList1);
        whiteListService.updateById(whiteList);
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
