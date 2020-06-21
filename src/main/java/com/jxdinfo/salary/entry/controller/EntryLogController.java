package com.jxdinfo.salary.entry.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.salary.departure.model.DepartureLog;
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

import java.text.SimpleDateFormat;
import java.util.*;

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
    public Object list(@RequestParam (value="condition1", defaultValue = "")String condition1,
                       @RequestParam (value="condition2", defaultValue = "")String condition2,
                       @RequestParam (value="condition3", defaultValue = "")String condition3,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<EntryLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<EntryLog> ew = new EntityWrapper<>();

        Map<String, Object> result = new HashMap<>(5);

        List<EntryLog> list = entryLogService.likeSelect(page,condition1,condition2,condition3);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取入职日志筛选列表
     */
    @RequestMapping(value = "/list/select")
    @BussinessLog(key = "/entry/list/select", type = BussinessLogType.QUERY, value = "获取入职日志筛选列表")
    @RequiresPermissions("entry:list")
    @ResponseBody
    public Object selectList(@RequestParam (value="condition1", defaultValue = "")String condition1,
                             @RequestParam (value="condition2", defaultValue = "")String condition2,
                             @RequestParam (value="condition3", defaultValue = "")String condition3,
                             @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<EntryLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<EntryLog> ew = new EntityWrapper<>();
        //模糊查询
        List<EntryLog> list = entryLogService.likeSelect(page,condition1,condition2,condition3);
        List<String> operationTimeList = new ArrayList<>();
        for(EntryLog e:list){
            operationTimeList.add(e.getOperationTime()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(e.getOperationTime()));
        }
        Set<String> operationTimes = new HashSet<>(operationTimeList);
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("operationTimes", operationTimes);
        return result;
    }


    /**
     * 获取筛选后的入职日志列表
     */
    @RequestMapping(value = "/list/condition")
    @BussinessLog(key = "/entry/list/condition", type = BussinessLogType.QUERY, value = "获取入职日志列表，筛选后")
    @RequiresPermissions("entry:list")
    @ResponseBody
    public Object conditionList(@RequestParam (value="condition1", defaultValue = "")String condition1,
                                @RequestParam (value="condition2", defaultValue = "")String condition2,
                                @RequestParam (value="condition3", defaultValue = "")String condition3,
                                @RequestParam (value="selectList")String conditions,
                                @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                                @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Map<String,String> info = JSONObject.parseObject(conditions,Map.class);
        Page<EntryLog> page = new Page<>(pageNumber, pageSize);
        String operationTime = info.get("operationTime");
        Wrapper<EntryLog> ew = new EntityWrapper<>();
        //模糊查询
        //page.setRecords(entryLogService.likeSelect(condition1, condition2, condition3));

        if (!operationTime.equals("")){
            if (operationTime.equals("所有")){
                ew.isNotNull("OPERATION_TIME");
            }else{
                ew.like("OPERATION_TIME",operationTime+"%");
            }
        }
        List<EntryLog> list = entryLogService.selectByDidPage(page,ew).getRecords();
        Map<String, Object> result = new HashMap<>(5);
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
