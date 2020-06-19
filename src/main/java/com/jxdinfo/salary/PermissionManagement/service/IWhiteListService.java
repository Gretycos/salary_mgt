package com.jxdinfo.salary.PermissionManagement.service;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 白名单表 服务类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
public interface IWhiteListService extends IService<WhiteList> {

    //获取员工工号
    List<Integer> selectStaffId();

    // 获取员工姓名
    List<String> selectStaffName();

    // 获取部门名称
    List<String> selectDepartmentName();

    // 获取权限名称
    List<String> selectPermissionName();

    // 根据条件进行查询
    List<WhiteList> searchByCondition(String condition);

//    Page<WhiteList> selectPage(String condition,Page<WhiteList> page, Wrapper<WhiteList> wrapper);

    // 根据三个主键批量删除
    Boolean deleteBatchByIds(List<WhiteList> list);
}


