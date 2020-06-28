package com.jxdinfo.salary.salaryList.dao;

import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 员工每月工资表 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2020-06-18
 */
public interface TMonthlySalaryMapper extends BaseMapper<TMonthlySalary> {
    void updateDeId(Integer departmentId,Integer positionId,Integer staffId);
}
