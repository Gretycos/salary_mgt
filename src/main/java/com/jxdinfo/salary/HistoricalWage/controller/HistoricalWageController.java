package com.jxdinfo.salary.HistoricalWage.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
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
import com.jxdinfo.salary.HistoricalWage.model.HistoricalWage;
import com.jxdinfo.salary.HistoricalWage.service.IHistoricalWageService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author 
 * @Date 2020-06-21 22:11:30
 */
@Controller
@RequestMapping("/historicalWage")
public class HistoricalWageController extends BaseController {

    private String PREFIX = "/salary/historicalWage/";

    @Autowired
    private IHistoricalWageService historicalWageService;
    @Autowired
    private IDepartmentService departmentService;
    /**
     * 跳转到首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/historicalWage/view", type = BussinessLogType.QUERY, value = "页面")
    @RequiresPermissions("historicalWage:view")
    public String index() {
        return PREFIX + "historicalWage.html";
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
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/historicalWage/list", type = BussinessLogType.QUERY, value = "获取列表")
    @RequiresPermissions("historicalWage:list")
    @ResponseBody
    public Object list(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        System.out.println("<--------------我-------------------->");
        Page<HistoricalWage> page = new Page<>(pageNumber, pageSize);
        String departmentName=super.getPara("departmentName");
        String name=super.getPara("name");
        System.out.println("<---------------------------------"+departmentName+"---------------------------->");
        Wrapper<Department> w1 = new EntityWrapper<>();
        w1.where("DEPARTMENT_NAME = {0}", departmentName);
        Department department = departmentService.selectOne(w1);
        Integer departmentId = department.getDepartmentId();
        Wrapper<HistoricalWage> w2 = new EntityWrapper<>();
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
        List<HistoricalWage> list = historicalWageService.selectPage(page,w2).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }




    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{historicalWageId}")
    @BussinessLog(key = "/historicalWage/detail", type = BussinessLogType.QUERY, value = "详情")
    @RequiresPermissions("historicalWage:detail")
    @ResponseBody
    public Object detail(@PathVariable("historicalWageId") String historicalWageId) {
        return historicalWageService.selectById(historicalWageId);
    }
}
