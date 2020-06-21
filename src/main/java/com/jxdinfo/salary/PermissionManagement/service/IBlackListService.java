package com.jxdinfo.salary.PermissionManagement.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 黑名单表 服务类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
public interface IBlackListService extends IService<BlackList> {

    //获取员工工号
    List<Integer> selectStaffId();

    // 获取员工姓名
    List<String> selectStaffName();

    // 获取部门名称
    List<String> selectDepartmentName(@Param("ew") Wrapper<BlackList> var1);

    // 获取权限名称
    List<String> selectPermissionName(@Param("ew") Wrapper<BlackList> var1);

    // 根据三个主键批量删除
    Boolean deleteBatchByIds(List<BlackList> list);
}
