package com.jxdinfo.salary.position.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.position.model.Position;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 职位表 服务类
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
public interface IPositionService extends IService<Position> {
    Page<Position> selectDetailsPage(Page<Position> page, Wrapper<Position> wrapper);
}
