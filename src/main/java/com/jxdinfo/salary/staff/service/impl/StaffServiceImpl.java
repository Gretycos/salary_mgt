package com.jxdinfo.salary.staff.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.dao.StaffMapper;
import com.jxdinfo.salary.staff.service.IStaffService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 职工表 服务实现类
 * </p>
 *
 * @author hyc
 * @since 2020-06-16
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper,Staff> implements IStaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public int getNewStaffId(int departmentId) {
        return staffMapper.selectMaxStaffIdByDid(departmentId)+1;
    }

    @Override
    public Set<String> getGenderSet(Wrapper<Staff> ew) {
        return staffMapper.getGenderSet(ew);
    }

    @Override
    public Set<Department> getDepartmentSet(Wrapper<Staff> ew) {
        return staffMapper.getDepartmentSet(ew);
    }

    @Override
    public Set<Position> getPositionSet(Wrapper<Staff> ew) {
        return staffMapper.getPositionSet(ew);
    }

    @Override
    public Set<String> getEntryTimeSet(Wrapper<Staff> ew) {
        return staffMapper.getEntryTimeSet(ew);
    }

    @Override
    public Set<String> getDepartureTimeSet(Wrapper<Staff> ew) {
        return staffMapper.getDepartureTimeSet(ew);
    }
}
