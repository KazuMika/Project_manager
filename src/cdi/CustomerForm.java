package cdi;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import entity.Customer;


/**
 * 取引先の登録・編集用のクラス
 */
@SessionScoped
public class CustomerForm implements Serializable {

	@NotEmpty(message = "入力してください")
	@Size(min = 3, max = 3, message = "3文字で入力してください")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "取引先コードは英数字のみです")
	private String customerCode;

	@NotEmpty(message = "入力してください")
	@Size(max = 64, message = "64字以内にしてください")
	//	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "取引先名は全角のみです")
	private String customerName;

	@Size(max = 64, message = "64字以内にしてください")
	//	@Pattern(regexp = "^[^ -~｡-ﾟ]*$", message = "案件名は全角のみです")
	private String address;

	@Pattern(regexp = "^[0-9]{0,4}-*[0-9]{0,4}-*[0-9]{0,4}$", message = "数字のみ，ハイフンを含めた形式で電話番号を入力してください")
	private String phoneNumber;

	@Pattern(regexp = "[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]*", message = "URLの形式で入力してください")
	private String url;

	private char deleteFlag;

	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	private boolean registerFlag;
	private boolean editFlag;
	private boolean deleteFlagForForm;

	public CustomerForm() {
	}

	public void set(Customer c) {
		this.customerCode = c.getCustomerCode();
		this.customerName = c.getCustomerName();
		this.address = c.getAddress();
		this.phoneNumber = c.getPhoneNumber();
		this.url = c.getUrl();
		this.updateUser = c.getUpdateUser();
		this.updateDate = c.getUpdateDate();
		this.createUser = c.getCreateUser();
		this.createDate = c.getCreateDate();

	}

	public void clear() {
		this.customerCode = null;
		this.customerName = null;
		this.address = null;
		this.phoneNumber = null;
		this.url = null;
		this.deleteFlag = '0';
		this.editFlag = false;
		this.registerFlag = false;
		this.deleteFlagForForm = false;
		this.updateUser = null;
		this.updateDate = null;
		this.createUser = null;
		this.createDate = null;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public boolean isEditFlag() {
		return editFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public boolean isDeleteFlagForForm() {
		return deleteFlagForForm;
	}

	public void setDeleteFlagForForm(boolean deleteFlagForForm) {
		this.deleteFlagForForm = deleteFlagForForm;
	}

	public char getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(char deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public boolean isRegisterFlag() {
		return registerFlag;
	}

	public void setRegisterFlag(boolean registerFlag) {
		this.registerFlag = registerFlag;
	}

}
