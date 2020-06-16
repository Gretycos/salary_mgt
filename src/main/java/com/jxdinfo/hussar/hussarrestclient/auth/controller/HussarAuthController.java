/*
 * 金现代轻骑兵V8开发平台 
 * HussarAuthController.java 
 * 版权所有：金现代信息产业股份有限公司  Copyright (c) 2018-2023 .
 * 金现代信息产业股份有限公司保留所有权利,未经允许不得以任何形式使用.
 */
package com.jxdinfo.hussar.hussarrestclient.auth.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jxdinfo.hussar.bsp.permit.service.impl.SysInterfaceServiceImpl;
import com.jxdinfo.hussar.hussarrestclient.auth.validator.impl.HussarDbValidator;
import com.jxdinfo.hussar.rest.auth.controller.dto.AuthRequest;
import com.jxdinfo.hussar.rest.auth.controller.dto.AuthResponse;
import com.jxdinfo.hussar.rest.auth.controller.dto.ExceptionResponse;
import com.jxdinfo.hussar.rest.auth.util.JwtTokenUtil;
import com.jxdinfo.hussar.rest.auth.validator.IReqValidator;
import com.jxdinfo.hussar.rest.common.exception.BizExceptionEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 请求验证的
 *
 * @author WangBinBin
 * @Date 2017/8/24 14:22
 */
@RestController
@Api(value = "HussarAuthController", description = "用户验证获取验证令牌")
public class HussarAuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(type = HussarDbValidator.class)
    private IReqValidator reqValidator;

    @Resource
    private SysInterfaceServiceImpl sysInterfaceService;

    @RequestMapping(value = "/demoAuth")
    @ApiOperation(value = "获取验证令牌", notes = "获取验证令牌", httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {

        // 验证用户是不是符合认证规则
        boolean validate = this.reqValidator.validate(authRequest);

        if (validate) {
            final String randomKey = this.jwtTokenUtil.getRandomKey();
            final String token = this.jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            // 生效时间
            Date issuedAtDate = this.jwtTokenUtil.getIssuedAtDateFromToken(token);
            // 失效时间
            Date expirationDate = this.jwtTokenUtil.getExpirationDateFromToken(token);
            // 用户名
            String userName = this.jwtTokenUtil.getUsernameFromToken(token);
            // 修改接口用户信息
            this.sysInterfaceService.updateInterfaceUser(userName, issuedAtDate, expirationDate, token);

            // 返回成功标识以及token信息
            return ResponseEntity.ok(new AuthResponse(token, randomKey, issuedAtDate.getTime(),
                    expirationDate.getTime() - issuedAtDate.getTime(), 200));
        } else {
            // 如果认证失败返回认证错误信息
            return ResponseEntity.ok(new ExceptionResponse(BizExceptionEnum.AUTH_REQUEST_ERROR.getCode(),
                    BizExceptionEnum.AUTH_REQUEST_ERROR.getMessage()));
        }
    }
}
