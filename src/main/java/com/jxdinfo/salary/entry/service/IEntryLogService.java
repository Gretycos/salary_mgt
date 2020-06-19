package com.jxdinfo.salary.entry.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.move.model.MoveLog;

/**
 * <p>
 * 入职记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface IEntryLogService extends IService<EntryLog> {

    Page<EntryLog> selectByDidPage(Page<EntryLog> page, Wrapper<EntryLog> wrapper);

}
