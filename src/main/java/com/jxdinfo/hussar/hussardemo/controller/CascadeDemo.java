/*
 * 金现代轻骑兵V8开发平台 
 * CascadeDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类的用途：字典级联的下拉框Demo
 * 创建日期：2018年12月6日
 * 修改历史：
 * 修改日期：
 * 修改作者：
 * 修改内容：
 * @author zhaopengcheng
 * @version 1.0
 */
@Controller
@RequestMapping("/cascadeDemo")
public class CascadeDemo extends BaseController {

    /**
     * 跳转到级联页面
     * @return String html页面的路径
     */
    @RequestMapping("/returnCascadeDemo")
    public String doReturnCascadeDemo(){
        return "/hussardemo/cascadeDemo.html";
    }
}
