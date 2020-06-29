package com.jxdinfo.salary.bonus.service;

import com.jxdinfo.salary.bonus.model.Bonus;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.staff.model.Staff;

/**
 * <p>
 * 员工奖金表 服务类
 * </p>
 *
 * @author zyx
 * @since 2020-06-24
 */
public interface IBonusService extends IService<Bonus> {
    void insertNewStaff(Staff staff);
    void deleteStaff(Integer staffId);
}
