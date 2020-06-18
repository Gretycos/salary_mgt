package com.jxdinfo.salary.department.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.department.model.Department;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
public interface IDepartmentService extends IService<Department> {
    Page<Department> selectDetailsPage(Page<Department> page, Wrapper<Department> wrapper);
}
