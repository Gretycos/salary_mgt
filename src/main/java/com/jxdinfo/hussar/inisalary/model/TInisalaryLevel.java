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
 * 
 * </p>
 *
 * @author yxx
 * @since 2020-06-16
 */
@TableName("t_inisalary_level")
public class TInisalaryLevel extends Model<TInisalaryLevel> {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId("DEPARTMENT_ID")
    private Integer departmentId;
    /**
     * 部门
     */
    @TableField(exist = false)
    private TDepartment department;

    /**
     * 等级
     */
    @TableField("LEVEL")
    private Integer level;
    /**
     * 最低年限
     */
    @TableField("MINIMUM_AGE")
    private Integer minimumAge;
    /**
     * 最高年限
     */
    @TableField("MAXIMUM_AGE")
    private Integer maximumAge;
    /**
     * 初始工资
     */
    @TableField("INITIAL_SALARY")
    private Integer initialSalary;


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public TDepartment getDepartment() {
        return department;
    }

    public void setDepartment(TDepartment department) {
        this.department = department;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(Integer minimumAge) {
        this.minimumAge = minimumAge;
    }

    public Integer getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(Integer maximumAge) {
        this.maximumAge = maximumAge;
    }

    public Integer getInitialSalary() {
        return initialSalary;
    }

    public void setInitialSalary(Integer initialSalary) {
        this.initialSalary = initialSalary;
    }

    @Override
    protected Serializable pkVal() {
        return this.departmentId;
    }

    @Override
    public String toString() {
        return "TInisalaryLevel{" +
        "departmentId=" + departmentId +
        ", level=" + level +
        ", minimumAge=" + minimumAge +
        ", maximumAge=" + maximumAge +
        ", initialSalary=" + initialSalary +
        "}";
    }
}
