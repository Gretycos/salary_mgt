/*
 * 金现代轻骑兵V8开发平台 
 * CustomTagDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类的用途：自定义标签Demo 控制器
 * 创建日期：2018/9/11 9:36 ;
 * 修改历史：
 * 修改日期：2018/9/11 9:36 ;
 * 修改作者：LiangDong ;
 * 修改内容：
 * @author LiangDong ;
 * @version 1.0
 */
@Controller
@RequestMapping("/customTag")
public class CustomTagDemo {

    /**
     * 跳转到自定义标签页面
     * @author      LiangDong
     * @return      java.lang.String
     * @date        2018/9/11 9:38
     */
    @RequestMapping("/view")
    public String index(Model model) {
        model.addAttribute("selectCheck","3");
        model.addAttribute("radioCheck","4");
        model.addAttribute("checkboxCheck","3-4-5");
        return "/hussardemo/customTagDemo.html";
    }

}
