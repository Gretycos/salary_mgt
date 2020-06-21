package com.jxdinfo.salary.PermissionManagement.service.impl;

import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.salary.PermissionManagement.dao.UtilMapper;
import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2020/6/20 10:48
 * @Author: wmk
 * @Description:
 */

@Service
public class UtilServiceImpl implements IUtilService {

    @Autowired
    private UtilMapper utilMapper;


    @Override
    public List<Util> selectList(Long staffId) {
        return utilMapper.selectList(staffId);
    }


    @Override
    public Object getPermissionList(){
        System.out.println("===========================================");
        System.out.println("=================权限查询开始================");
        ShiroUser user = ShiroKit.getUser();
        try {
            Long staffId = Long.parseLong(user.getAccount());
            // 根据staffId 得到真实权限
            // 先将黑白名单的超级管理权限展开 然后取差集
            List<Util> utilList = selectList(staffId);
            System.out.println("=================权限列表查询结束================");
            return utilList;
        }catch (NumberFormatException e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("=================权限列表查询结束================");
            return "获取员工工号失败:类型转化错误。"+"当前登录用户:"+user.getAccount()+",无法获取工号";
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.out.println("=================权限列表查询结束================");
            return "未知异常";
        }
    }
}
