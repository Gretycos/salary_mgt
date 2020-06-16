package com.jxdinfo.salary.staff.service.impl;

import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.dao.StaffMapper;
import com.jxdinfo.salary.staff.service.IStaffService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职工表 服务实现类
 * </p>
 *
 * @author hyc
 * @since 2020-06-16
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

}
