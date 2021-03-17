package cdi;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import entity.Staff;

/**
 * スタッフ情報更新用フォームクラス
 */
@SessionScoped
public class StaffForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String staffID;

	@NotEmpty(message = "入力してください")
	@Size(max = 32, message = "32文字以内にしてください")
//	@Pattern(regexp = "^[^ -~｡-ﾟ]+$", message = "名前は全角のみです")
	private String staffName;

	@Size(max = 32, message = "32文字以内にしてください")
	@Pattern(regexp = "^[A-Za-z0-9]*$", message = "パスワードは英数字のみです")
	private String newPassword;

	@NotEmpty(message = "入力してください")
	@Size(max = 32, message = "32文字以内にしてください")
	@Pattern(regexp = "^[A-Za-z0-9]*$", message = "パスワードは英数字のみです")
	private String oldPassword;

	private String divisionAndGroupCode;
	private String divisionCode;
	private String groupCode;

	private int roleCode;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
	private boolean deleteFlag;
	private boolean secondAccess = false;
	private String hiddenPass;

	{
		this.secondAccess = false;
	}

	public void set(Staff staff) {
		this.staffID = staff.getStaffID();
		this.staffName = staff.getStaffName();
		this.oldPassword = staff.getPassword();
		this.divisionCode = staff.getDivisionCode();
		this.groupCode = staff.getGroupCode();
		this.divisionAndGroupCode = this.divisionAndGroupCode + this.groupCode;
		this.roleCode = staff.getRoleCode();
		this.createUser = staff.getCreateUser();
		this.updateUser = staff.getUpdateUser();
		this.createDate = staff.getCreateDate();
		this.updateDate = staff.getUpdateDate();
	}

	public void clear() {
		this.staffID = null;
		this.staffName = null;
		this.newPassword = null;
		this.oldPassword = null;
		this.divisionCode = null;
		this.groupCode = null;
		this.roleCode = 0;
		this.createUser = null;
		this.updateUser = null;
		this.createDate = null;
		this.updateDate = null;
		this.secondAccess = false;
		this.deleteFlag = false;
		this.hiddenPass = null;

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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDivisionAndGroupCode() {
		return divisionAndGroupCode;
	}

	public void setDivisionAndGroupCode(String divisionAndGroupCode) {
		this.divisionAndGroupCode = divisionAndGroupCode;
	}

	public boolean isSecondAccess() {
		return secondAccess;
	}

	public void setSecondAccess(boolean secondAccess) {
		this.secondAccess = secondAccess;
	}

	public String getHiddenPass() {
		return hiddenPass;
	}

	public void setHiddenPass(String hiddenPass) {
		this.hiddenPass = hiddenPass;
	}

}
