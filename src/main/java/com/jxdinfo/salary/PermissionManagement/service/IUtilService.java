package com.jxdinfo.salary.PermissionManagement.service;

import com.jxdinfo.salary.PermissionManagement.model.Util;

import java.util.List;

/**
 * @Date: 2020/6/20 10:47
 * @Author: wmk
 * @Description:
 */
public interface IUtilService {

    // 根据员工Id查询员工真实权限列表 并且将超级管理的权限进行展开后 进行白名单-黑名单
    List<Util> selectList(Long staffId);
}
