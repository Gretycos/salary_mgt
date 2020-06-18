package com.jxdinfo.salary.position.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.position.dao.PositionMapper;
import com.jxdinfo.salary.position.service.IPositionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public Page<Position> selectDetailsPage(Page<Position> page, Wrapper<Position> wrapper) {
        wrapper = (Wrapper<Position>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(positionMapper.selectDetailsPage(page, wrapper));
        return page;
    }
}
