package com.jxdinfo.hussar.inisalary.controller;

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
import com.jxdinfo.hussar.inisalary.model.TDepartment;
import com.jxdinfo.hussar.inisalary.service.ITDepartmentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author yxx
 * @Date 2020-06-17 12:31:33
 */
@Controller
@RequestMapping("/tDepartment")
public class TDepartmentController extends BaseController {

    private String PREFIX = "/inisalary/tDepartment/";

    @Autowired
    private ITDepartmentService tDepartmentService;

    /**
     * 跳转到首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/tDepartment/view", type = BussinessLogType.QUERY, value = "页面")
    @RequiresPermissions("tDepartment:view")
    public String index() {
        return PREFIX + "tDepartment.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/tDepartment_add")
    @BussinessLog(key = "/tDepartment/tDepartment_add", type = BussinessLogType.INSERT, value = "跳转到添加")
    @RequiresPermissions("tDepartment:tDepartment_add")
    public String tDepartmentAdd() {
        return PREFIX + "tDepartment_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/tDepartment_update/{tDepartmentId}")
    @BussinessLog(key = "/tDepartment/tDepartment_update", type = BussinessLogType.MODIFY, value = "跳转到修改")
    @RequiresPermissions("tDepartment:tDepartment_update")
    public String tDepartmentUpdate(@PathVariable String tDepartmentId, Model model) {
        TDepartment tDepartment = tDepartmentService.selectById(tDepartmentId);
        model.addAttribute("item",tDepartment);
        return PREFIX + "tDepartment_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/tDepartment/list", type = BussinessLogType.QUERY, value = "获取列表")
    @RequiresPermissions("tDepartment:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<TDepartment> page = new Page<>(pageNumber, pageSize);
        Wrapper<TDepartment> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<TDepartment> list = tDepartmentService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/tDepartment/add", type = BussinessLogType.INSERT, value = "新增")
    @RequiresPermissions("tDepartment:add")
    @ResponseBody
    public Object add(TDepartment tDepartment) {
        tDepartmentService.insert(tDepartment);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/tDepartment/delete", type = BussinessLogType.DELETE, value = "删除")
    @RequiresPermissions("tDepartment:delete")
    @ResponseBody
    public Object delete(@RequestParam String tDepartmentId) {
        tDepartmentService.deleteById(tDepartmentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/tDepartment/update", type = BussinessLogType.MODIFY, value = "修改")
    @RequiresPermissions("tDepartment:update")
    @ResponseBody
    public Object update(TDepartment tDepartment) {
        TDepartment tDepartment1 = tDepartmentService.selectById(tDepartment);
        LogObjectHolder.me().set(tDepartment1);
        tDepartmentService.updateById(tDepartment);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{tDepartmentId}")
    @BussinessLog(key = "/tDepartment/detail", type = BussinessLogType.QUERY, value = "详情")
    @RequiresPermissions("tDepartment:detail")
    @ResponseBody
    public Object detail(@PathVariable("tDepartmentId") String tDepartmentId) {
        return tDepartmentService.selectById(tDepartmentId);
    }
}
