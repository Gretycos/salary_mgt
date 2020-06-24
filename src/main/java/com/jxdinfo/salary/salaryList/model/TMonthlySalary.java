package com.jxdinfo.salary.salaryList.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 员工每月工资表
 * </p>
 *
 * @author wx
 * @since 2020-06-18
 */
@TableName("t_monthly_salary")
public class TMonthlySalary extends Model<TMonthlySalary> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    @TableField("STAFF_ID")
    private Integer staffId;
    /**
     * 员工姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
    /**
     * 部门ID
     */
    @TableField("DEPARTMENT_ID")
    private Integer departmentId;
    /**
     * 职位ID
     */
    @TableField("POSITION_ID")
    private Integer positionId;
    /**
     * 基本工资金额
     */
    @TableField("SALARY_AMOUNT")
    private Integer salaryAmount;
    /**
     * 年限工资金额
     */
    @TableField("SALARY_OF_AGE")
    private Integer salaryOfAge;
    /**
     * 五险一金金额
     */
    @TableField("IAHF")
    private Integer iahf;
    /**
     * 奖金金额
     */
    @TableField("AWARD_AMOUNT")
    private Integer awardAmount;
    /**
     * 工资总计
     */
    @TableField("TOTAL")
    private Integer total;

    public TMonthlySalary(){}

    public TMonthlySalary(Integer staffId, String staffName, Integer departmentId, Integer positionId,
                          Integer salaryAmount, Integer salaryOfAge, Integer iahf, Integer awardAmount, Integer total) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.salaryAmount = salaryAmount;
        this.salaryOfAge = salaryOfAge;
        this.iahf = iahf;
        this.awardAmount = awardAmount;
        this.total = total;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Integer salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public Integer getSalaryOfAge() {
        return salaryOfAge;
    }

    public void setSalaryOfAge(Integer salaryOfAge) {
        this.salaryOfAge = salaryOfAge;
    }

    public Integer getIahf() {
        return iahf;
    }

    public void setIahf(Integer iahf) {
        this.iahf = iahf;
    }

    public Integer getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(Integer awardAmount) {
        this.awardAmount = awardAmount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "TMonthlySalary{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", departmentId=" + departmentId +
        ", positionId=" + positionId +
        ", salaryAmount=" + salaryAmount +
        ", salaryOfAge=" + salaryOfAge +
        ", iahf=" + iahf +
        ", awardAmount=" + awardAmount +
        ", total=" + total +
        "}";
    }
}
