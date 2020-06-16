package com.jxdinfo.salary.test.service.impl;

import com.jxdinfo.salary.test.model.Test;
import com.jxdinfo.salary.test.dao.TestMapper;
import com.jxdinfo.salary.test.service.ITestService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zwl
 * @since 2020-06-10
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
