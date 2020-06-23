package com.jxdinfo.salary.salaryList.test;

import com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper;
import com.jxdinfo.salary.salaryList.model.TBonus;
import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.jxdinfo.salary.salaryList.service.impl.TMonthlySalaryServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppTest {


    public static void main(String []args)
    {
        /**
        InputStream inputStream = null;


        try {

            inputStream = Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        //获取负责员工id
        int departmentId = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_departmentId", 2020101000);

        System.out.println(departmentId);
        */
        TMonthlySalaryServiceImpl tMonthlySalaryService = new TMonthlySalaryServiceImpl();
        tMonthlySalaryService.getCurrentSalary(2020999999);
    }
}
