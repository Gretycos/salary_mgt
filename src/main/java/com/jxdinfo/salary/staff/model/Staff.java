package com.jxdinfo.salary.staff.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.department.model.Department;

import java.io.Serializable;

/**
 * <p>
 * 职工表
 * </p>
 *
 * @author hyc
 * @since 2020-06-16
 */
@TableName("t_staff")
public class Staff extends Model<Staff> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工id
     */
    @TableId(value = "STAFF_ID", type = IdType.AUTO)
    private Integer staffId;
    /**
     * 姓名
     */
    @TableField("STAFF_NAME")
    private String staffName;
    /**
     * 性别
     */
    @TableField("GENDER")
    private String gender;
    /**
     * 部门ID
     */
//    @TableField("DEPARTMENT_ID")
//    private Integer departmentId;
    /**
     * 部门
     */
    @TableField(exist = false)
    private Department department;

    /**
     * 职位ID
     */
    @TableField("POSITION_ID")
    private Integer positionId;
    /**
     * 入职时间
     */
    @TableField("ENTRY_TIME")
    private Timestamp entryTime;
    /**
     * 离职时间
     */
    @TableField("DEPARTURE_TIME")
    private Timestamp departureTime;


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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public Integer getDepartmentId() {
//        return departmentId;
//    }
//
//    public void setDepartmentId(Integer departmentId) {
//        this.departmentId = departmentId;
//    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.staffId;
    }

    @Override
    public String toString() {
        return "Staff{" +
        "staffId=" + staffId +
        ", staffName=" + staffName +
        ", gender=" + gender +
        ", department=" + department.getDepartmentName() +
        ", positionId=" + positionId +
        ", entryTime=" + entryTime +
        ", departureTime=" + departureTime +
        "}";
    }
}
