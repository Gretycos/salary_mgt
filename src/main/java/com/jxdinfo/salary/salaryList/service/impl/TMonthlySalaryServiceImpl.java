package com.jxdinfo.salary.salaryList.service.impl;

import com.jxdinfo.salary.salaryList.model.TBonus;
import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper;
import com.jxdinfo.salary.salaryList.service.ITMonthlySalaryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jxdinfo.salary.staff.model.Staff;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 员工每月工资表 服务实现类
 * </p>
 *
 * @author wx
 * @since 2020-06-18
 */
@Service
public class TMonthlySalaryServiceImpl extends ServiceImpl<TMonthlySalaryMapper, TMonthlySalary> implements ITMonthlySalaryService {

    /**
     * 新增
     * <p>
     * 查询本月员工工资
     *
     * @param id 负责人号
     * @return 所负责成员的当月工资明细T
     */
    public List<TMonthlySalary> getCurrentSalary(@RequestParam Integer id) {


        List<Integer> ids;
        List<TMonthlySalary> tMonthlySalaries = new ArrayList<>();


        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        //获取负责员工id
        int departmentId = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_departmentId", id);
        int positionId = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_positionId", id);

        if (departmentId == 99 || departmentId == 12) {
            //如果是超级管理员，返回所有员工id
            ids = session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.all_staff_list", departmentId);
        } else {
            //否则，只能返回本部门职别比自己低的员工id
            Map param0 = new HashMap<>();
            param0.put("departmentId", departmentId);
            param0.put("positionId", positionId);
            ids = session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_id_list", param0);
        }


        if (ids != null) {
            for (Integer staffId : ids) {

                //     System.out.println("处理员工： " + staffId);


                //获取该员工的工资明细
                TMonthlySalary MonthlySalary = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_monthly_salary", staffId);


                //           System.out.println("原始当月工资明细： " + MonthlySalary.toString());

                Calendar cal = Calendar.getInstance();

                Map param1 = new HashMap<>();
                param1.put("id", staffId);
                param1.put("payMonth", cal.get(Calendar.MONTH) + 1);
                TBonus tBonus = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.getBonus", param1);


                if (tBonus == null) {
                    tBonus = new TBonus();

                }

                //      System.out.println("员工奖金明细：" + Bonus.toString());


                try {
                    //计算该员工本月的奖金总额

                    if (MonthlySalary != null && tBonus.getTotal() != MonthlySalary.getAwardAmount()) {

                        //如果奖金数与当前工资表不同，则更新当前工资表
                        Map param = new HashMap<>();
                        param.put("awardAmount", tBonus.getTotal());
                        param.put("id", staffId);

                        System.out.println("更新参数：" + param);

                        session.update("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.updateBonus", param);
                        session.commit();

                        Integer t = MonthlySalary.getSalaryAmount() + MonthlySalary.getSalaryOfAge() - MonthlySalary.getIahf() + tBonus.getTotal();

                        Map param2 = new HashMap<>();
                        param2.put("total", t);
                        param2.put("id", staffId);
                        session.update("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.updateTotal", param2);
                        session.commit();


                        //       System.out.println("更新当月工资数据表完成");


                        MonthlySalary.setAwardAmount(tBonus.getTotal());

                        MonthlySalary.setTotal(t);

                        //     System.out.println("现在的MonthlySalary:" + MonthlySalary);

                        tMonthlySalaries.add(MonthlySalary);

                        //     System.out.println("形成的MonthlySalary：" + MonthlySalary);


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }
        System.out.println(tMonthlySalaries);

        return tMonthlySalaries;
    }

    /**
     * 新增
     * <p>
     * 根据负责人id获取员工id列表
     *
     * @param id 负责人id
     * @return 员工id列表
     */
    public List<Integer> staffIdList(@RequestParam Integer id) {
        List<Integer> ids;


        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        int departmentId = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_departmentId", id);


        if (departmentId == 99 || departmentId == 12) {
            //如果是超级管理员，返回所有员工id
            ids = session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.all_staff_list", departmentId);
        } else {
            //获取负责员工id
            int positionId = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_positionId", id);

            //否则，只能返回本部门职别比自己低的员工id
            Map param0 = new HashMap<>();
            param0.put("departmentId", departmentId);
            param0.put("positionId", positionId);
            ids = session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_id_list", param0);
        }

        return ids;


    }

    /**
     * @param
     * @return
     */
    public List<Integer> staffIdByDepartment(@RequestParam Integer id, @RequestParam Integer departmentId) {

        List<Integer> ids;


        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        int departmentId1 = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_departmentId", id);


        if (departmentId1 == 99 || departmentId1 == 12) {
            //如果是超级管理员，返回所有员工id
            ids = session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.all_staff_list_with_departmentId", departmentId);
        } else {
            //获取负责员工id
            int positionId = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_positionId", id);

            //否则，只能返回本部门职别比自己低的员工id
            Map param0 = new HashMap<>();
            param0.put("departmentId", departmentId);
            param0.put("positionId", positionId);
            ids = session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.staff_id_list", param0);
        }

        return ids;


    }

    /**
     * 根据基础工资表 刷新当月工资表
     */
    public void refreshBaseSalary()
    {
        List<Integer> ids = new ArrayList<>();

        InputStream inputStream = null;
        try {
            inputStream =Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        ids=session.selectList("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.getIdsInMonthly");

        for(Integer id:ids)
        {
            //更新所有员工基础工资
            Integer oldBaseSalary = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.getBaseSalaryInMonthly",id);
            Integer newBaseSalary = session.selectOne("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.getBaseSalaryInInitial",id);

            System.out.println("原始工资："+oldBaseSalary);
            System.out.println("后来的工资:"+newBaseSalary);


            if(newBaseSalary != null &&oldBaseSalary != newBaseSalary)
            {
                Map param0 = new HashMap<>();
                param0.put("id", id);
                param0.put("newSalary", newBaseSalary);
                session.update("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.updateBaseSalary",param0);
                session.commit();
            }

        }



    }

    private String DepName(Integer departmentID) {
        switch (departmentID) {
            case 10:
                return "人力资源部";

            case 11:
                return "研发部";

            case 12:
                return "财务部";

            case 13:
                return "销售部";

            case 99:
                return "高管层";
            default:
                return "无职";
        }

    }

    private String PosName(Integer positionID) {
        switch (positionID) {

            case 0:return "员工";
            case 1: return "部门经理";
            case 2: return "总经理";
            default:return "无职";
        }

    }

    /**
     * 将本月工资提交给财务
     */
    public void commitSalary()
    {



    }

    public boolean insertNewStaff(Staff staff)
    {

        Map param0 = new HashMap<>();
        param0.put("staffId", staff.getStaffId());
        param0.put("staffName", staff.getStaffName());
        param0.put("departmentId",staff.getDepartment().getDepartmentId());
        param0.put("positionId",staff.getPosition().getPositionId());
        param0.put("salaryAmount",0);
        param0.put("salaryOfAge",0);
        param0.put("iahf",0);
        param0.put("awardAmount",0);
        param0.put("total",0);

        InputStream inputStream = null;
        try {
            inputStream =Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        session.insert("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.insertNewStaff",param0);
        session.commit();


        return true;


    }

    /**
     * 将离职员工的当月工资删除
     * @param staffId
     * @return
     */
    public boolean deleteStaff(Integer staffId)
    {
        InputStream inputStream = null;
        try {
            inputStream =Resources.getResourceAsStream("config/SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        session.delete("com.jxdinfo.salary.salaryList.dao.TMonthlySalaryMapper.deleteStaff",staffId);
        session.commit();

        return true;

    }
}

