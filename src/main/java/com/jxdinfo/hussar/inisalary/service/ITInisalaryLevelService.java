package com.jxdinfo.hussar.inisalary.service;

import com.jxdinfo.hussar.inisalary.model.TInisalaryLevel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */
public interface ITInisalaryLevelService extends IService<TInisalaryLevel> {


    TInisalaryLevel selectByTwo(String dep,String lev);
    int getInisalary(String dep, String lev);

}
