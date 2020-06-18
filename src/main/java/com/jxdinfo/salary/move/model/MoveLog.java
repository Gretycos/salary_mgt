package com.jxdinfo.salary.move.model;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.position.model.Position;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 调动记录表
 * </p>
 *
 * @author zxn
 * @since 2020-06-17
 */
@TableName("t_move_log")
public class MoveLog extends Model<MoveLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作ID
     */
    @TableId("OPERATION_ID")
    private String operationId;
    /**
     * 操作员ID
     */
    @TableField("OPERATOR_ID")
    private Integer operatorId;
    /**
     * 操作员
     */
    @TableField("OPERATOR_NAME")
    private Staff operator;
    /**
     * 调动员工ID
     */
    @TableField("MOVE_ID")
    private Integer moveId;
    /**
     * 调动员工
     */
    @TableField("MOVE_NAME")
    private Staff move;

    /**
     * 操作时间
     */
    @TableField("OPERATION_TIME")
    private Timestamp operationTime;
    /**
     * 原部门
     */
    @TableField("OLD_DEPARTMENT_NAME")
    private Department oldDepartment;
    /**
     * 原职位
     */
    @TableField("OLD_POSITION_NAME")
    private Position oldPosition;
    /**
     * 新部门
     */
    @TableField("NEW_DEPARTMENT_NAME")
    private Department newDepartment;
    /**
     * 新职位
     */
    @TableField("NEW_POSITION_NAME")
    private Position newPosition;

    //部门类
    public class Department{
        private Integer departmentId;
        private String departmentName;
        private List<Staff> staff;
        public Integer getDepartmentId(){
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

        public List<Staff> getStaff() {
            return staff;
        }

        public void setStaff(List<Staff> staff) {
            this.staff = staff;
        }
    }

    //职位类
    public class Position{
        private Integer positionId;
        private String positionName;
        private List<Staff> staff;
        public Integer getPositionId(){
            return positionId;
        }

        public void setPositionId(Integer positionId) {
            this.positionId = positionId;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }
        public List<Staff> getStaff() {
            return staff;
        }

        public void setStaff(List<Staff> staff) {
            this.staff = staff;
        }
    }

    //员工类
    public class Staff {
        private Integer staffId;
        private String staffName;
        private String gender;
        private Integer departmentId;
        private Integer positionId;
        private Position position;
        private Timestamp entryTime;
        private Timestamp departureTime;
        private Department department;

        public Integer getDepartmentId(){
            return departmentId;
        }
        public void setDepartmentId(Integer departmentId){
            this.departmentId = departmentId;
        }
        public Integer getPositionId(){ return positionId; }
        public void setPositionId(Integer positionId){
            this.positionId = positionId;
        }
        public Staff(Integer staffId,String staffName,String gender,Position position,Timestamp entryTime,Timestamp departureTime,Department department){
            super();
            this.staffId = staffId;
            this.staffName = staffName;
            this.gender = gender;
            this.position = position;
            this.entryTime = entryTime;
            this.departureTime = departureTime;
            this.department = department;
        }
        public Staff(){
            super();
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
        public Timestamp getEntryTime(){
            return entryTime;
        }
        public void setEntryTime(Timestamp entryTime) {
            this.entryTime = entryTime;
        }
        public Timestamp getDepartureTime(){
            return departureTime;
        }
        public void setDepartureTime(Timestamp departureTime) {
            this.departureTime = departureTime;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        public Position getPosition() { return position; }

        public void setPosition(Position position) {
            this.position = position;
        }
    }


    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Department getOldDepartment() {
        return oldDepartment;
    }

    public void setOldDepartment(Department oldDepartment) {
        this.oldDepartment = oldDepartment;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    public Staff getOperator() {
        return operator;
    }

    public void setOperator(Staff operator) {
        this.operator = operator;
    }

    public Staff getMove() {
        return move;
    }

    public void setMove(Staff move) {
        this.move = move;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getMoveId() {
        return moveId;
    }

    public void setMoveId(Integer moveId) {
        this.moveId = moveId;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.operationId;
    }

    @Override
    public String toString() {
        return "MoveLog{" +
        "operationId=" + operationId +
        ", operatorId=" + operatorId + ", operatorName=" + operator.getStaffName() +
        ", moveId=" + moveId + ", moveName=" + move.getStaffName() +
        ", oldDepartmentId=" + oldDepartment.getDepartmentId() + ", oldDepartmentName=" + oldDepartment.getDepartmentName() +
        ", newDepartmentId=" + newDepartment.getDepartmentId() + ", newDepartmentNme=" + newDepartment.getDepartmentName() +
        ", oldPositionId=" + oldPosition.getPositionId() + ", oldPositionName=" + oldPosition.getPositionName() +
        ", newPositionId=" + newPosition.getPositionId() + ", newPositionName=" + newPosition.getPositionName() +
        ", operationTime=" + operationTime +
        "}";
    }
}
