package com.jxdinfo.salary.entry.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.staff.model.Staff;

import java.sql.Timestamp;

/**
 * <p>
 * 入职记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface IEntryLogService extends IService<EntryLog> {

    //添加入职记录 对外接口
    boolean addEntryLog(Staff operator, Staff entrant, Timestamp entryTime);

    Page<EntryLog> selectByDidPage(Page<EntryLog> page, Wrapper<EntryLog> wrapper);

}
