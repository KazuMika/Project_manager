package entity;

import java.io.Serializable;
import java.util.Date;

public class Division implements Serializable {
	private static final long serialVersionUID = 1L;

	private String divisionCode;
	private String groupCode;
	private String divisionName;
	private String groupName;
	private int displayOrder;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	public Division(String divisionCode, String groupCode, String divisionName, String groupName, int displayOrder) {
		this.divisionCode = divisionCode;
		this.groupCode = groupCode;
		this.divisionName = divisionName;
		this.groupName = groupName;
		this.displayOrder = displayOrder;

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

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
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
