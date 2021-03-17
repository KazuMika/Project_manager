package entity;

import java.io.Serializable;
import java.util.Date;

public class Serial implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numberingCode;
	private int currentCount;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	public String getNumberingCode() {
		return numberingCode;
	}

	public void setNumberingCode(String numberingCode) {
		this.numberingCode = numberingCode;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
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
