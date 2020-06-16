package com.jxdinfo.salary.PermissionManagement.service.impl;

import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.PermissionManagement.dao.BlackListMapper;
import com.jxdinfo.salary.PermissionManagement.service.IBlackListService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单表 服务实现类
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
@Service
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements IBlackListService {

}
