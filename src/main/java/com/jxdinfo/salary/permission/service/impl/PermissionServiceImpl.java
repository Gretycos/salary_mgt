package com.jxdinfo.salary.permission.service.impl;

import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.permission.dao.PermissionMapper;
import com.jxdinfo.salary.permission.service.IPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }
}
