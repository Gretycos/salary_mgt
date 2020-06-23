package com.jxdinfo.salary.SDepartment.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.OutSalary.model.MonthlySalary;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author hyc
 * @since 2020-06-17
 */
@TableName("t_department")
public class SDepartment extends Model<SDepartment> {

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
    /**
     * 部门下的员工列表
     */
    @TableField(exist = false)
    private List<MonthlySalary> MonthlySalaryList;


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

    public List<MonthlySalary> getMonthlySalaryList() {
        return MonthlySalaryList;
    }

    public void setMonthlySalaryList(List<MonthlySalary> MonthlySalaryList) {
        this.MonthlySalaryList = MonthlySalaryList;
    }

    @Override
    protected Serializable pkVal() {
        return this.departmentId;
    }

    @Override
    public String toString() {
        return "Department{" +
        "departmentId=" + departmentId +
        ", departmentName=" + departmentName +
        "}";
    }
}
