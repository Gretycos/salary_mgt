package com.jxdinfo.salary.PermissionManagement.service.impl;

import com.jxdinfo.salary.PermissionManagement.dao.UtilMapper;
import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2020/6/20 10:48
 * @Author: wmk
 * @Description:
 */

@Service
public class UtilServiceImpl implements IUtilService {

    @Autowired
    private UtilMapper utilMapper;

    @Override
    public List<Util> selectList(Long staffId) {
        return utilMapper.selectList(staffId);
    }
}
