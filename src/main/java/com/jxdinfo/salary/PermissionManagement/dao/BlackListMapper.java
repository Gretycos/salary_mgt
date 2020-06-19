package com.jxdinfo.salary.PermissionManagement.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 黑名单表 Mapper 接口
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
public interface BlackListMapper extends BaseMapper<BlackList> {

    List<BlackList> selectPage(RowBounds rowBounds, @Param("ew") Wrapper<BlackList> wrapper);

    //获取员工工号
    List<Integer> selectStaffId();

    // 获取员工姓名
    List<String> selectStaffName();

    // 获取部门名称
    List<String> selectDepartmentName();

    // 获取权限名称
    List<String> selectPermissionName();

    // 根据三个主键批量删除
    Boolean deleteBatchByIds(List<BlackList> list);

    //重写update方法
    Integer update(@Param("et") BlackList var1, @Param("ew") Wrapper<BlackList> var2);

}
