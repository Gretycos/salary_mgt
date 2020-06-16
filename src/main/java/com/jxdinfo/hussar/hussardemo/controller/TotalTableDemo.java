/*
 * 金现代轻骑兵V8开发平台 
 * TotalTableDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jxdinfo.hussar.common.dicutil.DictionaryUtil;
import com.jxdinfo.hussar.core.page.HussarPager;
import com.jxdinfo.hussar.core.util.ToolUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.bsp.permit.model.SysUsers;
import com.jxdinfo.hussar.bsp.permit.service.ISysUsersService;
import com.jxdinfo.hussar.core.base.controller.BaseController;

/**
 * 类的用途：合计行控制层<p>
 * 创建日期：2018年6月13日 <br>
 * 修改历史：<br>
 * 修改日期：2018年6月13日 <br>
 * 修改作者：ChenXin <br>
 * 修改内容：修改内容 <br>
 * @author ChenXin
 * @version 1.0
 */
@Controller
@RequestMapping("/totalTableDemo")
public class TotalTableDemo extends BaseController {

    /**
     * 用户管理服务 接口
     */
    @Resource
    private ISysUsersService iSysUsersService;
    /**
     * 字典相关的工具类
     */
    @Resource
    private DictionaryUtil dictionaryUtil;

    /**
     * 
     * 合计行页面
     * @Title: view 
     * @author: ChenXin
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) {
        model.addAttribute("isAdmin",dictionaryUtil.getDictListJsonObjectByType("yes_no"));
        return "/hussardemo/totalTableDemo.html";
    }

    /**
     * 
     * 可编辑列表数据
     * @Title: list 
     * @author: ChenXin
     * @return Object
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestBody HussarPager<SysUsers> hussarPager) {
        SysUsers sysUsers = hussarPager.getQuery();
        Page<SysUsers> page = new Page(hussarPager.getPageNumber(), hussarPager.getPageSize());
        String userAccount = "";
        String userName = "";
        if (ToolUtil.isNotEmpty(sysUsers)) {
            userAccount = sysUsers.getUserAccount();
            userName = sysUsers.getUserName();
        }
        page = this.iSysUsersService.getUserList(page, userAccount, userName);
        return super.packForBT(page);
    }
}
