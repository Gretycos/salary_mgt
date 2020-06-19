package com.jxdinfo.salary.permission.service;

import com.jxdinfo.salary.permission.model.Permission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-19
 */
public interface IPermissionService extends IService<Permission> {

    // 查询所有的权限信息
    List<Permission> getAllPermission();
}
