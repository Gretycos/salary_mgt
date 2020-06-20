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
import java.text.SimpleDateFormat;
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
    public boolean addDepartureLog(Staff operator, Staff departure, Timestamp departureTime){
        DepartureLog departureLogWithMaxId = departureLogMapper.selectMaxOperationId(
                new SimpleDateFormat("yyyyMMdd").format(departureTime)); //传入日期匹配出最大Id的记录
        DepartureLog departureLog; // 新记录
        if(departureLogWithMaxId!=null){ // 如果有最大记录
            long operationId = Long.parseLong(departureLogWithMaxId.getOperationId());
            String newOperationId = String.valueOf(operationId+1); //最大Id+1
            departureLog = new DepartureLog(newOperationId,operator,departure,departureTime);
        }else{ // 如果没有最大记录
            departureLog = new DepartureLog(operator,departure,departureTime); //生成当前时间的第一个Id
        }
        // 内置的插入
        /*
        * public boolean insert(T entity) {
            return retBool(this.baseMapper.insert(entity));
          }
        * */
        return insert(departureLog); //调用内置的插入
    }

    @Override
    public Page<DepartureLog> selectByDidPage(Page<DepartureLog> page, Wrapper<DepartureLog> wrapper) {
        wrapper = (Wrapper<DepartureLog>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(departureLogMapper.selectByDidPage(page, wrapper));
        return page;
    }

}
