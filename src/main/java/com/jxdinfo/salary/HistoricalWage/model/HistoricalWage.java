package com.jxdinfo.salary.HistoricalWage.model;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.SDepartment.model.SDepartment;
import com.jxdinfo.salary.TPosition.model.TPosition;

import java.io.Serializable;

/**
 * <p>
 * 员工历史工资表
 * </p>
 *
 * @author 
 * @since 2020-06-21
 */
@TableName("t_historical_wage")
public class HistoricalWage extends Model<HistoricalWage> {

    private static final long serialVersionUID = 1L;

    @TableField("STAFF_ID")
    private Integer staffId;
    /**
     * 员工姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
    @TableField(exist = false)
    private SDepartment department;
    @TableField(exist = false)
    private TPosition position;
    /**
     * 发放工资月份
     */
    @TableField("MONTHLY_PAYROLL")
    private Integer monthlyPayroll;
    /**
     * 发放工资时间
     */
    @TableField("PAY_TIME")
    private Timestamp payTime;
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

public HistoricalWage(){
    super();
}



    public SDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SDepartment department) {
        this.department = department;
    }

    public TPosition getPosition() {
        return position;
    }

    public void setPosition(TPosition position) {
        this.position = position;
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




    public Integer getMonthlyPayroll() {
        return monthlyPayroll;
    }

    public void setMonthlyPayroll(Integer monthlyPayroll) {
        this.monthlyPayroll = monthlyPayroll;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
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

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "HistoricalWage{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", departmentId=" + department.getDepartmentId() +
        ", positionId=" + position.getPositionId() +
        ", monthlyPayroll=" + monthlyPayroll +
        ", payTime=" + payTime +
        ", salaryAmount=" + salaryAmount +
        ", salaryOfAge=" + salaryOfAge +
        ", iahf=" + iahf +
        ", awardAmount=" + awardAmount +
        "}";
    }
}
