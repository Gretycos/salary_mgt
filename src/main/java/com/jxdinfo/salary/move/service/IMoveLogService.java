package com.jxdinfo.salary.move.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.salary.move.model.MoveLog;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 调动记录表 服务类
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
public interface IMoveLogService extends IService<MoveLog> {
    Page<MoveLog> selectByDidPage(Page<MoveLog> page, Wrapper<MoveLog> wrapper);
}
