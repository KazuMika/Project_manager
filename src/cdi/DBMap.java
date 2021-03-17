package cdi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import entity.Project;
import entity.Report;


/**
 * メニュー・プルダウン用のMapを所持するクラス
 */
@SessionScoped
public class DBMap implements Serializable {

	private List<Project> projectList = new ArrayList<>();
	private List<Report> reportList = new ArrayList<>();

	private Map<String, Integer> acc = new LinkedHashMap<String, Integer>();
	private Map<Integer, String> accR = new LinkedHashMap<Integer, String>();
	private Map<String, Integer> pStatus = new LinkedHashMap<String, Integer>();
	private Map<Integer, String> pStatusR = new LinkedHashMap<Integer, String>();;
	private Map<String, String> customerCode = new LinkedHashMap<String, String>();
	private Map<String, String> customerCodeR = new LinkedHashMap<String, String>();

	private Map<String, Integer> pStatusForRegister = new LinkedHashMap<String, Integer>();
	private Map<Integer, String> pStatusRForRegister = new LinkedHashMap<Integer, String>();;

	private Map<String, String> section = new LinkedHashMap<String, String>();
	private Map<String, String> sectionR = new LinkedHashMap<String, String>();

	private Map<String, Integer> trafficCode = new LinkedHashMap<String, Integer>();
	private Map<Integer, String> trafficCodeR = new LinkedHashMap<Integer, String>();

	private List<Integer> funcListForFilter = new ArrayList<>();

	public void clear() {
		this.pStatus.clear();
		this.pStatusR.clear();
		this.customerCode.clear();
		this.customerCodeR.clear();
		this.pStatusForRegister.clear();
		this.pStatusRForRegister.clear();
		this.section.clear();
		this.sectionR.clear();
		this.projectList.clear();
		this.reportList.clear();
		this.acc.clear();
		this.accR.clear();
		this.funcListForFilter.clear();
		this.trafficCode.clear();
		this.trafficCodeR.clear();
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public List<Report> getReportList() {
		return reportList;
	}

	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}

	public Map<String, Integer> getAcc() {
		return acc;
	}

	public void setAcc(Map<String, Integer> acc) {
		this.acc = acc;
	}

	public Map<Integer, String> getAccR() {
		return accR;
	}

	public void setAccR(Map<Integer, String> accR) {
		this.accR = accR;
	}

	public Map<String, Integer> getpStatus() {
		return pStatus;
	}

	public void setpStatus(Map<String, Integer> pStatus) {
		this.pStatus = pStatus;
	}

	public Map<Integer, String> getpStatusR() {
		return pStatusR;
	}

	public void setpStatusR(Map<Integer, String> pStatusR) {
		this.pStatusR = pStatusR;
	}

	public Map<String, String> getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(Map<String, String> customerCode) {
		this.customerCode = customerCode;
	}

	public Map<String, String> getCustomerCodeR() {
		return customerCodeR;
	}

	public void setCustomerCodeR(Map<String, String> customerCodeR) {
		this.customerCodeR = customerCodeR;
	}

	public Map<String, Integer> getpStatusForRegister() {
		return pStatusForRegister;
	}

	public void setpStatusForRegister(Map<String, Integer> pStatusForRegister) {
		this.pStatusForRegister = pStatusForRegister;
	}

	public Map<Integer, String> getpStatusRForRegister() {
		return pStatusRForRegister;
	}

	public void setpStatusRForRegister(Map<Integer, String> pStatusRForRegister) {
		this.pStatusRForRegister = pStatusRForRegister;
	}

	public Map<String, String> getSection() {
		return section;
	}

	public void setSection(Map<String, String> section) {
		this.section = section;
	}

	public Map<String, String> getSectionR() {
		return sectionR;
	}

	public void setSectionR(Map<String, String> sectionR) {
		this.sectionR = sectionR;
	}

	public Map<String, Integer> getTrafficCode() {
		return trafficCode;
	}

	public void setTrafficCode(Map<String, Integer> trafficCode) {
		this.trafficCode = trafficCode;
	}

	public Map<Integer, String> getTrafficCodeR() {
		return trafficCodeR;
	}

	public void setTrafficCodeR(Map<Integer, String> trafficCodeR) {
		this.trafficCodeR = trafficCodeR;
	}

	public List<Integer> getFuncListForFilter() {
		return funcListForFilter;
	}

	public void setFuncListForFilter(List<Integer> funcListForFilter) {
		this.funcListForFilter = funcListForFilter;
	}

}
