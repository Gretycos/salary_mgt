/*
 * 金现代轻骑兵V8开发平台 
 * ChangeNumToWordDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.common.projecttool.NumToWordUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 类的用途：数字转换为人民币Demo
 * 创建日期：2018年10月18日
 * 修改历史：
 * 修改日期：2018年10月18日
 * 修改作者：XinXueLei
 * 修改内容：修改内容
 * @author XinXueLei
 * @version 1.0
 */
@RequestMapping("/changeNumToWordDemo")
@Controller
public class ChangeNumToWordDemo extends BaseController {
    /**
     *
     * 数字转换为人民币Ddemo页面
     * @Title: view
     * @author: XinXueLei
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) {
        return "/hussardemo/changeNumToWordDemo.html";
    }

    /**
     *
     * 转换
     * @author: XinXueLei
     * @return String
     */
    @RequestMapping("/change")
    @ResponseBody
    public String change() {
        String amountNum = super.getPara("amountNum");
        String amountWord = NumToWordUtil.convert(new BigDecimal(amountNum));
        return amountWord;
    }
}
