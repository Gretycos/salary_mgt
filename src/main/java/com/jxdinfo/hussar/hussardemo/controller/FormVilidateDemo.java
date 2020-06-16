/*
 * 金现代轻骑兵V8开发平台 
 * FormVilidateDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxdinfo.hussar.core.base.controller.BaseController;

/**
 * 类的用途：<p>
 * 创建日期：2018年6月4日 <br>
 * 修改历史：<br>
 * 修改日期：2018年6月4日 <br>
 * 修改作者：ChenXin <br>
 * 修改内容：修改内容 <br>
 * @author ChenXin
 * @version 1.0
 */
@Controller
@RequestMapping("formVilidate")
public class FormVilidateDemo extends BaseController {

    /**
     * 
     * 表单验证页面
     * @Title: view 
     * @author: ChenXin
     * @return 页面
     */
    @RequestMapping("view")
    public String view() {
        return "/hussardemo/formVilidate.html";
    }
}
