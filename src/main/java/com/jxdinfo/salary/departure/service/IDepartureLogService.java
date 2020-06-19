package com.jxdinfo.salary.departure.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.baomidou.mybatisplus.service.IService;
import com.jxdinfo.salary.entry.model.EntryLog;

/**
 * <p>
 * 离职记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-19
 */
public interface IDepartureLogService extends IService<DepartureLog> {

    Page<DepartureLog> selectByDidPage(Page<DepartureLog> page, Wrapper<DepartureLog> wrapper);

}
