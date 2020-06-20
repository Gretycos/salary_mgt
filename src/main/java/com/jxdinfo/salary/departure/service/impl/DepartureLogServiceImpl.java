package com.jxdinfo.salary.departure.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.departure.dao.DepartureLogMapper;
import com.jxdinfo.salary.departure.service.IDepartureLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.salary.entry.dao.EntryLogMapper;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.staff.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 离职记录表 服务实现类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
@Service
public class DepartureLogServiceImpl extends ServiceImpl<DepartureLogMapper, DepartureLog> implements IDepartureLogService {

    @Autowired
    DepartureLogMapper departureLogMapper;

    //模糊查询
    @Override
    public List<DepartureLog> likeSelect(String condition1,String condition2,String condition3) {
        return departureLogMapper.likeSelect(condition1, condition2, condition3);
    }

    //添加离职记录
    @Override
    public void addDepartureLog(Staff operator, Staff departure, Timestamp departureTime){
        departureLogMapper.addDepartureLogRecord(departureLog);
    }

    @Override
    public Page<DepartureLog> selectByDidPage(Page<DepartureLog> page, Wrapper<DepartureLog> wrapper) {
        wrapper = (Wrapper<DepartureLog>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(departureLogMapper.selectByDidPage(page, wrapper));
        return page;
    }

}
