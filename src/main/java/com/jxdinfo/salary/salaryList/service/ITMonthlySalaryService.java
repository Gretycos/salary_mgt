package com.jxdinfo.salary.salaryList.service;

import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.staff.model.Staff;

/**
 * <p>
 * 员工每月工资表 服务类
 * </p>
 *
 * @author wx
 * @since 2020-06-18
 */
public interface ITMonthlySalaryService extends IService<TMonthlySalary> {
    void insertNewStaff(Staff staff);
    void deleteStaff(Integer staffId);
    void updateDeId(Integer departmentId,Integer positionId,Integer staffId);
}
