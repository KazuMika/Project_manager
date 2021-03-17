package cdi;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import entity.Project;

/**
 * 案件用のフォームクラス
 */
@SessionScoped
public class MatterForm implements Serializable {

	@NotEmpty(message = "入力してください")
	@Size(min = 6, max = 6, message = "6文字にしてください")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "案件コードは英数字のみです")
	private String projectCode;

	@NotEmpty(message = "入力してください")
	@Size(max = 40, message = "40字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]+$", message = "案件名は全角のみです")
	private String projectName;

	@NotNull(message = "入力してください")
	private Date receivedDate;

	@NotNull(message = "入力してください")
	private int accuracy; //map

	@NotNull(message = "入力してください")
	private int projectStatus; //mapde  akiaito

	@NotEmpty(message = "入力してください")
	@Size(max = 100, message = "100字以内にしてください")
	@Pattern(regexp = "^[^ -~｡-ﾟ]+$", message = "メモは全角のみです")
	private String projectMemo;

	@NotEmpty(message = "入力してください")
	private String customerCode; //sql

	@Pattern(regexp = "^[0-9]{0,4}-*[0-9]{0,4}-*[0-9]{0,4}$", message = "数字かつハイフンを含めた形式で電話番号を入力してください")
	private String customerPhoneNumber;

	@Email(message = "メールアドレスの形式で入力してください")
	private String customerMailAddress;

	private String staffName;

	@NotEmpty(message = "入力してください")
	@Size(min = 6, max = 6, message = "6文字にしてください")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "主観部署コードは英数字のみです")
	private String section;

	//	private String sectionCode; //map
	//	private String sectionGroupCode; //mape setcion codeと一緒に
	@NotEmpty(message = "入力してください")
	@Size(min = 7, max = 7, message = "7文字にしてください")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "StaffIDは英数字のみです")
	private String staffID;

	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	private boolean editFlag = false;

	public MatterForm() {

	}

	public void set(Project p) {
		this.setEditFlag(true);
		this.setProjectCode(p.getProjectCode());
		this.setProjectName(p.getProjectName());
		this.setReceivedDate(p.getReceivedDate());
		this.setAccuracy(p.getAccuracy());
		this.setProjectStatus(p.getProjectStatus());
		this.setProjectMemo(p.getProjectMemo());
		this.setCustomerCode(p.getCustomerCode());
		this.setCustomerPhoneNumber(p.getCustomerPhoneNumber());
		this.setCustomerMailAddress(p.getCustomerMailAddress());
		this.setSection(p.getSectionCode() + p.getSectionGroupCode());
		this.setStaffID(p.getStaffID());
		this.setUpdateUser(p.getUpdateUser());
		this.setUpdateDate(p.getUpdateDate());
		this.setCreateDate(p.getCreateDate());
		this.setCreateUser(p.getCreateUser());

	}

	public void clear() {
		this.updateUser = null;
		this.updateDate = null;
		this.createUser = null;
		this.createDate = null;
		this.staffID = null;
		this.projectCode = null;
		this.projectName = null;
		this.receivedDate = null;
		this.accuracy = 0;
		this.projectStatus = 0;
		this.projectMemo = null;
		this.customerCode = null;
		this.customerPhoneNumber = null;
		this.customerMailAddress = null;
		this.section = null;
		this.staffID = null;
		this.editFlag = false;
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

	public void setCustomerPhoneNumber(String customerPhoneNubmer) {
		this.customerPhoneNumber = customerPhoneNubmer;
	}

	public String getCustomerMailAddress() {
		return customerMailAddress;
	}

	public void setCustomerMailAddress(String customerMailAddress) {
		this.customerMailAddress = customerMailAddress;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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

}
