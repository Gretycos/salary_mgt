package com.jxdinfo.salary.entry.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.entry.dao.EntryLogMapper;
import com.jxdinfo.salary.entry.service.IEntryLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.salary.move.dao.MoveLogMapper;
import com.jxdinfo.salary.move.model.MoveLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<EntryLog> selectByDidPage(Page<EntryLog> page, Wrapper<EntryLog> wrapper) {
        wrapper = (Wrapper<EntryLog>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(entryLogMapper.selectByDidPage(page, wrapper));
        return page;
    }

}
