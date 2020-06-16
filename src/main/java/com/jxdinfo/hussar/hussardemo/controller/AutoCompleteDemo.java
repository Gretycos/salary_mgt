/*
 * 金现代轻骑兵V8开发平台 
 * AutoCompleteDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.jxdinfo.hussar.common.autocomplete.model.Autocompelte;
import com.jxdinfo.hussar.common.autocomplete.service.AutocompleteService;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 类的用途：联想Demo
 * 创建日期：2018/6/6 9:15 ;
 * 修改历史：
 * 修改日期：2018/6/6 9:15 ;
 * 修改作者：ChenChang ;
 * 修改内容：
 * @author ChenChang ;
 * @version 1.0
 */
@Controller
@RequestMapping("/autocomplete")
public class AutoCompleteDemo extends BaseController {

    /**
     * 跳转到联想查询页
     * @Title: view
     * @author: Chen chang
     * @return 页面
     */
    @RequestMapping("/view")
    public String index() {
        return "/hussardemo/autoCompleteDemo.html";
    }

     /**
      * 联想查询service接口
      */
    @Autowired
    private AutocompleteService autocompleteService;

    /**
     * @Description:此demo以  SYS_USERS 表为例
     * @Author: Chen chang
     * @param term
     * @return
     */
    @RequestMapping(value = "/auto")
    @ResponseBody
    public Object auto(String term){
        String table= "SYS_USERS";
        String label ="USER_NAME";
        String value="USER_ID";
        if (null!=term&&!("".equals(term))){
            List<Autocompelte> userList=autocompleteService.inputPrompt(table,label,value,term);
            return userList;
        }else {
            return "error";
        }
    }

}
