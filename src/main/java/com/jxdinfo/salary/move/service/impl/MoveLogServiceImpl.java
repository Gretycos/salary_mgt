package com.jxdinfo.salary.move.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.move.dao.MoveLogMapper;
import com.jxdinfo.salary.move.service.IMoveLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 调动记录表 服务实现类
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
@Service
public class MoveLogServiceImpl extends ServiceImpl<MoveLogMapper, MoveLog> implements IMoveLogService {

    @Autowired
    MoveLogMapper moveLogMapper;

    @Override
    public Page<MoveLog> selectByDidPage(Page<MoveLog> page, Wrapper<MoveLog> wrapper) {
        wrapper = (Wrapper<MoveLog>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(moveLogMapper.selectByDidPage(page, wrapper));
        return page;
    }
}
