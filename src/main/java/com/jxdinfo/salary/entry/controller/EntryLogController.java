package com.jxdinfo.salary.entry.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.salary.move.model.MoveLog;
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
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.entry.service.IEntryLogService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入职日志控制器
 *
 * @author zxn
 * @Date 2020-06-19 14:38:17
 */
@Controller
@RequestMapping("/entry")
public class EntryLogController extends BaseController {

    private String PREFIX = "/salary/entry/";

    @Autowired
    private IEntryLogService entryLogService;

    /**
     * 跳转到入职日志首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/entry/view", type = BussinessLogType.QUERY, value = "入职日志页面")
    @RequiresPermissions("entry:view")
    public String index() {
        return PREFIX + "entryLog.html";
    }


    /**
     * 获取入职日志列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/entry/list", type = BussinessLogType.QUERY, value = "获取入职日志列表")
    @RequiresPermissions("entry:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<EntryLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<EntryLog> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<EntryLog> list = entryLogService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取特定部门入职列表
     */
    @RequestMapping(value = "/list/department")
    @BussinessLog(key = "/entry/list/department", type = BussinessLogType.QUERY, value = "获取特定部门入职列表")
    @RequiresPermissions("entry:list")
    @ResponseBody
    public Object listByODid(String condition,
                             @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<EntryLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<EntryLog> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<EntryLog> list = entryLogService.selectByDidPage(page,ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }


    /**
     * 入职日志详情
     */
    @RequestMapping(value = "/detail/{entryLogId}")
    @BussinessLog(key = "/entry/detail", type = BussinessLogType.QUERY, value = "入职日志详情")
    @RequiresPermissions("entry:detail")
    @ResponseBody
    public Object detail(@PathVariable("entryLogId") String entryLogId) {
        return entryLogService.selectById(entryLogId);
    }
}
