package entity;

import java.io.Serializable;
import java.util.Date;

import cdi.ReportForm;
import constants.FlagCons;

public class Report implements Serializable {
	private String projectCode;
	private Date receivedDate;
	private int accuracy;
	private int status;
	private Date meetingDate;
	private int trafficCode;
	private String customer1;
	private String customer2;
	private String customer3;
	private String staff1;
	private String staff2;
	private String staff3;
	private char customerAddFlg;
	private char staffAddFlg;
	private String memo;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	private String memoHidden;

	public Report() {

	}

	public Report(ReportForm form) {
		this.projectCode = form.getProjectCode();
		this.receivedDate = form.getReceivedDate();
		this.customer1 = form.getCustomer1();
		this.customer2 = form.getCustomer2();
		this.customer3 = form.getCustomer3();
		this.staff1 = form.getStaff1();
		this.staff2 = form.getStaff2();
		this.staff3 = form.getStaff3();
		this.accuracy = form.getAccuracy();
		this.status = form.getStatus();
		if (form.isCustomerAddFlg()) {
			this.customerAddFlg = FlagCons.CUSTOMER_ADD_FLAG_TRUE;
		} else {
			this.customerAddFlg = FlagCons.CUSTOMER_ADD_FLAG_FALSE;
		}
		if (form.isStaffAddFlg()) {
			this.staffAddFlg = FlagCons.USER_ADD_FLAG_TRUE;
		} else {
			this.staffAddFlg = FlagCons.USER_ADD_FLAG_FALSE;
		}
		this.memo = form.getMemo();
		this.meetingDate = form.getMeetingDate();
		this.trafficCode = form.getTrafficCode();
		this.createDate = form.getCreateDate();
		this.createUser = form.getCreateUser();
		this.updateDate = form.getUpdateDate();
		this.updateUser = form.getUpdateUser();

	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTrafficCode() {
		return trafficCode;
	}

	public void setTrafficCode(int trafficCode) {
		this.trafficCode = trafficCode;
	}

	public String getCustomer1() {
		return customer1;
	}

	public void setCustomer1(String customer1) {
		this.customer1 = customer1;
	}

	public String getCustomer2() {
		return customer2;
	}

	public void setCustomer2(String customer2) {
		this.customer2 = customer2;
	}

	public String getCustomer3() {
		return customer3;
	}

	public void setCustomer3(String customer3) {
		this.customer3 = customer3;
	}

	public String getStaff1() {
		return staff1;
	}

	public void setStaff1(String staff1) {
		this.staff1 = staff1;
	}

	public String getStaff2() {
		return staff2;
	}

	public void setStaff2(String staff2) {
		this.staff2 = staff2;
	}

	public String getStaff3() {
		return staff3;
	}

	public void setStaff3(String staff3) {
		this.staff3 = staff3;
	}

	public char getCustomerAddFlg() {
		return customerAddFlg;
	}

	public void setCustomerAddFlg(char customerAddFlg) {
		this.customerAddFlg = customerAddFlg;
	}

	public char getStaffAddFlg() {
		return staffAddFlg;
	}

	public void setStaffAddFlg(char staffAddFlg) {
		this.staffAddFlg = staffAddFlg;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getMemoHidden() {
		return memoHidden;
	}

	public void setMemoHidden(String memoHidden) {
		this.memoHidden = memoHidden;
	}
}
