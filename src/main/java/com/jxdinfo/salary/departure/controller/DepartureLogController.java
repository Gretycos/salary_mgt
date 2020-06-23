package com.jxdinfo.salary.departure.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.salary.PermissionManagement.model.Util;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.entry.model.EntryLog;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.position.service.IPositionService;
import com.jxdinfo.salary.staff.model.Staff;
import com.jxdinfo.salary.staff.service.IStaffService;
import oracle.net.aso.g;
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
import com.jxdinfo.salary.departure.model.DepartureLog;
import com.jxdinfo.salary.departure.service.IDepartureLogService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 离职日志控制器
 *
 * @author zxn
 * @Date 2020-06-19 14:13:01
 */
@Controller
@RequestMapping("/departure")
public class DepartureLogController extends BaseController {

    private String PREFIX = "/salary/departure/";

    @Autowired
    private IDepartureLogService departureLogService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IUtilService utilService;

    private Staff currentUser;

    /**
     * 获取当前登陆人信息
     */
    private int getCurrentAccountId(){
        String account = ShiroKit.getUser().getAccount();
        try{
            return Integer.parseInt(account);//当前登录的人的Id 对应STAFF_ID
        }catch (Exception e){
            account = "2020999999";
            return Integer.parseInt(account);//超级管理员
        }
    }


    /**
     * 跳转到离职日志首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/departure/view", type = BussinessLogType.QUERY, value = "离职日志页面")
    @RequiresPermissions("departure:view")
    public String index() {
        currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        return PREFIX + "departureLog.html";
    }



    /**
     * 获取离职日志列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/departure/list", type = BussinessLogType.QUERY, value = "获取离职日志列表")
    @RequiresPermissions("departure:list")
    @ResponseBody
    public Object list(@RequestParam (value="condition1", defaultValue = "")String condition1,
                       @RequestParam (value="condition2", defaultValue = "")String condition2,
                       @RequestParam (value="condition3", defaultValue = "")String condition3,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<DepartureLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<DepartureLog> ew = new EntityWrapper<>();

        //权限部分
        if (currentUser.getPosition().getPositionId()==0){ //当前用户为员工
            // 根据用户ID查询用户真实权限列表
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){
                //当前用户无任何权限
                System.out.println("您没有查看该日志的权限！");
                return 0;
            } else {
                boolean able=false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("日志查看")){
                        //该员工用户可以查看从该部门离职员工的日志
                        able = true;
                        ew.or().eq("c.DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!able){//该用户没有日志查看权限
                    System.out.println("您没有查看该日志的权限！");
                    return 0;
                }
            }
        }
        else{//当前用户不为员工,可能是部门经理或总经理
            //如果是总经理，则可以查看全部日志
            //如果是人力资源部经理，则可以查看全部日志
            //如果既不是总经理也不是人力资源部经理，则可以访问从自己部门离职的日志
            if(currentUser.getDepartment().getDepartmentId()!=10 &&
                    currentUser.getDepartment().getDepartmentId()!=99){
                ew.or().eq("c.DEPARTMENT_ID", currentUser.getDepartment().getDepartmentId());
            }
        }

        Map<String, Object> result = new HashMap<>(5);
        List<DepartureLog> list = departureLogService.likeSelectByCondition(page,ew,condition1,condition2,condition3);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取离职日志筛选列表
     */
    @RequestMapping(value = "/list/select")
    @BussinessLog(key = "/departure/list/select", type = BussinessLogType.QUERY, value = "获取离职日志筛选列表")
    @RequiresPermissions("departure:list")
    @ResponseBody
    public Object selectList(@RequestParam (value="condition1", defaultValue = "")String condition1,
                             @RequestParam (value="condition2", defaultValue = "")String condition2,
                             @RequestParam (value="condition3", defaultValue = "")String condition3,
                             @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<DepartureLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<DepartureLog> ew = new EntityWrapper<>();

        //权限部分
        if (currentUser.getPosition().getPositionId()==0){ //当前用户为员工
            // 根据用户ID查询用户真实权限列表
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){
                //当前用户无任何权限
                System.out.println("您没有查看该日志的权限！");
                return 0;
            } else {
                boolean able=false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("日志查看")){
                        //该员工用户可以查看从该部门离职员工的日志
                        able = true;
                        ew.or().eq("c.DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!able){//该用户没有日志查看权限
                    System.out.println("您没有查看该日志的权限！");
                    return 0;
                }
            }
        }
        else{//当前用户不为员工,可能是部门经理或总经理
            //如果是总经理，则可以查看全部日志
            //如果是人力资源部经理，则可以查看全部日志
            //如果既不是总经理也不是人力资源部经理，则可以访问从自己部门离职的日志
            if(currentUser.getDepartment().getDepartmentId()!=10 &&
                    currentUser.getDepartment().getDepartmentId()!=99){
                ew.or().eq("c.DEPARTMENT_ID", currentUser.getDepartment().getDepartmentId());
            }
        }

        //模糊查询
        List<DepartureLog> list = departureLogService.likeSelectByCondition(page,ew,condition1,condition2,condition3);

        List<Department> departmentList = new ArrayList<>();
        List<Position> positionList = new ArrayList<>();
        List<String> operationTimeList = new ArrayList<>();

        for(DepartureLog d:list){
            departmentList.add(d.getDeparture().getDepartment());
            positionList.add(d.getDeparture().getPosition());
            operationTimeList.add(d.getOperationTime()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(d.getOperationTime()));
        }

        Set<Department> departments = new HashSet<>(departmentList);
        Set<Position> positions = new HashSet<>(positionList);
        Set<String> operationTimes = new HashSet<>(operationTimeList);

        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("departments", departments);
        result.put("positions", positions);
        result.put("operationTimes", operationTimes);
        return result;
    }


    /**
     * 获取筛选后的离职日志列表
     */
    @RequestMapping(value = "/list/condition")
    @BussinessLog(key = "/departure/list/condition", type = BussinessLogType.QUERY, value = "获取离职日志列表，筛选后")
    @RequiresPermissions("departure:list")
    @ResponseBody
    public Object conditionList(@RequestParam (value="condition1", defaultValue = "")String condition1,
                                @RequestParam (value="condition2", defaultValue = "")String condition2,
                                @RequestParam (value="condition3", defaultValue = "")String condition3,
                                @RequestParam (value="selectList")String conditions,
                                @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                                @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Map<String,String> info = JSONObject.parseObject(conditions,Map.class);
        Page<DepartureLog> page = new Page<>(pageNumber, pageSize);
        String department = info.get("department");
        String position = info.get("position");
        String operationTime = info.get("operationTime");
        Wrapper<DepartureLog> ew = new EntityWrapper<>();

        //权限部分
        if (currentUser.getPosition().getPositionId()==0){ //当前用户为员工
            // 根据用户ID查询用户真实权限列表
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){
                //当前用户无任何权限
                System.out.println("您没有查看该日志的权限！");
                return 0;
            }
            if (!department.equals("")){
                int departmentId = Integer.parseInt(department);
                ew.eq("c.DEPARTMENT_ID",departmentId);
            }
            else {
                boolean able=false;
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("日志查看")){
                        //该员工用户可以查看从该部门离职员工的日志
                        able = true;
                        ew.or().eq("c.DEPARTMENT_ID",p.getDepartmentId());
                    }
                }
                if (!able){//该用户没有日志查看权限
                    System.out.println("您没有查看该日志的权限！");
                    return 0;
                }
            }
        }
        else{//当前用户不为员工,可能是部门经理或总经理
            //如果是总经理，则可以查看全部日志
            //如果是人力资源部经理，则可以查看全部日志
            //如果既不是总经理也不是人力资源部经理，则可以访问从自己部门离职的日志
            if(currentUser.getDepartment().getDepartmentId()!=10 &&
                    currentUser.getDepartment().getDepartmentId()!=99){
                ew.or().eq("c.DEPARTMENT_ID", currentUser.getDepartment().getDepartmentId());
            }
        }


        if (!position.equals("")){
            int positionId = Integer.parseInt(position);
            ew.eq("c.POSITION_ID",positionId);
        }
        if (!operationTime.equals("")){
            if (operationTime.equals("所有")){
                ew.isNotNull("OPERATION_TIME");
            }else{
                ew.like("OPERATION_TIME",operationTime+"%");
            }
        }
        List<DepartureLog> list = departureLogService.likeSelectByCondition(page,ew,condition1,condition2,condition3);
        Map<String, Object> result = new HashMap<>(5);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取特定部门离职列表
     */
    @RequestMapping(value = "/list/department")
    @BussinessLog(key = "/departure/list/department", type = BussinessLogType.QUERY, value = "获取特定部门离职列表")
    @RequiresPermissions("departure:list")
    @ResponseBody
    public Object listByODid(String condition,
                             @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<DepartureLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<DepartureLog> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<DepartureLog> list = departureLogService.selectByDidPage(page,ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }


    /**
     * 离职日志详情
     */
    @RequestMapping(value = "/detail/{departureLogId}")
    @BussinessLog(key = "/departure/detail", type = BussinessLogType.QUERY, value = "离职日志详情")
    @RequiresPermissions("departure:detail")
    @ResponseBody
    public Object detail(@PathVariable("departureLogId") String departureLogId) {
        return departureLogService.selectById(departureLogId);
    }
}
