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
import com.jxdinfo.hussar.inisalary.model.TInisalaryInforLog;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInforLogService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始工资日志控制器
 *
 * @author yxx
 * @Date 2020-06-23 10:58:07
 */
@Controller
@RequestMapping("/tInisalaryInforLog")
public class TInisalaryInforLogController extends BaseController {

    private String PREFIX = "/inisalary/tInisalaryInforLog/";

    @Autowired
    private ITInisalaryInforLogService tInisalaryInforLogService;

    /**
     * 跳转到初始工资日志首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/tInisalaryInforLog/view", type = BussinessLogType.QUERY, value = "初始工资日志页面")
    @RequiresPermissions("tInisalaryInforLog:view")
    public String index() {
        return PREFIX + "tInisalaryInforLog.html";
    }

    /**
     * 跳转到添加初始工资日志
     */
    @RequestMapping("/tInisalaryInforLog_add")
    @BussinessLog(key = "/tInisalaryInforLog/tInisalaryInforLog_add", type = BussinessLogType.INSERT, value = "跳转到添加初始工资日志")
    @RequiresPermissions("tInisalaryInforLog:tInisalaryInforLog_add")
    public String tInisalaryInforLogAdd() {
        return PREFIX + "tInisalaryInforLog_add.html";
    }

    /**
     * 跳转到修改初始工资日志
     */
    @RequestMapping("/tInisalaryInforLog_update/{tInisalaryInforLogId}")
    @BussinessLog(key = "/tInisalaryInforLog/tInisalaryInforLog_update", type = BussinessLogType.MODIFY, value = "跳转到修改初始工资日志")
    @RequiresPermissions("tInisalaryInforLog:tInisalaryInforLog_update")
    public String tInisalaryInforLogUpdate(@PathVariable String tInisalaryInforLogId, Model model) {
        TInisalaryInforLog tInisalaryInforLog = tInisalaryInforLogService.selectById(tInisalaryInforLogId);
        model.addAttribute("item",tInisalaryInforLog);
        return PREFIX + "tInisalaryInforLog_edit.html";
    }

    /**
     * 获取初始工资日志列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/tInisalaryInforLog/list", type = BussinessLogType.QUERY, value = "获取初始工资日志列表")
    @RequiresPermissions("tInisalaryInforLog:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<TInisalaryInforLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<TInisalaryInforLog> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        String STAFF_ID = super.getPara("STAFF_ID");
        String STAFF = STAFF_ID.replaceAll("[0-9]","");//将所有数字转换为空来判断是否为整型
        List<TInisalaryInforLog> list;
        if(STAFF.length()==0){
            ew.like("STAFF_ID",STAFF_ID);//是整型 搜索员工ID
            list = tInisalaryInforLogService.selectPage(page, ew).getRecords();
        }else{
            ew.like("STAFF_NAME",STAFF_ID);
            list = tInisalaryInforLogService.selectPage(page, ew).getRecords();
        }

        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 新增初始工资日志
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/tInisalaryInforLog/add", type = BussinessLogType.INSERT, value = "新增初始工资日志")
    @RequiresPermissions("tInisalaryInforLog:add")
    @ResponseBody
    public Object add(TInisalaryInforLog tInisalaryInforLog) {
        tInisalaryInforLogService.insert(tInisalaryInforLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除初始工资日志
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/tInisalaryInforLog/delete", type = BussinessLogType.DELETE, value = "删除初始工资日志")
    @RequiresPermissions("tInisalaryInforLog:delete")
    @ResponseBody
    public Object delete(@RequestParam String tInisalaryInforLogId) {
        tInisalaryInforLogService.deleteById(tInisalaryInforLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改初始工资日志
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/tInisalaryInforLog/update", type = BussinessLogType.MODIFY, value = "修改初始工资日志")
    @RequiresPermissions("tInisalaryInforLog:update")
    @ResponseBody
    public Object update(TInisalaryInforLog tInisalaryInforLog) {
        TInisalaryInforLog tInisalaryInforLog1 = tInisalaryInforLogService.selectById(tInisalaryInforLog);
        LogObjectHolder.me().set(tInisalaryInforLog1);
        tInisalaryInforLogService.updateById(tInisalaryInforLog);
        return SUCCESS_TIP;
    }

    /**
     * 初始工资日志详情
     */
    @RequestMapping(value = "/detail/{tInisalaryInforLogId}")
    @BussinessLog(key = "/tInisalaryInforLog/detail", type = BussinessLogType.QUERY, value = "初始工资日志详情")
    @RequiresPermissions("tInisalaryInforLog:detail")
    @ResponseBody
    public Object detail(@PathVariable("tInisalaryInforLogId") String tInisalaryInforLogId) {
        return tInisalaryInforLogService.selectById(tInisalaryInforLogId);
    }
}
