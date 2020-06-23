package com.jxdinfo.salary.OutSalary.service;

import com.jxdinfo.salary.OutSalary.model.MonthlySalary;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 * 员工每月工资表 服务类
 * </p>
 *
 * @author 
 * @since 2020-06-17
 */
public interface IMonthlySalaryService extends IService<MonthlySalary> {
    ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response,List<MonthlySalary> list1);

}
