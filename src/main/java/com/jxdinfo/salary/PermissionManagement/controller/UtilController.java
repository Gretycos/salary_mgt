package com.jxdinfo.salary.PermissionManagement.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jxdinfo.hussar.core.shiro.ShiroKit;
import com.jxdinfo.hussar.core.shiro.ShiroUser;
import com.jxdinfo.salary.PermissionManagement.model.BlackList;
import com.jxdinfo.salary.PermissionManagement.model.WhiteList;
import com.jxdinfo.salary.PermissionManagement.service.IBlackListService;
import com.jxdinfo.salary.PermissionManagement.service.IUtilService;
import com.jxdinfo.salary.PermissionManagement.service.IWhiteListService;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.department.service.IDepartmentService;
import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.permission.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.midi.Soundbank;
import java.util.List;

/**
 * @Date: 2020/6/20 10:46
 * @Author: wmk
 * @Description:
 */
@Controller
@ResponseBody
@RequestMapping("/util")
public class UtilController {

    @Autowired
    private IUtilService utilService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IBlackListService blackListService;
    @Autowired
    private IWhiteListService whiteListService;

    @RequestMapping("/getPermission")
    public Object getPermission(String permissionName,String departmentName){
        System.out.println("===========================================");
        System.out.println("=================权限查询开始================");
        System.out.println("===permissionName:"+permissionName+"===departmentName:"+departmentName+"===");
        ShiroUser user = ShiroKit.getUser();
        try {
            Integer staffId = Integer.parseInt(user.getAccount());
            // 得到staffId之后去判断是否有权限
            //01 查询黑名单是否存在 先根据部门和权限的名称得到部门和权限的ID
            Wrapper<Department> w1 = new EntityWrapper<>();
            w1.where("DEPARTMENT_NAME = {0}", departmentName);
            Wrapper<Permission> w2 = new EntityWrapper<>();
            w2.where("PERMISSION_NAME = {0}", permissionName);
            Department department = departmentService.selectOne(w1);
            Permission permission = permissionService.selectOne(w2);
            // 判断输入的部门和权限名称是否正确
            if (department==null){
                System.out.println("=================权限查询结束================");
                return "输入的部门不存在";
            }
            if (permission==null){
                System.out.println("=================权限查询结束================");
                return "输入的权限不存在";
            }
            Integer departmentId = department.getDepartmentId();
            Integer permissionId = permission.getPermissionId();
            Wrapper<BlackList> wrapper1 = new EntityWrapper<>();
            wrapper1.where("STAFF_ID = {0}",staffId);
            wrapper1.where("DEPARTMENT_ID = {0}",departmentId);
            wrapper1.where("PERMISSION_ID = {0}",permissionId);
            List<BlackList> blackLists = blackListService.selectList(wrapper1);
            if (blackLists.isEmpty()||blackLists==null){
                // 如果黑名单没有的话 去查看白名单是否有
                Wrapper<WhiteList> wrapper2 = new EntityWrapper<>();
                wrapper2.where("STAFF_ID = {0}",staffId);
                wrapper2.where("DEPARTMENT_ID = {0}",departmentId);
                wrapper2.where("PERMISSION_ID = {0}",permissionId);
                List<WhiteList> whiteLists = whiteListService.selectList(wrapper2);
                System.out.println("=================权限查询结束================");
                if (whiteLists.isEmpty()||whiteLists==null)
                    return false;
                else
                    return true;
            }else {
                System.out.println("=================权限查询结束================");
                return false;
            }

        }catch (NumberFormatException e){
            System.out.println(e);
            System.out.println("=================权限查询结束================");
            return "获取员工工号失败:类型转化错误。"+"当前登录用户:"+user.getAccount()+",无法获取工号";
        }catch (Exception e){
            System.out.println(e);
            System.out.println("=================权限查询结束================");
            return "未知错误";
        }
    }

}
