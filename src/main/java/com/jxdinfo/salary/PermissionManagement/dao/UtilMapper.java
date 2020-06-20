package com.jxdinfo.salary.PermissionManagement.dao;

import com.jxdinfo.salary.PermissionManagement.model.Util;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2020/6/20 10:48
 * @Author: wmk
 * @Description:
 */
@Component
public interface UtilMapper {
    List<Util> selectList(Long staffId);
}
