package com.jxdinfo.salary.department.service.impl;

import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.dao.DepartmentMapper;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
