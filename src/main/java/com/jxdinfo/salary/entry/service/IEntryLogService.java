package com.jxdinfo.salary.entry.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.staff.model.Staff;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 入职记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface IEntryLogService extends IService<EntryLog> {

    //模糊查询
    List<EntryLog> likeSelect(Page<EntryLog> page,String condition1, String condition2, String condition3);

    //添加入职记录 对外接口
    boolean addEntryLog(Staff operator, Staff entrant, Timestamp entryTime);

    Page<EntryLog> selectByDidPage(Page<EntryLog> page, Wrapper<EntryLog> wrapper);

}
