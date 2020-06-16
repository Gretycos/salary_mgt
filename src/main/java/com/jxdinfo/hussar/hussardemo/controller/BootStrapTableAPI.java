/*
 * 金现代轻骑兵V8开发平台 
 * BootStrapTableAPI.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类的用途：BootStrap列表API
 * 创建日期：2019年01月04日
 * 修改历史：
 * 修改日期：2019年01月04日
 * 修改作者：XinXueLei
 * 修改内容：修改内容
 * @author XinXueLei
 * @version 1.0
 */
@RequestMapping("/bootStrapTableAPI")
@Controller
public class BootStrapTableAPI extends BaseController {
    /**
     *
     * BootStrap列表API页面
     * @Title: view
     * @author: XinXueLei
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) {
        return "/hussardemo/bootStrapTableAPI.html";
    }
}
