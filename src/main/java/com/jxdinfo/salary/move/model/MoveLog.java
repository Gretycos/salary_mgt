package com.jxdinfo.salary.move.model;

import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.jxdinfo.salary.department.model.Department;
import com.jxdinfo.salary.position.model.Position;
import com.jxdinfo.salary.staff.model.Staff;

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
     * 操作员
     */
    @TableField("OPERATOR_NAME")
    private Staff operator;
    /**
     * 调动员工
     */
    @TableField(exist = false)
    private Staff move;
    /**
     * 操作时间
     */
    @TableField("OPERATION_TIME")
    private Timestamp operationTime;
    /**
     * 原部门
     */
    @TableField(exist = false)
    private Department oldDepartment;
    /**
     * 新部门
     */
    @TableField(exist = false)
    private Department newDepartment;
    /**
     * 原职位
     */
    @TableField(exist = false)
    private Position oldPosition;
    /**
     * 新职位
     */
    @TableField(exist = false)
    private Position newPosition;

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public Timestamp getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Timestamp operationTime) {
        this.operationTime = operationTime;
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

    public Department getOldDepartment() {
        return oldDepartment;
    }

    public void setOldDepartment(Department oldDepartment) {
        this.oldDepartment = oldDepartment;
    }

    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Position getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(Position oldPosition) {
        this.oldPosition = oldPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(Position newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    protected Serializable pkVal() {
        return this.operationId;
    }

    @Override
    public String toString() {
        return "MoveLog{" +
                "operationId='" + operationId + '\'' +
                ", operator=" + operator +
                ", move=" + move +
                ", operationTime=" + operationTime +
                ", oldDepartment=" + oldDepartment +
                ", newDepartment=" + newDepartment +
                ", oldPosition=" + oldPosition +
                ", newPosition=" + newPosition +
                '}';
    }
}
