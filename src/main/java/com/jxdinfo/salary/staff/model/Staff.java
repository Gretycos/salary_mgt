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
import com.jxdinfo.salary.position.model.Position;

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
     * 部门
     */
    @TableField(exist = false)
    private Department department;
    /**
     * 职位
     */
    @TableField(exist = false)
    private Position position;
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

    public Staff(){}

    public Staff(Integer staffId, String staffName, String gender, Department department, Position position, Timestamp entryTime) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.gender = gender;
        this.department = department;
        this.position = position;
        this.entryTime = entryTime;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
        ", position=" + position.getPositionName() +
        ", entryTime=" + entryTime +
        ", departureTime=" + departureTime +
        "}";
    }
}
