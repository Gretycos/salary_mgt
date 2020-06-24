package com.jxdinfo.hussar.inisalary.service.impl;

import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.dao.TInisalaryInfoMapper;
import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;
import com.jxdinfo.salary.staff.model.Staff;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 初始工资信息 服务实现类
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */
@Service
public class TInisalaryInfoServiceImpl extends ServiceImpl<TInisalaryInfoMapper, TInisalaryInfo> implements ITInisalaryInfoService {

    @Autowired
    ITInisalaryLevelService itLevel;

    @Override
    public int iniSalary(int staffId) {
        TInisalaryInfo tInisalaryInfo = selectById(staffId);
        if(tInisalaryInfo!=null){
            int salary = tInisalaryInfo.getInitialSalary();
            return salary;
        }
        return -1;
    }

    @Override
    public void addEmployee(Staff staff) {


        int sal=itLevel.getInisalary(staff.getDepartment().getDepartmentId().toString(),1+"");
        TInisalaryInfo t = new TInisalaryInfo(staff.getStaffId(),staff.getStaffName(),staff.getDepartment().getDepartmentId(),
                0,1,sal);
        insert(t);

    }
}
