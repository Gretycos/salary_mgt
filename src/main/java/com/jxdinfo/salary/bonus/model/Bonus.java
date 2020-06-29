package com.jxdinfo.salary.bonus.model;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;

import java.io.Serializable;

/**
 * <p>
 * 员工奖金表
 * </p>
 *
 * @author zyx
 * @since 2020-06-24
 */
@TableName("t_bonus")
public class Bonus extends Model<Bonus> {

    private static final long serialVersionUID = 1L;

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
    private Department department;
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
     * 业绩考核奖金
     */
    @TableField("PERFORMANCE_APPRAISAL_BONUS")
    private Integer performanceAppraisalBonus;
    /**
     * 全勤奖金
     */
    @TableField("NO_ABSENT_BONUS")
    private Integer noAbsentBonus;
    /**
     * 特殊贡献奖金
     */
    @TableField("SPECIAL_CONTRIBUTIONS_BONUS")
    private Integer specialContributionsBonus;
    /**
     * 年终奖金
     */
    @TableField("DECEMBER_BONUS")
    private Integer decemberBonus;


    public Bonus(){super();}

    public Bonus(Integer staffId,String staffName,Department department,Integer monthlyPayroll,
                 Timestamp payTime,Integer performanceAppraisalBonus,Integer noAbsentBonus,Integer specialContributionsBonus,Integer decemberBonus)
    {
        this.staffId=staffId;
        this.staffName=staffName;
        this.department=department;
        this.monthlyPayroll=monthlyPayroll;
        this.payTime=payTime;
        this.performanceAppraisalBonus=performanceAppraisalBonus;
        this.noAbsentBonus=noAbsentBonus;
        this.specialContributionsBonus=specialContributionsBonus;
        this.decemberBonus=decemberBonus;
    }


    public Department getDepartment(){return department;}
    public void setDepartmentId(Department department){this.department=department;}

    /*public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }*/

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

    public Integer getPerformanceAppraisalBonus() {
        return performanceAppraisalBonus;
    }

    public void setPerformanceAppraisalBonus(Integer performanceAppraisalBonus) {
        this.performanceAppraisalBonus = performanceAppraisalBonus;
    }

    public Integer getNoAbsentBonus() {
        return noAbsentBonus;
    }

    public void setNoAbsentBonus(Integer noAbsentBonus) {
        this.noAbsentBonus = noAbsentBonus;
    }

    public Integer getSpecialContributionsBonus() {
        return specialContributionsBonus;
    }

    public void setSpecialContributionsBonus(Integer specialContributionsBonus) {
        this.specialContributionsBonus = specialContributionsBonus;
    }

    public Integer getDecemberBonus() {
        return decemberBonus;
    }

    public void setDecemberBonus(Integer decemberBonus) {
        this.decemberBonus = decemberBonus;
    }

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "Bonus{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", monthlyPayroll=" + monthlyPayroll +
        ",departmentId="+department.getDepartmentName()+
        ", payTime=" + payTime +
        ", performanceAppraisalBonus=" + performanceAppraisalBonus +
        ", noAbsentBonus=" + noAbsentBonus +
        ", specialContributionsBonus=" + specialContributionsBonus +
        ", decemberBonus=" + decemberBonus +
        "}";
    }
}
