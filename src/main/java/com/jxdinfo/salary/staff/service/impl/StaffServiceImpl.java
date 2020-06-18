package com.jxdinfo.salary.staff.service.impl;

import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.dao.StaffMapper;
import com.jxdinfo.salary.staff.service.IStaffService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return staffMapper.selectMaxStaffIdByDid(departmentId).getStaffId()+1;
    }
}
