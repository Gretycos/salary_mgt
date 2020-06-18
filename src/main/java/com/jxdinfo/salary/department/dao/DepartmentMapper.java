package com.jxdinfo.salary.department.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.department.model.Department;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
public interface DepartmentMapper extends BaseMapper<Department>{
    List<Department> selectDetailsPage(RowBounds var1, @Param("ew") Wrapper<Department> var2);
}
