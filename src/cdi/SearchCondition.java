package cdi;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;

import constants.SearchCons;

/**
 * 案件用検索条件情報を持つクラス
 */
@SessionScoped
public class SearchCondition implements Serializable {

	private String projectCode;
	private String projectName;
	private Date receivedDateStart;
	private Date receivedDateEnd;
	private int accuracy;
	private int projectStatus;
	private String customerCode;

	private Integer firstRecordIndex; //DB読込時、何番目のレコードから読み込むか
	private Integer maxDisp = SearchCons.MAXDISP; //最大表示件数

	public void clear() {
		this.projectCode = null;
		this.projectName = null;
		this.receivedDateEnd = null;
		this.receivedDateStart = null;
		this.accuracy = 0;
		this.projectStatus = 0;
		this.customerCode = null;
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

	public Date getReceivedDateStart() {
		return receivedDateStart;
	}

	public void setReceivedDateStart(Date receivedDateStart) {
		this.receivedDateStart = receivedDateStart;
	}

	public Date getReceivedDateEnd() {
		return receivedDateEnd;
	}

	public void setReceivedDateEnd(Date receivedDateEnd) {
		this.receivedDateEnd = receivedDateEnd;
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

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
