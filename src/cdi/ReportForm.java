package cdi;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import constants.FlagCons;
import entity.Project;
import entity.Report;

/**
 * 案件報告フォームクラスj
 */
@SessionScoped
public class ReportForm implements Serializable {

	@NotEmpty(message = "入力してください")
//	@Size(min = 6, max = 6, message = "6文字にしてください")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "案件コードは英数字のみです")
	private String projectCode;
	private String projectName;
	@NotNull(message = "入力してください")
	private Date receivedDate;

	@NotNull(message = "入力してください")
	private int accuracy; //map

	@NotNull(message = "入力してください")
	private int status;

	@NotNull(message = "入力してください")
	private Date meetingDate;

	@NotNull(message = "入力してください")
	private int trafficCode;

	@Size(max = 10, message = "10字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前は全角のみです")
	private String customer1;

	@Size(max = 10, message = "10字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前は全角のみです")
	private String customer2;

	@Size(max = 10, message = "10字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前は全角のみです")
	private String customer3;

	@Size(max = 10, message = "10字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前は全角のみです")
	private String staff1;

	@Size(max = 10, message = "10字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前は全角のみです")
	private String staff2;

	@Size(max = 10, message = "10字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "名前は全角のみです")
	private String staff3;

	@NotEmpty(message = "入力してください")
	@Size(max = 100, message = "100字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "メモは全角のみです")
	private String memo;

	private String memoHidden;

	private boolean customerAddFlg;
	private boolean staffAddFlg;
	private String staffName;
	private String sectionName;

	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	private boolean editFlag = false;
	private Project project;
	private boolean firstReport = false;
	private boolean notFirstReport;
	//初めて作成する案件報告か
	private String customerAddFlagForDisplay;
	private String staffAddFlagForDisplay;

	private boolean secondAccess;

	public void set(Report r) {
		this.projectCode = r.getProjectCode();
		this.projectName = this.project.getProjectName();
		this.receivedDate = r.getReceivedDate();
		this.customer1 = r.getCustomer1();
		this.customer2 = r.getCustomer2();
		this.customer3 = r.getCustomer3();
		this.staff1 = r.getStaff1();
		this.staff2 = r.getStaff2();
		this.staff3 = r.getStaff2();
		this.accuracy = r.getAccuracy();
		this.status = r.getStatus();
		if (r.getCustomerAddFlg() == FlagCons.CUSTOMER_ADD_FLAG_TRUE) {
			this.customerAddFlg = true;
		} else {
			this.customerAddFlg = false;
		}
		if (r.getStaffAddFlg() == FlagCons.USER_ADD_FLAG_TRUE) {
			this.staffAddFlg = true;
		} else {
			this.staffAddFlg = false;
		}
		this.meetingDate = r.getMeetingDate();
		this.trafficCode = r.getTrafficCode();
		this.memo = r.getMemo();
		this.createDate = r.getCreateDate();
		this.createUser = r.getCreateUser();
		this.updateDate = r.getUpdateDate();
		this.updateUser = r.getUpdateUser();
	}

	public void clear() {
		this.secondAccess = false;
		this.updateUser = null;
		this.updateDate = null;
		this.createUser = null;
		this.createDate = null;
		this.projectCode = null;
		this.projectName = null;
		this.receivedDate = null;
		this.customer1 = null;
		this.customer2 = null;
		this.customer3 = null;
		this.staff1 = null;
		this.staff2 = null;
		this.staff3 = null;
		this.accuracy = 0;
		this.status = 0;
		this.customerAddFlg = false;
		this.staffAddFlg = false;
		this.memo = null;
		this.editFlag = false;
		this.meetingDate = null;
		this.trafficCode = 0;
		this.createDate = null;
		this.createUser = null;
		this.updateDate = null;
		this.updateUser = null;
		this.notFirstReport = false;
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

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isCustomerAddFlg() {
		return customerAddFlg;
	}

	public void setCustomerAddFlg(boolean customerAddFlg) {
		this.customerAddFlg = customerAddFlg;
	}

	public boolean isStaffAddFlg() {
		return staffAddFlg;
	}

	public void setStaffAddFlg(boolean staffAddFlg) {
		this.staffAddFlg = staffAddFlg;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean isFirstReport() {
		return firstReport;
	}

	public void setFirstReport(boolean firstReport) {
		this.firstReport = firstReport;
	}

	public String getCustomerAddFlagForDisplay() {
		return customerAddFlagForDisplay;
	}

	public void setCustomerAddFlagForDisplay(String customerAddFlagForDisplay) {
		this.customerAddFlagForDisplay = customerAddFlagForDisplay;
	}

	public String getStaffAddFlagForDisplay() {
		return staffAddFlagForDisplay;
	}

	public void setStaffAddFlagForDisplay(String staffAddFlagForDisplay) {
		this.staffAddFlagForDisplay = staffAddFlagForDisplay;
	}

	public boolean isNotFirstReport() {
		return notFirstReport;
	}

	public void setNotFirstReport(boolean notFirstReport) {
		this.notFirstReport = notFirstReport;
	}

	public boolean isSecondAccess() {
		return secondAccess;
	}

	public void setSecondAccess(boolean secondAccess) {
		this.secondAccess = secondAccess;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getMemoHidden() {
		return memoHidden;
	}

	public void setMemoHidden(String memoHidden) {
		this.memoHidden = memoHidden;
	}

}
