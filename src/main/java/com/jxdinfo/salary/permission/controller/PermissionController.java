package com.jxdinfo.salary.permission.controller;

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
import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.permission.service.IPermissionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理-权限信息控制器
 *
 * @author 王明凯
 * @Date 2020-06-19 11:16:57
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    private String PREFIX = "/permission/permission/";

    @Autowired
    private IPermissionService permissionService;

    /**
     * 跳转到权限管理-权限信息首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/permission/view", type = BussinessLogType.QUERY, value = "权限管理-权限信息页面")
    @RequiresPermissions("permission:view")
    public String index() {
        return PREFIX + "permission.html";
    }

    /**
     * 跳转到添加权限管理-权限信息
     */
    @RequestMapping("/permission_add")
    @BussinessLog(key = "/permission/permission_add", type = BussinessLogType.INSERT, value = "跳转到添加权限管理-权限信息")
    @RequiresPermissions("permission:permission_add")
    public String permissionAdd() {
        return PREFIX + "permission_add.html";
    }

    /**
     * 跳转到修改权限管理-权限信息
     */
    @RequestMapping("/permission_update/{permissionId}")
    @BussinessLog(key = "/permission/permission_update", type = BussinessLogType.MODIFY, value = "跳转到修改权限管理-权限信息")
    @RequiresPermissions("permission:permission_update")
    public String permissionUpdate(@PathVariable String permissionId, Model model) {
        Permission permission = permissionService.selectById(permissionId);
        model.addAttribute("item",permission);
        return PREFIX + "permission_edit.html";
    }

    /**
     * 获取权限管理-权限信息列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/permission/list", type = BussinessLogType.QUERY, value = "获取权限管理-权限信息列表")
    @RequiresPermissions("permission:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Permission> page = new Page<>(pageNumber, pageSize);
        Wrapper<Permission> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<Permission> list = permissionService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增权限管理-权限信息
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/permission/add", type = BussinessLogType.INSERT, value = "新增权限管理-权限信息")
    @RequiresPermissions("permission:add")
    @ResponseBody
    public Object add(Permission permission) {
        permissionService.insert(permission);
        return SUCCESS_TIP;
    }

    /**
     * 删除权限管理-权限信息
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/permission/delete", type = BussinessLogType.DELETE, value = "删除权限管理-权限信息")
    @RequiresPermissions("permission:delete")
    @ResponseBody
    public Object delete(@RequestParam String permissionId) {
        permissionService.deleteById(permissionId);
        return SUCCESS_TIP;
    }

    /**
     * 修改权限管理-权限信息
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/permission/update", type = BussinessLogType.MODIFY, value = "修改权限管理-权限信息")
    @RequiresPermissions("permission:update")
    @ResponseBody
    public Object update(Permission permission) {
        Permission permission1 = permissionService.selectById(permission);
        LogObjectHolder.me().set(permission1);
        permissionService.updateById(permission);
        return SUCCESS_TIP;
    }

    /**
     * 权限管理-权限信息详情
     */
    @RequestMapping(value = "/detail/{permissionId}")
    @BussinessLog(key = "/permission/detail", type = BussinessLogType.QUERY, value = "权限管理-权限信息详情")
    @RequiresPermissions("permission:detail")
    @ResponseBody
    public Object detail(@PathVariable("permissionId") String permissionId) {
        return permissionService.selectById(permissionId);
    }
}
