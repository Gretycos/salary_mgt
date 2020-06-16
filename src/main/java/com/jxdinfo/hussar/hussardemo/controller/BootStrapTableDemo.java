/*
 * 金现代轻骑兵V8开发平台 
 * BootStrapTableDemo.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussardemo.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.bsp.permit.model.SysUsers;
import com.jxdinfo.hussar.bsp.permit.service.ISysUsersService;
import com.jxdinfo.hussar.common.dicutil.DictionaryUtil;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.page.HussarPager;
import com.jxdinfo.hussar.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 类的用途：BootStrap列表Demo
 * 创建日期：2018年12月19日
 * 修改历史：
 * 修改日期：2018年12月19日
 * 修改作者：XinXueLei
 * 修改内容：修改内容
 * @author XinXueLei
 * @version 1.0
 */
@RequestMapping("/bootStrapTableDemo")
@Controller
public class BootStrapTableDemo extends BaseController {
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
     * BootStrap列表Demo页面
     * @Title: view
     * @author: XinXueLei
     * @return 页面
     */
    @RequestMapping("/view")
    public String view(Model model) {
        model.addAttribute("isAdmin",dictionaryUtil.getDictListJsonObjectByType("yes_no"));
        return "/hussardemo/bootStrapTableDemo.html";
    }

    @RequestMapping("/userList")
    @ResponseBody
    public Object userTableList(@RequestBody HussarPager<SysUsers> hussarPager) {
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
