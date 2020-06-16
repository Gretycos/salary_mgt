/*
 * 金现代轻骑兵V8开发平台 
 * WorkCalendarDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.common.calendarutil.WorkCalendarUtil;
import com.jxdinfo.hussar.common.dicutil.DictionaryUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类的用途：工作日历Demo 控制器
 * 创建日期：2018/5/29 13:41 ;
 * 修改历史：
 * 修改日期：2018/5/29 13:41 ;
 * 修改作者：LiangDong ;
 * 修改内容：
 * @author LiangDong ;
 * @version 1.0
 */
@Controller
@RequestMapping("/workCalendarDemo")
public class WorkCalendarDemo extends BaseController {

    /**
     * 字典工具接口
     */
    @Resource
    private DictionaryUtil dictionaryUtil;

    /**
     *  工作日历工具接口
     */
    @Resource
    private WorkCalendarUtil workCalendarUtil;

    /**
     * 返回工作日历Demo
     * @author      LiangDong
     * @return      java.lang.String
     * @date        2018/5/29 18:01
     */
    @RequestMapping("/view")
    @RequiresPermissions("workCalendarDemo:view")
    public String index() {
        return "/hussardemo/workCalendarDemo.html";
    }

    /**
     * 返回“是否”下拉框数据字典值
     * @author      LiangDong
     * @return      java.lang.Object
     * @date        2018/5/29 18:02
     */
    @RequestMapping("/options")
    @ResponseBody
    public Object getSelectOptions() {
        List<Map<String,Object>> list = this.dictionaryUtil.getDictListByType("yes_no");
        return list;
    }

    /**
     * 判断某一天是否是工作日
     * @author      LiangDong
     * @return      boolean
     * @date        2018/5/30 10:34
     */
    @RequestMapping("/isWorkDay")
    @ResponseBody
    public boolean isWorkDay() {
        String date = super.getPara("date").trim();
        boolean flag = workCalendarUtil.isWorkDay(date);
        return flag;
    }

    /**
     * 获取某月工作日天数
     * @author      LiangDong
     * @return      int
     * @date        2018/5/30 10:35
     */
    @RequestMapping("/workDayNum")
    @ResponseBody
    public int getWorkDayNum() {
        String month = super.getPara("month").trim();
        return workCalendarUtil.getWorkDayNum(month);
    }

    /**
     * 获取某月休息日天数
     * @author      LiangDong
     * @return      int
     * @date        2018/5/30 10:35
     */
    @RequestMapping("/holidayNum")
    @ResponseBody
    public int getHolidayNum() {
        String month = super.getPara("month").trim();
        return workCalendarUtil.getHolidayNum(month);
    }

}
