/*
 * 金现代轻骑兵V8开发平台 
 * MultiSelectDemoController.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类的用途：多选下拉框Demo
 * 创建日期：2018/12/3 9:31;
 * 修改历史：
 * 修改日期：2018/12/3 9:31;
 * 修改作者：zhaopengcheng ;
 * 修改内容：
 * @author zhaopengcheng ;
 * @version 1.0
 */
@Controller
@RequestMapping("/multiSelectDemoController")
public class MultiSelectDemoController extends BaseController{

    /**
     * 跳转到多选下拉框页面
     * @param model 传值
     * @return String
     */
    @RequestMapping("/returnMultiSelect")
    public String doReturnMultiSelectM(Model model){
        model.addAttribute("selectCheck",'3');
        return "/hussardemo/multiSelectDemo.html";
    }
}
