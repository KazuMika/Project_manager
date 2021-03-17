package entity;

import java.io.Serializable;
import java.util.Date;

import cdi.CustomerForm;

public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String customerCode;
	private String customerName;
	private String address;
	private String phoneNumber;
	private String url;
	private char deleteFlag;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	public Customer() {

	}

	public Customer(CustomerForm form) {

		this.customerCode = form.getCustomerCode();
		this.customerName = form.getCustomerName();
		this.address = form.getAddress();
		this.phoneNumber = form.getPhoneNumber();
		this.url = form.getUrl();
		this.deleteFlag = form.getDeleteFlag();
		this.createDate = form.getCreateDate();
		this.createUser = form.getCreateUser();
		this.updateDate = form.getUpdateDate();
		this.updateUser = form.getUpdateUser();

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public char getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(char deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
