package com.jxdinfo.salary.staff.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jxdinfo.salary.staff.model.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 职工表 Mapper 接口
 * </p>
 *
 * @author hyc
 * @since 2020-06-16
 */
public interface StaffMapper extends BaseMapper<Staff> {
    Staff selectMaxStaffIdByDid(@Param("departmentId")int departmentId);
}
