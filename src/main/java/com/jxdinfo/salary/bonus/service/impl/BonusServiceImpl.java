package com.jxdinfo.salary.bonus.service.impl;

import com.jxdinfo.salary.bonus.model.Bonus;
import com.jxdinfo.salary.bonus.dao.BonusMapper;
import com.jxdinfo.salary.bonus.service.IBonusService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.jxdinfo.salary.staff.model.Staff;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * <p>
 * 员工奖金表 服务实现类
 * </p>
 *
 * @author zyx
 * @since 2020-06-24
 */
@Service
public class BonusServiceImpl extends ServiceImpl<BonusMapper, Bonus> implements IBonusService {
    public void insertNewStaff(Staff staff) {
        Timestamp d = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        Integer month = cal.get(Calendar.MONTH) + 1;
        Bonus bonus = new Bonus(staff.getStaffId(),staff.getStaffName(),
                staff.getDepartment().getDepartmentId(),staff.getPosition().getPositionId(),
                month,d,0,0,0,0);
        insert(bonus);
    }
    public void deleteStaff(Integer staffId) {
        deleteById(staffId);
    }
}
