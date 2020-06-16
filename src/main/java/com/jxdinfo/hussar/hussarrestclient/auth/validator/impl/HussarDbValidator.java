/*
 * 金现代轻骑兵V8开发平台 
 * HussarDbValidator.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussarrestclient.auth.validator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.jxdinfo.hussar.bsp.permit.dao.SysInterfaceUserMapper;
import com.jxdinfo.hussar.bsp.permit.dao.SysUsersMapper;
import com.jxdinfo.hussar.bsp.permit.model.SysInterfaceUser;
import com.jxdinfo.hussar.bsp.permit.model.SysUsers;
import com.jxdinfo.hussar.rest.auth.validator.IReqValidator;
import com.jxdinfo.hussar.rest.auth.validator.dto.Credence;

/**
 * 账号密码验证
 *
 * @author WangBinBin
 * @date 2017-08-23 12:34
 */
@Service
public class HussarDbValidator implements IReqValidator {

    /**
     * 用户接口 Mapper 接口
     */
    @Autowired
    SysInterfaceUserMapper interfaceUserMapper;

    /**
     * 用户表 Mapper 接口
     */
    @Autowired
    SysUsersMapper sysUsersMapper;

    @Override
    public boolean validate(Credence credence) {
        String userName = credence.getCredenceName();
        if (StringUtils.isEmpty(userName)) {
            return false;
        }
        boolean flag = false;
        // 验证用户名是否正确
        List<SysInterfaceUser> users = this.interfaceUserMapper
                .selectInterfaceUserListByName(credence.getCredenceName());
        // 验证用户是否是系统管理员
        SysUsers sysUsers = this.sysUsersMapper.selectById("superadmin");
        if (sysUsers.getIsSys().equals("1")) {
            flag = true;
        }
        if (users != null && users.size() > 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
}
