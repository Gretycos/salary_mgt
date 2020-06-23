package com.jxdinfo.salary.OutSalary.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import com.jxdinfo.salary.SDepartment.model.SDepartment;
import com.jxdinfo.salary.TPosition.model.TPosition;
/**
 * <p>
 * 员工每月工资表
 * </p>
 *
 * @author 
 * @since 2020-06-17
 */
@TableName("t_monthly_salary")
public class MonthlySalary extends Model<MonthlySalary> {

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
    @TableField(exist = false)
    private SDepartment department;
    /**
     * 职位ID
     */
    @TableField(exist = false)
    private TPosition position;
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
    //构造函数
    public MonthlySalary(){
        super();
    }

    public MonthlySalary(Integer staffId, String staffName, SDepartment department, TPosition position, Integer salaryAmount, Integer salaryOfAge, Integer iahf, Integer awardAmount){
        super();
        this.staffId = staffId;
        this.staffName = staffName;
        this.department = department;
        this.position = position;
        this.salaryAmount = salaryAmount;
        this.salaryOfAge = salaryOfAge;
        this.iahf = iahf;
        this.awardAmount = awardAmount;
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

    ////////
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
    ///////

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
        return "MonthlySalary{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", department=" + department.getDepartmentName() +
        ", position=" + position.getPositionName() +
        ", salaryAmount=" + salaryAmount +
        ", salaryOfAge=" + salaryOfAge +
        ", iahf=" + iahf +
        ", awardAmount=" + awardAmount +
        "}";
    }
}
