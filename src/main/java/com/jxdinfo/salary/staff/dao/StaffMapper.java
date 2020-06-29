package com.jxdinfo.salary.staff.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 职工表 Mapper 接口
 * </p>
 *
 * @author hyc
 * @since 2020-06-16
 */
public interface StaffMapper extends BaseMapper<Staff> {
    int selectMaxStaffIdByDid(@Param("departmentId")int departmentId);
    Set<String> getGenderSet(@Param("ew") Wrapper<Staff> ew);
    Set<Department> getDepartmentSet(@Param("ew") Wrapper<Staff> ew);
    Set<Position> getPositionSet(@Param("ew") Wrapper<Staff> ew);
    Set<String> getEntryTimeSet(@Param("ew") Wrapper<Staff> ew);
    Set<String> getDepartureTimeSet(@Param("ew") Wrapper<Staff> ew);
}
