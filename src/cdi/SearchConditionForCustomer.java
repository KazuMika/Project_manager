package cdi;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;

import constants.SearchCons;

/**
 * 取引先用検索条件を持つクラス
 */
@SessionScoped
public class SearchConditionForCustomer implements Serializable {

	//フィールドに検索条件を保存
	private String customerCode;
	private String customerName;
	private String address;
	private Date updateDateStart;
	private Date updateDateEnd;

	private Integer firstRecordIndex; //DB読込時、何番目のレコードから読み込むか
	private Integer maxDisp = SearchCons.MAXDISP; //最大表示件数

	private boolean secondAccess = false;

	public boolean isSecondAccess() {
		return secondAccess;
	}

	public void setSecondAccess(boolean secondAccess) {
		this.secondAccess = secondAccess;
	}

	public void clear() {
		this.customerCode = null;
		this.customerName = null;
		this.updateDateEnd = null;
		this.updateDateStart = null;
		this.address = null;
	}

	public Date getUpdateDateStart() {
		return updateDateStart;
	}

	public void setUpdateDateStart(Date updateDateStart) {
		this.updateDateStart = updateDateStart;
	}

	public Date getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(Date updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
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

	public Integer getFirstRecordIndex() {
		return firstRecordIndex;
	}

	public void setFirstRecordIndex(Integer firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}

	public Integer getMaxDisp() {
		return maxDisp;
	}

	public void setMaxDisp(Integer maxDisp) {
		this.maxDisp = maxDisp;
	}

}
