package com.jxdinfo.salary.PermissionManagement.model;

import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.permission.model.Permission;
import com.jxdinfo.salary.staff.model.Staff;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2020/6/24 10:38
 * @Author: wmk
 * @Description:
 */
public class MergeList implements Serializable {

    private static final long serialVersionUID = 1L;

    private Staff staff;

    private Department department;

    private List<Permission> permissionList;

    private String permissionName;

    @Override
    public String toString() {
        return "MergeList{" +
                "staff=" + staff +
                ", department=" + department +
                ", permissionList=" + permissionList +
                '}';
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
}
