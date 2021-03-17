package cdi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import entity.Project;

/**
 * 案件用のページングクラス
 */
@SessionScoped
public class PageBean implements Serializable {

	private long resultNum = 0L;
	private int currentPage = 1;
	private int maxDisp = 1;

	private long maxPage = 1;

	private String userId;

	private String userName;

	private Date fromDate;
	private Date toDate;

	private boolean searched = false;
	private Integer firstResultNum;

	private List<Project> projectList = new ArrayList<>();

	public PageBean() {
	}

	public void config(Long resultNum, Integer maxDisp) {
		this.resultNum = resultNum;
		this.maxDisp = maxDisp;
		this.maxPage = this.resultNum % this.maxDisp == 0 ? this.resultNum / this.maxDisp
				: this.resultNum / this.maxDisp + 1;
	}

	public boolean pastPageExt() {
		return this.currentPage > 1;

	}

	public boolean nextPageExt() {
		return this.currentPage < maxPage;
	}

	public void topPage() {
		this.currentPage = 1;

	}

	public void lastPage() {
		this.currentPage = (int) maxPage;

	}

	public void nextPage() {
		if (this.nextPageExt()) {
			this.currentPage++;

		}
	}

	public void pastPage() {
		if (this.pastPageExt()) {
			this.currentPage -= 1;
		}
	}

	public Integer firstResult() {
		this.firstResultNum = (this.currentPage - 1) * this.maxDisp;
		return this.firstResultNum;

	}

	public Integer maxDisp() {
		return this.maxDisp();
	}

	public long getResultNum() {
		return resultNum;
	}

	public void setResultNum(long resultNum) {
		this.resultNum = resultNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxDisp() {
		return maxDisp;
	}

	public void setMaxDisp(int maxDisp) {
		this.maxDisp = maxDisp;
	}

	public long getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(long maxPage) {
		this.maxPage = maxPage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isSearched() {
		return searched;
	}

	public void setSearched(boolean searched) {
		this.searched = searched;
	}

	public Integer getFirstResultNum() {
		return firstResultNum;
	}

	public void setFirstResultNum(Integer firstResultNum) {
		this.firstResultNum = firstResultNum;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

}
