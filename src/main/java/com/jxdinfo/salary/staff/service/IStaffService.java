package com.jxdinfo.salary.staff.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 职工表 服务类
 * </p>
 *
 * @author hyc
 * @since 2020-06-16
 */

public interface IStaffService extends IService<Staff>{
    int getNewStaffId(int departmentId);
    Set<String> getGenderSet(Wrapper<Staff> ew);
    Set<Department> getDepartmentSet(Wrapper<Staff> ew);
    Set<Position> getPositionSet(Wrapper<Staff> ew);
    Set<String> getEntryTimeSet(Wrapper<Staff> ew);
    Set<String> getDepartureTimeSet(Wrapper<Staff> ew);
}
