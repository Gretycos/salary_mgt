package com.jxdinfo.salary.bonus.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import com.jxdinfo.salary.SDepartment.model.SDepartment;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.salaryList.model.TMonthlySalary;
import com.jxdinfo.salary.salaryList.service.impl.TMonthlySalaryServiceImpl;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.service.IStaffService;
import oracle.sql.TIMESTAMP;
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
import com.jxdinfo.salary.bonus.model.Bonus;
import com.jxdinfo.salary.bonus.service.IBonusService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 奖金展示控制器
 *
 * @author zyx
 * @Date 2020-06-24 16:30:13
 */
@Controller
@RequestMapping("/bonus")
public class BonusController extends BaseController {

    private String PREFIX = "/salary/bonus/";
    @Autowired
    private IStaffService staffService;
    @Autowired
    private IUtilService utilService;
    @Autowired
    private IBonusService bonusService;
    //当前用户
    private Staff currentUser;
    //当前用户权限
    private List<Util> permissionList;
    /**
     * 跳转到奖金展示首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/bonus/view", type = BussinessLogType.QUERY, value = "奖金展示页面")
    @RequiresPermissions("bonus:view")
    public String index() {
        currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        permissionList = utilService.selectList((long)currentUser.getStaffId()); //获取当前登录用户的权限
        return PREFIX + "bonus.html";

    }

    /**
     * 跳转到添加奖金展示
     */
    @RequestMapping("/bonus_add")
    @BussinessLog(key = "/bonus/bonus_add", type = BussinessLogType.INSERT, value = "跳转到添加奖金展示")
    @RequiresPermissions("bonus:bonus_add")
    public String bonusAdd() {
        return PREFIX + "bonus_add.html";
    }

    /**
     * 跳转到修改奖金展示
     */
    @RequestMapping("/bonus_update/{bonusId}")
    @BussinessLog(key = "/bonus/bonus_update", type = BussinessLogType.MODIFY, value = "跳转到修改奖金展示")
    @RequiresPermissions("bonus:bonus_update")
    public String bonusUpdate(@PathVariable String bonusId, Model model) {
        Bonus bonus = bonusService.selectById(bonusId);
        model.addAttribute("item",bonus);
        return PREFIX + "bonus_edit.html";
    }

    /**
     * 获取奖金展示列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/bonus/list", type = BussinessLogType.QUERY, value = "获取奖金展示列表")
    @RequiresPermissions("bonus:list")
    @ResponseBody
    public Object list(@RequestParam (value="condition", defaultValue = "")String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        System.out.println(condition);
        Page<Bonus> page = new Page<>(pageNumber, pageSize);
        Wrapper<Bonus> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        if (currentUser.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的查询权限，然后筛选出这些部门的员工，如果没有任何部门的权限，传入部门编号为-1
            if (permissionList.size()==0){
                ew.eq("DEPARTMENT_ID",-1);
            } else {
                boolean canQuery = false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("调整工资")){
                        canQuery = true;
                        ew.or().eq("DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!canQuery){
                    ew.eq("DEPARTMENT_ID",-1);
                }
            }
        }
        if(!condition.equals("")){
            ew.andNew().like("STAFF_ID",condition).or().like("STAFF_NAME",condition);
        }
        List<Bonus> list = bonusService.selectPage(page, ew).getRecords();
        System.out.println("查出来的部门id"+list.get(0).getDepartment().getDepartmentName());
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 查询
     */
    @RequestMapping(value = "/list/select")
    @BussinessLog(key = "/bonus/list/select", type = BussinessLogType.QUERY, value = "查询列表")
    @RequiresPermissions("bonus:list")
    @ResponseBody
    public Object selectList(@RequestParam (value="condition", defaultValue = "")String condition,
                             @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<Bonus> page = new Page<>(pageNumber, pageSize);
        Wrapper<Bonus> ew = new EntityWrapper<>();
//        System.out.println(condition);
//        System.out.println();

        if (currentUser.getPosition().getPositionId()==0){ //员工---负责部门
            //先查询拥有哪些部门的查询权限，然后筛选出这些部门的员工，如果没有任何部门的权限，传入部门编号为-1
            if (permissionList.size()==0){
                ew.eq("DEPARTMENT_ID",-1);
            } else {
                boolean canQuery = false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("查看信息")){
                        canQuery = true;
                        ew.or().eq("DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!canQuery){
                    ew.eq("DEPARTMENT_ID",-1);
                }
            }
        }
        if(!condition.equals("")){
            ew.andNew().like("STAFF_ID",condition).or().like("STAFF_NAME",condition);
        }

        List<Bonus> bonusList = bonusService.selectPage(page, ew).getRecords();



        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());

        return result;
    }
    /**
     * 新增奖金展示
     */
    @RequestMapping(value = "/add")
    @BussinessLog(key = "/bonus/add", type = BussinessLogType.INSERT, value = "新增奖金展示")
    @RequiresPermissions("bonus:add")
    @ResponseBody
    public Object add(Bonus bonus) {
        bonusService.insert(bonus);
        return SUCCESS_TIP;
    }

    /**
     * 删除奖金展示
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(key = "/bonus/delete", type = BussinessLogType.DELETE, value = "删除奖金展示")
    @RequiresPermissions("bonus:delete")
    @ResponseBody
    public Object delete(@RequestParam String bonusId) {
        bonusService.deleteById(bonusId);
        return SUCCESS_TIP;
    }

    /**
     * 修改奖金展示
     */

    @RequestMapping(value = "/update")
    @BussinessLog(key = "/bonus/update", type = BussinessLogType.MODIFY, value = "修改奖金展示")
    @RequiresPermissions("bonus:update")
    @ResponseBody
    public Object update(@RequestParam Map<String, String> bonus) {
        int staffId = Integer.parseInt(bonus.get("staffId")); //员工ID
        String staffName = bonus.get("staffName"); // 员工名称
        Calendar cal = Calendar.getInstance();
        int monthlyPayroll= cal.get(Calendar.MONTH) + 1;
        Timestamp payTime = Timestamp.valueOf(bonus.get("payTime"));
        int performanceAppraisalBonus=Integer.parseInt(bonus.get("performanceAppraisalBonus"));
        int noAbsentBonus=Integer.parseInt(bonus.get("noAbsentBonus"));
        int specialContributionsBonus=Integer.parseInt(bonus.get("specialContributionsBonus"));
        int decemberBonus=Integer.parseInt(bonus.get("decemberBonus"));
        Bonus bonus1= bonusService.selectOne(new EntityWrapper<Bonus>()
                .eq("STAFF_ID",staffId));
        bonus1.setDecemberBonus(decemberBonus);
        bonus1.setMonthlyPayroll(monthlyPayroll);
        bonus1.setNoAbsentBonus(noAbsentBonus);
        bonus1.setPayTime(payTime);
        bonus1.setStaffId(staffId);
        bonus1.setStaffName(staffName);
        bonus1.setPerformanceAppraisalBonus(performanceAppraisalBonus);
        bonus1.setSpecialContributionsBonus(specialContributionsBonus);
        bonusService.updateById(bonus1);
        Map<String,Object> res= new HashMap<>();
        res.put("code",200);
        res.put("message","成功");
        return res;
    }



    /**
     * 奖金展示详情
     */
    @RequestMapping(value = "/detail/{bonusId}")
    @BussinessLog(key = "/bonus/detail", type = BussinessLogType.QUERY, value = "奖金展示详情")
    @RequiresPermissions("bonus:detail")
    @ResponseBody
    public Object detail(@PathVariable("bonusId") String bonusId) {

        return bonusService.selectById(bonusId);
    }
    private int getCurrentAccountId(){
        String account = ShiroKit.getUser().getAccount();
        try{
            return Integer.parseInt(account);//当前登录的人的Id 对应STAFF_ID
        }catch (Exception e){
            account = "2020999999";
            return Integer.parseInt(account);//超级用户
        }
    }
}