/*
 * 金现代轻骑兵V8开发平台 
 * IdCardCheckDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.common.projecttool.IDCardUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类的用途：身份证号验证Demo
 * 创建日期：2018年10月18日
 * 修改历史：
 * 修改日期：2018年10月18日
 * 修改作者：XinXueLei
 * 修改内容：修改内容
 * @author XinXueLei
 * @version 1.0
 */
@RequestMapping("/idCardCheckDemo")
@Controller
public class IdCardCheckDemo extends BaseController {
    /**
     *
     * 身份证号验证demo页面
     * @Title: view
     * @author: XinXueLei
     * @return 页面
     */
    @RequestMapping("/view")
    public String view() {
        return "/hussardemo/idCardCheckDemo.html";
    }

    /**
     *
     * 校验
     * @author: XinXueLei
     * @return boolean
     */
    @RequestMapping("/check")
    @ResponseBody
    public boolean check() {
        String idCard = super.getPara("idCardNum");
        return IDCardUtil.checkCardId(idCard);
    }
}
