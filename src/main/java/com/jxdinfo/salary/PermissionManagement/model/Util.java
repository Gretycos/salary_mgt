package com.jxdinfo.salary.PermissionManagement.model;

import java.io.Serializable;

/**
 * @Date: 2020/6/20 10:47
 * @Author: wmk
 * @Description:
 */
public class Util implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 员工ID
     */
    private Integer staffId;
    /**
     * 部门ID
     */
    private Integer departmentId;
    /**
     * 权限ID
     */
    private Integer permissionId;

    // 员工姓名
    private String staffName;

    // 管理部门的名称
    private String departmentName;

    // 拥有的权限的名称
    private String permissionName;

    @Override
    public String toString() {
        return "Util{" +
                "staffId=" + staffId +
                ", departmentId=" + departmentId +
                ", permissionId=" + permissionId +
                ", staffName='" + staffName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
