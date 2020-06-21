package com.jxdinfo.salary.PermissionManagement.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.permission.model.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 白名单表 Mapper 接口
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
public interface WhiteListMapper extends BaseMapper<WhiteList> {

    List<WhiteList> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<WhiteList> wrapper);

    //获取员工工号
    List<Integer> selectStaffId();

    // 获取员工姓名
    List<String> selectStaffName();

    // 获取部门名称
    List<String> selectDepartmentName( @Param("ew") Wrapper<WhiteList> var1);

    // 获取权限名称
    List<String> selectPermissionName( @Param("ew") Wrapper<WhiteList> var1);

    // 根据查询条件进行查询 条件是员工的工号或者姓名
    List<WhiteList> searchByCondition(String condition);

    // 批量删除
    Boolean deleteBatchByIds(List<WhiteList> list);

    //重写update方法
    Integer update(@Param("et") WhiteList var1, @Param("ew") Wrapper<WhiteList> var2);

    // 重写insert方法
    Integer insert(WhiteList whiteList);

    // 重写selectList方法
    List<WhiteList> selectList(@Param("ew") Wrapper<WhiteList> var1);
}
