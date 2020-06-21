package com.jxdinfo.hussar.inisalary.service.impl;

import com.jxdinfo.hussar.inisalary.model.TInisalaryInfo;
import com.jxdinfo.hussar.inisalary.dao.TInisalaryInfoMapper;
import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxdinfo.hussar.inisalary.service.ITInisalaryLevelService;

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
    public void addEmployee(int staffId,String staffName,int departmentId) {
        TInisalaryInfo t = new TInisalaryInfo();
        t.setStaffId(staffId);
        t.setStaffName(staffName);
        t.setDepartmentId(departmentId);
        t.setLevel(1);
        t.setYears(0);
        int sal=itLevel.getInisalary(departmentId+"",1+"");
        t.setInitialSalary(sal);
        insert(t);
    }
}
