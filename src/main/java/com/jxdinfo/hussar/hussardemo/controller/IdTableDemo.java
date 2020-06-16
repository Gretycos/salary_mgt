/*
 * 金现代轻骑兵V8开发平台 
 * IdTableDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jxdinfo.hussar.bsp.permit.model.SysIdtable;
import com.jxdinfo.hussar.bsp.permit.service.ISysIdtableService;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.bsp.permit.service.TableService;

import java.sql.SQLException;

/**
 * 类的用途：<p>
 * 创建日期：2018年5月29日 <br>
 * 修改历史：<br>
 * 修改日期：2018年5月29日 <br>
 * 修改作者：ChenXin <br>
 * 修改内容：修改内容 <br>
 * @author ChenXin
 * @version 1.0
 */
@RequestMapping("/idTableDemo")
@Controller
public class IdTableDemo extends BaseController {

    /**
     * 获取表名接口
     */
    @Resource
    private TableService tableService;

    /**
     * 最大号表接口
     */
    @Resource
    ISysIdtableService sysIdtableService;

    /**
     *
     * 最大号表demo页面
     * @Title: view
     * @author: ChenXin
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) throws SQLException {
        model.addAttribute("tables", tableService.getTables());
        model.addAttribute("operation", sysIdtableService.selectList(new EntityWrapper<SysIdtable>()));
        return "/hussardemo/idTableDemo.html";
    }

    /**
     *
     * 获取最大号表序号
     * @Title: getNumber
     * @author: ChenXin
     * @return String
     */
    @RequestMapping("/getNumber")
    @ResponseBody
    public String getNumber() {
        String idTable = super.getPara("idTable").toLowerCase();
        String operation = super.getPara("operation");
        String currentCode = sysIdtableService.getCurrentCode(operation, idTable);
        return currentCode;
    }

    /**
    *
    * 获取最大号表序号
    * @Title: getNumber
    * @author: ChenXin
    * @return String
    */
    @RequestMapping("/getLevel")
    @ResponseBody
    public String getLevel() {
        String idTable = super.getPara("idTable").toLowerCase();
        String operation = super.getPara("operation");
        String parentNumber = super.getPara("parentNumber");
        String currentCode = sysIdtableService.getCurrentCode(operation, idTable, parentNumber);
        return currentCode;
    }
}
