package com.jxdinfo.salary.salaryList.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.salary.SDepartment.model.SDepartment;
import com.jxdinfo.salary.SDepartment.service.impl.SDepartmentServiceImpl;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.department.service.impl.DepartmentServiceImpl;
import com.jxdinfo.salary.salaryList.service.impl.TMonthlySalaryServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.jxdinfo.salary.salaryList.service.ITMonthlySalaryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author wx
 * @Date 2020-06-18 09:05:43
 */
@Controller
@RequestMapping("/tMonthlySalary")
public class TMonthlySalaryController extends BaseController {

    private String PREFIX = "/salaryList/tMonthlySalary/";

    @Autowired
    private ITMonthlySalaryService tMonthlySalaryService;
    @Autowired
    private SDepartmentServiceImpl departmentService ;

    /**
     * 跳转到导出工资首页
     */

    @RequestMapping("/view")
    @BussinessLog(key = "/tMonthlySalary/view", type = BussinessLogType.QUERY, value = "页面")
    @RequiresPermissions("tMonthlySalary:view")
    public String index() {


        return PREFIX + "tMonthlySalary.html";
    }


     /**
     * 获取列表
     */
    //修改2.0
     @RequestMapping(value = "/list")
     @BussinessLog(key = "/tMonthlySalary/list", type = BussinessLogType.QUERY, value = "获取列表")
     @RequiresPermissions("tMonthlySalary:list")
     @ResponseBody
     public Object list(String condition,
                        @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                        @RequestParam(value="pageSize", defaultValue="20") int pageSize) {


         ShiroUser shiroUser = ShiroKit.getUser();
         Integer id;

         if(isDigit(shiroUser.getAccount()))
         {
             id = Integer.valueOf(shiroUser.getAccount());
         }else
         {
             id = 2020999999;
         }

         System.out.println();
         System.out.println();
         System.out.println("account:"+shiroUser.getAccount());
         System.out.println("id:"+id);
         System.out.println();
         System.out.println();




         TMonthlySalaryServiceImpl MonthlySalaryService0 = new TMonthlySalaryServiceImpl();
         List<Integer> ids = MonthlySalaryService0.staffIdList(id);//设置权限
         //System.out.println(ids);
         //MonthlySalaryService0.getCurrentSalary(id);

         Page<TMonthlySalary> page = new Page<>(pageNumber, pageSize);
         Wrapper<TMonthlySalary> ew = new EntityWrapper<>();
         ew.in("STAFF_ID",ids);

         Map<String, Object> result = new HashMap<>(5);
         List<TMonthlySalary> list = tMonthlySalaryService.selectPage(page, ew).getRecords();
         result.put("total", page.getTotal());
         result.put("rows", list);
         return result;

     }




    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{tMonthlySalaryId}")
    @BussinessLog(key = "/tMonthlySalary/detail", type = BussinessLogType.QUERY, value = "详情")
    @RequiresPermissions("tMonthlySalary:detail")
    @ResponseBody
    public Object detail(@PathVariable("tMonthlySalaryId") String tMonthlySalaryId) {
        return tMonthlySalaryService.selectById(tMonthlySalaryId);
    }

    //新增
    /**
     * 重新生成
     */
    @RequestMapping(value = "/refreshSalary")
    @BussinessLog(key = "/tMonthlySalary/refreshSalary", type = BussinessLogType.QUERY, value = "重新生成")
    @RequiresPermissions("tMonthlySalary:refreshSalary")
    @ResponseBody
    public Object refreshSalary() {

        TMonthlySalaryServiceImpl MonthlySalaryService1 = new TMonthlySalaryServiceImpl();


        ShiroUser shiroUser = ShiroKit.getUser();
        Integer id;

        if(isDigit(shiroUser.getAccount()))
        {
            id = Integer.valueOf(shiroUser.getAccount());
        }else
        {
            id = 2020999999;
        }



        MonthlySalaryService1.getCurrentSalary(id);

        return SUCCESS_TIP;
    }



    //新增2.0
    /**
     * 查询列表
     */
    @RequestMapping(value = "/search")
    @BussinessLog(key = "/tMonthlySalary/search", type = BussinessLogType.QUERY, value = "获取列表")
    @RequiresPermissions("tMonthlySalary:search")
    @ResponseBody
    public Object search(String departmentName,
                        @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                        @RequestParam(value="pageSize", defaultValue="20") int pageSize) {


        //String departmentname = super.getPara("departmentname");
        System.out.println("<--------------------------------!!!!!!!!"+departmentName);
        System.out.println("<<<<<你好>>>>>>");

        TMonthlySalaryServiceImpl monthlySalaryService = new TMonthlySalaryServiceImpl();
        Page<TMonthlySalary> page = new Page<>(pageNumber, pageSize);

        Wrapper<SDepartment> w1 = new EntityWrapper<>();
        w1.where("DEPARTMENT_NAME = {0}", departmentName);
        SDepartment department = departmentService.selectOne(w1);
        Integer departmentId = department.getDepartmentId();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<"+departmentId);
        Wrapper<TMonthlySalary> w2 = new EntityWrapper<>();
        w2.where("DEPARTMENT_ID={0}",departmentId);
        ShiroUser shiroUser = ShiroKit.getUser();
        Integer id;

        if(isDigit(shiroUser.getAccount()))
        {
            id = Integer.valueOf(shiroUser.getAccount());
        }else
        {
            id = 2020999999;
        }



        List<Integer> ids = monthlySalaryService.staffIdByDepartment(id,departmentId);


        Wrapper<TMonthlySalary> ew = new EntityWrapper<>();
        ew.in("STAFF_ID",ids);


        Map<String, Object> result = new HashMap<>(5);
        List<TMonthlySalary> list = tMonthlySalaryService.selectPage(page, ew).getRecords();
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).getStaffName());
        }
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;




    }
//刷新基础工资
    @RequestMapping(value = "/refreshBaseSalary")
    @BussinessLog(key = "/tMonthlySalary/refreshBaseSalary", type = BussinessLogType.QUERY, value = "刷新初始工资")
    @RequiresPermissions("tMonthlySalary:refreshBaseSalary")
    @ResponseBody
    public Object refeshBaseSalary() {

        TMonthlySalaryServiceImpl MonthlySalaryService1 = new TMonthlySalaryServiceImpl();

        MonthlySalaryService1.refreshBaseSalary();

        return SUCCESS_TIP;
    }



    //新增2.0
    public boolean isDigit(String strNum) {

        return strNum.matches("[0-9]{1,}");

    }


}
