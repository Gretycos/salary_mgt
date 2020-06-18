package com.jxdinfo.salary.move.controller;

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
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.move.service.IMoveLogService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调动日志控制器
 *
 * @author zxn
 * @Date 2020-06-17 16:23:07
 */
@Controller
@RequestMapping("/move")
public class MoveLogController extends BaseController {

    private String PREFIX = "/salary/move/";

    @Autowired
    private IMoveLogService moveLogService;

    /**
     * 跳转到调动日志首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/move/view", type = BussinessLogType.QUERY, value = "调动日志页面")
    @RequiresPermissions("move:view")
    public String index() {
        return PREFIX + "moveLog.html";
    }

    /**
     * 跳转到添加调动日志
     */
    @RequestMapping("/moveLog_add")
    @BussinessLog(key = "/move/moveLog_add", type = BussinessLogType.INSERT, value = "跳转到添加调动日志")
    @RequiresPermissions("move:moveLog_add")
    public String moveLogAdd() {
        return PREFIX + "moveLog_add.html";
    }

    /**
     * 获取调动日志列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/move/list", type = BussinessLogType.QUERY, value = "获取调动日志列表")
    @RequiresPermissions("move:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<MoveLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<MoveLog> ew = new EntityWrapper<>();
        String moveName = super.getPara("moveName");
        ew.like("MOVE_ID",moveName);
        Map<String, Object> result = new HashMap<>(5);
        List<MoveLog> list = moveLogService.selectPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取特定部门调动日志列表
     */
    @RequestMapping(value = "/list/department")
    @BussinessLog(key = "/move/list/department", type = BussinessLogType.QUERY, value = "获取调动日志列表")
    @RequiresPermissions("move:list")
    @ResponseBody
    public Object listByODid(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<MoveLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<MoveLog> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<MoveLog> list = moveLogService.selectByDidPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增调动日志
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/move/add", type = BussinessLogType.INSERT, value = "新增调动日志")
    @RequiresPermissions("move:add")
    @ResponseBody
    public Object add(MoveLog moveLog) {
        moveLogService.insert(moveLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除调动日志
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/move/delete", type = BussinessLogType.DELETE, value = "删除调动日志")
    @RequiresPermissions("move:delete")
    @ResponseBody
    public Object delete(@RequestParam String moveLogId) {
        moveLogService.deleteById(moveLogId);
        return SUCCESS_TIP;
    }

    /**
     * 调动日志详情
     */
    @RequestMapping(value = "/detail/{moveLogId}")
    @BussinessLog(key = "/move/detail", type = BussinessLogType.QUERY, value = "调动日志详情")
    @RequiresPermissions("move:detail")
    @ResponseBody
    public Object detail(@PathVariable("moveLogId") String moveLogId) {
        return moveLogService.selectById(moveLogId);
    }
}