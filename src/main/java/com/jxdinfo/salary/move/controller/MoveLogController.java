package com.jxdinfo.salary.move.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jxdinfo.hussar.bsp.permit.model.SysUsers;
import com.jxdinfo.hussar.bsp.permit.service.ISysUsersService;
import com.jxdinfo.hussar.core.base.controller.BaseController;
import com.jxdinfo.hussar.core.page.HussarPager;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.util.ToolUtil;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.jxdinfo.hussar.core.log.LogObjectHolder;
import com.jxdinfo.hussar.common.annotion.BussinessLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.jxdinfo.hussar.core.log.type.BussinessLogType;
import com.jxdinfo.salary.move.model.MoveLog;
import com.jxdinfo.salary.move.service.IMoveLogService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 调动日志控制器
 *
 * @author zxn
 * @Date 2020-06-17 16:23:07
 */
@Controller
@RequestMapping("/move")
public class MoveLogController extends BaseController {

    private String PREFIX = "/salary/move/";

    @Autowired
    private IMoveLogService moveLogService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IUtilService utilService;

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
     * 跳转到调动日志首页
     */
    @RequestMapping("/view")
    @BussinessLog(key = "/move/view", type = BussinessLogType.QUERY, value = "调动日志页面")
    @RequiresPermissions("move:view")
    public String index() {
        return PREFIX + "moveLog.html";
    }

    /**
     * 获取调动日志列表
     */
    @RequestMapping(value = "/list")
    @BussinessLog(key = "/move/list", type = BussinessLogType.QUERY, value = "获取调动日志列表")
    @RequiresPermissions("move:list")
    @ResponseBody
    public Object list(@RequestParam (value="condition1", defaultValue = "")String condition1,
                       @RequestParam (value="condition2", defaultValue = "")String condition2,
                       @RequestParam (value="condition3", defaultValue = "")String condition3,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {

        Page<MoveLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<MoveLog> ew = new EntityWrapper<>();

        //权限部分
        boolean able=false;
        Staff currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        if (currentUser.getPosition().getPositionId()==0){ //当前用户为员工
            // 根据用户ID查询用户真实权限列表
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){
                //当前用户无任何权限
                System.out.println("您没有查看该日志的权限！");
                return 0;
            } else {
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("日志查看")){
                        //该员工用户可以查看从该部门调出的日志
                        able = true;
                        ew.eq("OLD_DEPARTMENT_ID",p.getDepartmentId());

                        Map<String, Object> operatorResult = new HashMap<>(5);
                        List<MoveLog> list = moveLogService.likeSelectD(page, condition1, condition2, condition3,ew);
                        operatorResult.put("total", page.getTotal());
                        operatorResult.put("rows", list);
                        return operatorResult;
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
            //如果既不是总经理也不是人力资源部经理，则可以访问从自己部门调出或调入的日志
            if(currentUser.getDepartment().getDepartmentId()!=10 &&
                    currentUser.getDepartment().getDepartmentId()!=99){
                    able = true;
                    ew.eq("OLD_DEPARTMENT_ID", currentUser.getDepartment().getDepartmentId());
                    List<MoveLog> listD = moveLogService.selectPage(page, ew).getRecords();
                    page.setRecords(listD);
                }
            }

        Map<String, Object> result = new HashMap<>(5);
        List<MoveLog> list = moveLogService.likeSelect(page, condition1, condition2, condition3);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取调动日志筛选列表
     */
    @RequestMapping(value = "/list/select")
    @BussinessLog(key = "/move/list/select", type = BussinessLogType.QUERY, value = "获取调动日志筛选列表")
    @RequiresPermissions("move:list")
    @ResponseBody
    public Object selectList(@RequestParam (value="condition1", defaultValue = "")String condition1,
                             @RequestParam (value="condition2", defaultValue = "")String condition2,
                             @RequestParam (value="condition3", defaultValue = "")String condition3,
                             @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                             @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<MoveLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<MoveLog> ew = new EntityWrapper<>();

        //权限部分
        boolean able=false;
        Staff currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        if (currentUser.getPosition().getPositionId()==0){ //当前用户为员工
            // 根据用户ID查询用户真实权限列表
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){
                //当前用户无任何权限
                System.out.println("您没有查看该日志的权限！");
                return 0;
            } else {
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("日志查看")){
                        //该员工用户可以查看从该部门调出的日志
                        able = true;
                        ew.eq("OLD_DEPARTMENT_ID",p.getDepartmentId());
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
            //如果既不是总经理也不是人力资源部经理，则可以访问从自己部门调出或调入的日志
            if(currentUser.getDepartment().getDepartmentId()!=10 &&
                    currentUser.getDepartment().getDepartmentId()!=99){
                able = true;
                //让他自己在全表里筛选好了w
            }
        }

        List<MoveLog> list = moveLogService.likeSelect(page,condition1,condition2,condition3);

        List<Department> oldDList = new ArrayList<>();
        List<Position> oldPList = new ArrayList<>();
        List<Department> newDList = new ArrayList<>();
        List<Position> newPList = new ArrayList<>();
        List<String> operationTimeList = new ArrayList<>();

        for(MoveLog m:list){
            oldDList.add(m.getOldDepartment());
            oldPList.add(m.getOldPosition());
            newDList.add(m.getNewDepartment());
            newPList.add(m.getNewPosition());
            operationTimeList.add(m.getOperationTime()==null?"":new SimpleDateFormat("yyyy-MM-dd").format(m.getOperationTime()));
        }

        Set<Department> oldDs = new HashSet<>(oldDList);
        Set<Position> oldPs = new HashSet<>(oldPList);
        Set<Department> newDs = new HashSet<>(newDList);
        Set<Position> newPs = new HashSet<>(newPList);
        Set<String> operationTimes = new HashSet<>(operationTimeList);

        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("oldDs", oldDs);
        result.put("oldPs", oldPs);
        result.put("newDs", newDs);
        result.put("newPs", newPs);
        result.put("operationTimes", operationTimes);
        return result;
    }


    /**
     * 获取筛选后的调动日志列表
     */
    @RequestMapping(value = "/list/condition")
    @BussinessLog(key = "/move/list/condition", type = BussinessLogType.QUERY, value = "获取调动日志列表，筛选后")
    @RequiresPermissions("move:list")
    @ResponseBody
    public Object conditionList(@RequestParam (value="condition1", defaultValue = "")String condition1,
                                @RequestParam (value="condition2", defaultValue = "")String condition2,
                                @RequestParam (value="condition3", defaultValue = "")String condition3,
                                @RequestParam (value="selectList")String conditions,
                                @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                                @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Map<String,String> info = JSONObject.parseObject(conditions,Map.class);
        Page<MoveLog> page = new Page<>(pageNumber, pageSize);
        String oldD = info.get("oldD");
        String oldP = info.get("oldP");
        String newD = info.get("newD");
        String newP = info.get("newP");
        String operationTime = info.get("operationTime");
        Wrapper<MoveLog> ew = new EntityWrapper<>();

        //权限部分
        boolean able=false;
        Staff currentUser = staffService.selectById(getCurrentAccountId()); //获取当前登录用户
        if (currentUser.getPosition().getPositionId()==0){ //当前用户为员工
            // 根据用户ID查询用户真实权限列表
            List<Util> permissionList = utilService.selectList((long)currentUser.getStaffId());
            if (permissionList.size()==0){
                //当前用户无任何权限
                System.out.println("您没有查看该日志的权限！");
                return 0;
            } else {
                for (Util p: permissionList){
                    if(p.getPermissionName().equals("日志查看")){
                        //该员工用户可以查看从该部门调出的日志
                        able = true;
                        ew.eq("OLD_DEPARTMENT_ID",p.getDepartmentId());
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
            //如果既不是总经理也不是人力资源部经理，则可以访问从自己部门调出或调入的日志
            if(currentUser.getDepartment().getDepartmentId()!=10 &&
                    currentUser.getDepartment().getDepartmentId()!=99){
                able = true;
                //让他自己在全表里筛选好了w
            }
        }

        if (!oldD.equals("")){
            int oldDId = Integer.parseInt(oldD);
            ew.eq("OLD_DEPARTMENT_ID",oldDId);
        }
        if (!oldP.equals("")){
            int oldPId = Integer.parseInt(oldP);
            ew.eq("OLD_POSITION_ID",oldPId);
        }
        if (!newD.equals("")){
            int newDId = Integer.parseInt(newD);
            ew.eq("NEW_DEPARTMENT_ID",newDId);
        }
        if (!newP.equals("")){
            int newPId = Integer.parseInt(newP);
            ew.eq("NEW_POSITION_ID",newPId);
        }
        if (!operationTime.equals("")){
            if (operationTime.equals("所有")){
                ew.isNotNull("OPERATION_TIME");
            }else{
                ew.like("OPERATION_TIME",operationTime+"%");
            }
        }
        //List<MoveLog> list
        //List<MoveLog> list1 = moveLogService.selectList(ew);
        List<MoveLog> list = moveLogService.likeSelectByCondition(page,ew,condition1,condition2,condition3);
        Map<String, Object> result = new HashMap<>(5);
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 获取特定部门调动日志列表
     */
    @RequestMapping(value = "/list/department")
    @BussinessLog(key = "/move/list/department", type = BussinessLogType.QUERY, value = "获取调动日志列表")
    @RequiresPermissions("move:list")
    @ResponseBody
    public Object listByODid(String condition,
                       @RequestParam(value="pageNumber", defaultValue="1")int pageNumber,
                       @RequestParam(value="pageSize", defaultValue="20") int pageSize) {
        Page<MoveLog> page = new Page<>(pageNumber, pageSize);
        Wrapper<MoveLog> ew = new EntityWrapper<>();
        Map<String, Object> result = new HashMap<>(5);
        List<MoveLog> list = moveLogService.selectByDidPage(page, ew).getRecords();
        result.put("total", page.getTotal());
        result.put("rows", list);
        return result;
    }

    /**
     * 调动日志详情
     */
    @RequestMapping(value = "/detail/{moveLogId}")
    @BussinessLog(key = "/move/detail", type = BussinessLogType.QUERY, value = "调动日志详情")
    @RequiresPermissions("move:detail")
    @ResponseBody
    public Object detail(@PathVariable("moveLogId") String moveLogId) {
        return moveLogService.selectById(moveLogId);
    }
}
