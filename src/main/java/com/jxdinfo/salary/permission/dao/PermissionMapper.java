package com.jxdinfo.salary.permission.dao;

import com.jxdinfo.salary.permission.model.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-19
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    // 查询所有的权限信息
    List<Permission> getAllPermission();

}
