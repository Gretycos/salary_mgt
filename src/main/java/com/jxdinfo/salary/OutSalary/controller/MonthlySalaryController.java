package com.jxdinfo.salary.OutSalary.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.MybatisSqlSessionTemplate;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.salary.PermissionManagement.controller.UtilController;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.PermissionManagement.service.IWhiteListService;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.salaryList.service.impl.TMonthlySalaryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.service.impl.UtilServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.jxdinfo.hussar.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.OutSalary.model.MonthlySalary;
import com.jxdinfo.salary.OutSalary.service.IMonthlySalaryService;
import com.jxdinfo.salary.department.service.IDepartmentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;

/**
 * 控制器
 *
 * @author 
 * @Date 2020-06-17 21:16:12
 */
@Controller
@RequestMapping("/monthlySalary")
public class MonthlySalaryController extends BaseController {
    //Private String
    private String PREFIX = "/salary/monthlySalary/";

    @Autowired
    private IMonthlySalaryService monthlySalaryService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IUtilService utilService;
    @Autowired
    private IWhiteListService whiteListService;

    /**
     * 导出工资首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/monthlySalary/view", type = BussinessLogType.QUERY, value = "页面")
    @RequiresPermissions("monthlySalary:view")
    public String index() {
        return PREFIX + "monthlySalary.html";
    }


    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/monthlySalary/list", type = BussinessLogType.QUERY, value = "获取列表")
    @RequiresPermissions("monthlySalary:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<MonthlySalary> page = new Page<>(pageNumber, pageSize);
        Wrapper<MonthlySalary> ew = new EntityWrapper<>();
        String departmentname=super.getPara("departmentname");
        System.out.println("<---------------------------------"+departmentname+"---------------------------->");
        Wrapper<Department> w1 = new EntityWrapper<>();
        w1.where("DEPARTMENT_NAME = {0}", departmentname);
        Department department = departmentService.selectOne(w1);
        Integer departmentId = department.getDepartmentId();
        Wrapper<MonthlySalary> w2 = new EntityWrapper<>();
        w2.where("DEPARTMENT_ID={0}",departmentId);
        Map<String, Object> result = new HashMap<>(5);
        List<MonthlySalary> list = monthlySalaryService.selectPage(page,w2).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
          // String departmentname="财务部";
       /*   UtilController user=new UtilController();
             boolean flag=false;
        Object per=user.getPermission("查看工资",departmentname);
        if(per instanceof Boolean)
       {
           flag=((Boolean)per).booleanValue();
           System.out.println("<-----------------------------------flag");
         }
        else if(per instanceof String){
           System.out.println("<----------------"+(String)per);
       }
       if(flag){
            Wrapper<Department> w1 = new EntityWrapper<>();
            w1.where("DEPARTMENT_NAME = {0}", departmentname);
            Department department = departmentService.selectOne(w1);
            Integer departmentId = department.getDepartmentId();
            Wrapper<MonthlySalary> w2 = new EntityWrapper<>();
            w2.where("DEPARTMENT_ID={0}",departmentId);
            Map<String, Object> result = new HashMap<>(5);
            List<MonthlySalary> list = monthlySalaryService.selectPage(page,w2).getRecords();
            result.put("total", page.getTotal());
            result.put("rows", list);
            //result.put("toatal","无此权限");
            return result;
        }else{
            Map<String, Object> result = new HashMap<>(5);
            result.put("toatal","无此权限");
            return result;
        }
       /* String name  = super.getPara("name");
        ew.like("STAFF_NAME",name);*/
      //  Page<MonthlySalary> page = new Page<>(pageNumber, pageSize);
       // String departmentname=request.getParameter("departmentname");
      //  System.out.println("<--------------"+departmentname+"------------------------->");

    }

    public boolean isNumeric(String str)
    {
        for (int i = 0; i < str.length(); i++)
    {
        System.out.println(str.charAt(i));
       if (!Character.isDigit(str.charAt(i))) {
    return false;
}
}
return true;
    }
    /**
     * 获取列表
     */
    @RequestMapping(value = "/list2")
    @BussinessLog(key = "/monthlySalary/list2", type = BussinessLogType.QUERY, value = "获取列表")
    @RequiresPermissions("monthlySalary:list2")
    @ResponseBody
    public Object list2(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        System.out.println("<--------------我-------------------->");
        Page<MonthlySalary> page = new Page<>(pageNumber, pageSize);
        Wrapper<MonthlySalary> ew = new EntityWrapper<>();
        String departmentname=super.getPara("departmentname");
        String name=super.getPara("name");
        System.out.println("<---------------------------------"+departmentname+"---------------------------->");
        Wrapper<Department> w1 = new EntityWrapper<>();
        w1.where("DEPARTMENT_NAME = {0}", departmentname);
        Department department = departmentService.selectOne(w1);
        Integer departmentId = department.getDepartmentId();
        Wrapper<MonthlySalary> w2 = new EntityWrapper<>();
        w2.where("DEPARTMENT_ID={0}",departmentId);
        System.out.println("<--------------你-------------------->");
        if(!name.equals("")) {
            if (isNumeric(name)) {
                Integer inte = Integer.valueOf(name);
                w2.where("STAFF_ID={0}", inte);
                System.out.println("<--------------数字");
            } else {
                w2.like("STAFF_NAME", name);
                System.out.println("<-----------------名字");
            }
        }else{
            w2.like("STAFF_NAME", name);
            System.out.println("<-----------------名字");
        }
        Map<String, Object> result = new HashMap<>(5);
        List<MonthlySalary> list = monthlySalaryService.selectPage(page,w2).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
        // String departmentname="财务部";
       /*   UtilController user=new UtilController();
             boolean flag=false;
        Object per=user.getPermission("查看工资",departmentname);
        if(per instanceof Boolean)
       {
           flag=((Boolean)per).booleanValue();
           System.out.println("<-----------------------------------flag");
         }
        else if(per instanceof String){
           System.out.println("<----------------"+(String)per);
       }
       if(flag){
            Wrapper<Department> w1 = new EntityWrapper<>();
            w1.where("DEPARTMENT_NAME = {0}", departmentname);
            Department department = departmentService.selectOne(w1);
            Integer departmentId = department.getDepartmentId();
            Wrapper<MonthlySalary> w2 = new EntityWrapper<>();
            w2.where("DEPARTMENT_ID={0}",departmentId);
            Map<String, Object> result = new HashMap<>(5);
            List<MonthlySalary> list = monthlySalaryService.selectPage(page,w2).getRecords();
            result.put("total", page.getTotal());
            result.put("rows", list);
            //result.put("toatal","无此权限");
            return result;
        }else{
            Map<String, Object> result = new HashMap<>(5);
            result.put("toatal","无此权限");
            return result;
        }
       /* String name  = super.getPara("name");
        ew.like("STAFF_NAME",name);*/
        //  Page<MonthlySalary> page = new Page<>(pageNumber, pageSize);
        // String departmentname=request.getParameter("departmentname");
        //  System.out.println("<--------------"+departmentname+"------------------------->");


    }
    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/monthlySalary/add", type = BussinessLogType.INSERT, value = "新增")
    @RequiresPermissions("monthlySalary:add")
    @ResponseBody
    public Object add(MonthlySalary monthlySalary) {
        monthlySalaryService.insert(monthlySalary);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/monthlySalary/delete", type = BussinessLogType.DELETE, value = "删除")
    @RequiresPermissions("monthlySalary:delete")
    @ResponseBody
    public Object delete(@RequestParam String monthlySalaryId) {
        monthlySalaryService.deleteById(monthlySalaryId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @BussinessLog(key = "/monthlySalary/update", type = BussinessLogType.MODIFY, value = "修改")
    @RequiresPermissions("monthlySalary:update")
    @ResponseBody
    public Object update(MonthlySalary monthlySalary) {
        MonthlySalary monthlySalary1 = monthlySalaryService.selectById(monthlySalary);
        LogObjectHolder.me().set(monthlySalary1);
        monthlySalaryService.updateById(monthlySalary);
        return SUCCESS_TIP;
    }

    /**
     * 导出Exce
     */

        // 导出excel
        @RequestMapping(value="/exportExcel")
        @BussinessLog(key = "/monthlySalary/exportExcel", type = BussinessLogType.MODIFY, value = "导出")
        @RequiresPermissions("monthlySalary:exportExcel")
        @ResponseBody
        /*public ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response) {
            return monthlySalaryService.exportExcel(request,response);
        }
  */
        public ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response,String condition,
                                                  @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                                                  @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
            System.out.println("nihao");
            Page<MonthlySalary> page = new Page<>(pageNumber, pageSize);
            String departmentname=request.getParameter("departmentname");
            System.out.println("<--------------"+departmentname+"------------------------->");
            Wrapper<Department> w1 = new EntityWrapper<>();
            w1.where("DEPARTMENT_NAME = {0}", departmentname);
            Department department = departmentService.selectOne(w1);
            Integer departmentId = department.getDepartmentId();
            Wrapper<MonthlySalary> w2 = new EntityWrapper<>();
            w2.where("DEPARTMENT_ID={0}",departmentId);
            Map<String, Object> result = new HashMap<>(5);
            List<MonthlySalary> list = monthlySalaryService.selectPage(page,w2).getRecords();
            return monthlySalaryService.exportExcel(request,response,list);
        }

    //刷新基础工资
    @RequestMapping(value = "/BaseSalary")
    @BussinessLog(key = "/monthlySalary/BaseSalary", type = BussinessLogType.QUERY, value = "刷新初始工资")
    @RequiresPermissions("monthlySalary:BaseSalary")
    @ResponseBody
    public Object refeshBaseSalary() {

        TMonthlySalaryServiceImpl MonthlySalaryService1 = new TMonthlySalaryServiceImpl();

        MonthlySalaryService1.refreshBaseSalary();

        return SUCCESS_TIP;
    }
    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{monthlySalaryId}")
    @BussinessLog(key = "/monthlySalary/detail", type = BussinessLogType.QUERY, value = "详情")
    @RequiresPermissions("monthlySalary:detail")
    @ResponseBody
    public Object detail(@PathVariable("monthlySalaryId") String monthlySalaryId) {
        return monthlySalaryService.selectById(monthlySalaryId);
    }
}
