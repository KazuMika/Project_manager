package entity;

import java.io.Serializable;
import java.util.Date;

import cdi.MatterForm;

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	private String projectCode;
	private String projectName;
	private Date receivedDate;
	private int accuracy;
	private int projectStatus;
	private String projectMemo;
	private String customerCode;
	private String customerPhoneNumber;
	private String customerMailAddress;
	private String sectionCode;
	private String sectionGroupCode;
	private String staffID;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
	private String projectNameHidden;

	public Project() {

	}

	public Project(MatterForm form) {
		this.projectCode = form.getProjectCode();
		this.projectName = form.getProjectName();
		this.receivedDate = form.getReceivedDate();
		this.accuracy = form.getAccuracy();
		this.projectStatus = form.getProjectStatus();
		this.projectMemo = form.getProjectMemo();
		this.customerCode = form.getCustomerCode();
		this.customerPhoneNumber = form.getCustomerPhoneNumber();
		this.customerMailAddress = form.getCustomerMailAddress();
		this.sectionCode = form.getSection().substring(0, 3);
		this.sectionGroupCode = form.getSection().substring(3, 6);
		this.staffID = form.getStaffID();
		this.updateUser = form.getUpdateUser();
		this.updateDate = form.getUpdateDate();
		this.createUser = form.getCreateUser();
		this.createDate = form.getCreateDate();

	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getProjectMemo() {
		return projectMemo;
	}

	public void setProjectMemo(String projectMemo) {
		this.projectMemo = projectMemo;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCustomerMailAddress() {
		return customerMailAddress;
	}

	public void setCustomerMailAddress(String customerMailAddress) {
		this.customerMailAddress = customerMailAddress;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSectionGroupCode() {
		return sectionGroupCode;
	}

	public void setSectionGroupCode(String sectionGroupCode) {
		this.sectionGroupCode = sectionGroupCode;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
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

	public String getProjectNameHidden() {
		return projectNameHidden;
	}

	public void setProjectNameHidden(String projectNameHidden) {
		this.projectNameHidden = projectNameHidden;
	}

}
