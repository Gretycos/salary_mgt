package com.jxdinfo.salary.department.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.dao.DepartmentMapper;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Page<Department> selectDetailsPage(Page<Department> page, Wrapper<Department> wrapper){
        wrapper = (Wrapper<Department>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(departmentMapper.selectDetailsPage(page, wrapper));
        return page;
    }
}
