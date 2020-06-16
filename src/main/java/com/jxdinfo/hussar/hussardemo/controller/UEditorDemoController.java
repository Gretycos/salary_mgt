/*
 * 金现代轻骑兵V8开发平台 
 * UEditorDemoController.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxdinfo.hussar.core.base.controller.BaseController;

/**
 * 类的用途：富文本编辑示例<p>
 * 创建日期：2018年11月14日 <br>
 * 修改历史：<br>
 * 修改日期：2018年11月14日 <br>
 * 修改作者：WangBinBin <br>
 * 修改内容：修改内容 <br>
 * @author WangBinBin
 * @version 1.0
 */
@Controller
@RequestMapping("/ueditorDemo")
public class UEditorDemoController extends BaseController {

    /**
     * 返回富文本编辑框页面
     * @Title: index 
     * @author: WangBinBin
     * @return
     */
    @RequestMapping("/view")
    public String index() {
        return "/hussardemo/ueditorDemo.html";
    }

}
