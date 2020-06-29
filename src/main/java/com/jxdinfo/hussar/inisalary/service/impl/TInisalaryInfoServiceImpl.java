package com.jxdinfo.hussar.inisalary.service.impl;

import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.dao.TInisalaryInfoMapper;
import com.jxdinfo.hussar.inisalary.model.TInisalaryInforLog;
import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInforLogService;
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
import java.sql.Timestamp;
import java.util.Date;
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

    @Autowired
    ITInisalaryInforLogService inisalaryInforLogService;

    @Override
    public int iniSalary(int staffId) {
        TInisalaryInfo tInisalaryInfo = selectById(staffId);
        if(tInisalaryInfo!=null){
            int salary = tInisalaryInfo.getInitialSalary();
            return salary;
        }
        return -1;
    }

    public int getLevel(int staffId){
        TInisalaryInfo tInisalaryInfo = selectById(staffId);
        return tInisalaryInfo.getLevel();
    }

    public int getDepId(int staffId){
        TInisalaryInfo tInisalaryInfo = selectById(staffId);
        return tInisalaryInfo.getDepartmentId();
    }

    public int getYears(int staffId){
        TInisalaryInfo tInisalaryInfo = selectById(staffId);
        return tInisalaryInfo.getYears();
    }

    @Override
    public void addEmployee(Staff staff) {


        int sal=itLevel.getInisalary(staff.getDepartment().getDepartmentId().toString(),1+"");
        TInisalaryInfo t = new TInisalaryInfo(staff.getStaffId(),staff.getStaffName(),staff.getDepartment().getDepartmentId(),
                0,1,sal);
        insert(t);

    }

    @Override
    public void deleteEmployee(Staff staff) {
         Integer sal = iniSalary(staff.getStaffId());
         Integer lev = getLevel(staff.getStaffId());
         Date date = new Date();
         Timestamp nousedate = new Timestamp(date.getTime());
         deleteById(staff.getStaffId());
         //更新日志
         TInisalaryInforLog inisalaryInforLog = new TInisalaryInforLog(staff.getStaffId(),staff.getStaffName(),
                 staff.getDepartment().getDepartmentId(),lev,sal,nousedate,0
                 );
         inisalaryInforLogService.insert(inisalaryInforLog);
    }

    @Override
    public void updateEmployee(Staff staff) {
        //获取原先的工资信息
        Integer sal = iniSalary(staff.getStaffId());
        Integer lev = getLevel(staff.getStaffId());
        Integer year = getYears(staff.getStaffId());
        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        int OlddepId = getDepId(staff.getStaffId());

        TInisalaryInforLog inisalaryInforLog = new TInisalaryInforLog(staff.getStaffId(),staff.getStaffName(),
                OlddepId,lev,sal,nousedate,0
        );
        //增加信息到日志
        inisalaryInforLogService.insert(inisalaryInforLog);

        //更新工资表信息
        Integer newSal =itLevel.getInisalary(staff.getDepartment().getDepartmentId().toString(),lev.toString());
        TInisalaryInfo tInisalaryInfo = new TInisalaryInfo(staff.getStaffId(),staff.getStaffName(),staff.getDepartment().getDepartmentId(),
                year,lev,newSal);
        updateById(tInisalaryInfo);

    }
}
