package com.jxdinfo.salary.entry.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.entry.dao.EntryLogMapper;
import com.jxdinfo.salary.entry.service.IEntryLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.salary.move.dao.MoveLogMapper;
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.staff.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 入职记录表 服务实现类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
@Service
public class EntryLogServiceImpl extends ServiceImpl<EntryLogMapper, EntryLog> implements IEntryLogService {

    @Autowired
    EntryLogMapper entryLogMapper;

    //模糊查询
    @Override
    public List<EntryLog> likeSelect(String condition1, String condition2, String condition3) {
        return entryLogMapper.likeSelect(condition1, condition2, condition3);
    }

    //添加入职记录
    @Override
    public boolean addEntryLog(Staff operator, Staff entrant, Timestamp entryTime){
        EntryLog entryLogWithMaxId = entryLogMapper.selectMaxOperationId(
                new SimpleDateFormat("yyyyMMdd").format(entryTime)); //传入日期匹配出最大Id的记录
        EntryLog entryLog; // 新记录
        if(entryLogWithMaxId!=null){ // 如果有最大记录
            long operationId = Long.parseLong(entryLogWithMaxId.getOperationId());
            String newOperationId = String.valueOf(operationId+1); //最大Id+1
            entryLog = new EntryLog(newOperationId,operator,entrant,entryTime);
        }else{ // 如果没有最大记录
            entryLog = new EntryLog(operator,entrant,entryTime); //生成当前时间的第一个Id
        }

        return insert(entryLog); //调用内置的插入
    }

    @Override
    public Page<EntryLog> selectByDidPage(Page<EntryLog> page, Wrapper<EntryLog> wrapper) {
        wrapper = (Wrapper<EntryLog>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(entryLogMapper.selectByDidPage(page, wrapper));
        return page;
    }

}
