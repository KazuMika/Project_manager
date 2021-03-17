package entity;

import java.io.Serializable;
import java.util.Date;

import cdi.StaffForm;

public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	private String staffID;
	private String staffName;
	private String password;
	private String divisionCode;
	private String groupCode;
	private int roleCode;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	public Staff() {

	}

	public Staff(StaffForm form) {
		this.staffID = form.getStaffID();
		this.staffName = form.getStaffName();
		this.password = form.getNewPassword();
		this.divisionCode = form.getDivisionCode();
		this.groupCode = form.getGroupCode();
		this.roleCode = form.getRoleCode();
		this.createUser = form.getCreateUser();
		this.updateUser = form.getUpdateUser();
		this.createDate = form.getCreateDate();
		this.updateDate = form.getUpdateDate();
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public int getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(int roleCode) {
		this.roleCode = roleCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
