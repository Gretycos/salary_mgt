package com.jxdinfo.salary.salaryList.model;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 员工奖金表
 * </p>
 *
 * @author wx
 * @since 2020-06-18
 */
@TableName("t_bonus")
public class TBonus extends Model<TBonus> {

    private static final long serialVersionUID = 1L;

    @TableField("STAFF_ID")
    private Integer staffId;
    /**
     * 员工姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
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


    /***
     * 新增
     * 得到用户的奖金总数
     * @return Integer
     */
    public Integer getTotal() {
        Integer t = 0;
        if (performanceAppraisalBonus!=null) t += performanceAppraisalBonus;
        if(noAbsentBonus!=null) t += noAbsentBonus;
        if(specialContributionsBonus != null) t += specialContributionsBonus;
        if(decemberBonus != null) t += decemberBonus;
        return t;

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
        return "TBonus{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", monthlyPayroll=" + monthlyPayroll +
        ", payTime=" + payTime +
        ", performanceAppraisalBonus=" + performanceAppraisalBonus +
        ", noAbsentBonus=" + noAbsentBonus +
        ", specialContributionsBonus=" + specialContributionsBonus +
        ", decemberBonus=" + decemberBonus +
        "}";
    }


}
