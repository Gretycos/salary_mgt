package com.jxdinfo.hussar.inisalary.service;

import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.jxdinfo.hussar.inisalary.service.impl.TInisalaryLevelServiceImpl;
import com.jxdinfo.salary.staff.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;

/**
 * <p>
 * 初始工资信息 服务类
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */
public interface ITInisalaryInfoService extends IService<TInisalaryInfo> {


    /**
     * 查询员工初始工资
     */
    int iniSalary(int staffId);

    /**
     * 增加新入职员工信息
     */
    void addEmployee(Staff staff);

}
