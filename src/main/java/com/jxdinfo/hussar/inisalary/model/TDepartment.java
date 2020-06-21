package com.jxdinfo.hussar.inisalary.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author yxx
 * @since 2020-06-17
 */
@TableName("t_department")
public class TDepartment extends Model<TDepartment> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId("DEPARTMENT_ID")
    private Integer departmentId;

    /**
     * 部门名称
     */
    @TableField("DEPARTMENT_NAME")
    private String departmentName;


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    protected Serializable pkVal() {
        return this.departmentId;
    }

    @Override
    public String toString() {
        return "TDepartment{" +
        "departmentId=" + departmentId +
        ", departmentName=" + departmentName +
        "}";
    }
}
