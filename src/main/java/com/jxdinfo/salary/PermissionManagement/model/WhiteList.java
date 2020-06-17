package com.jxdinfo.salary.PermissionManagement.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 白名单表
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
@TableName("t_white_list")
public class WhiteList extends Model<WhiteList> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableId("STAFF_ID")
    private Integer staffId;
    /**
     * 部门ID
     */
    @TableField("DEPARTMENT_ID")
    private Integer departmentId;
    /**
     * 权限ID
     */
    @TableField("PERMISSION_ID")
    private Integer permissionId;

    // 员工姓名
    private String staffName;

    // 管理部门的名称
    private String departmentName;

    // 拥有的权限的名称
    private String permissionName;

    @Override
    public String toString() {
        return "WhiteList{" +
                "staffId=" + staffId +
                ", departmentId=" + departmentId +
                ", permissionId=" + permissionId +
                ", staffName='" + staffName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", permissionName='" + permissionName + '\'' +
                '}';
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

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }


}
