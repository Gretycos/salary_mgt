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
 * 黑名单表
 * </p>
 *
 * @author 王明凯
 * @since 2020-06-16
 */
@TableName("t_black_list")
public class BlackList extends Model<BlackList> {

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

    @Override
    public String toString() {
        return "BlackList{" +
        "staffId=" + staffId +
        ", departmentId=" + departmentId +
        ", permissionId=" + permissionId +
        "}";
    }
}
