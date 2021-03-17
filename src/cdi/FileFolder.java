package cdi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.Part;

import entity.Division;

/**
 * 所属登録時の情報を持つクラス
 */
@SessionScoped
public class FileFolder implements Serializable {

	private Part uploadFile;

	private List<Division> divisionList = new ArrayList<Division>();

	private int deleteStaffCount;
	private int insertStaffCount;

	public void clear() {
		this.divisionList.clear();
		this.uploadFile = null;
	}

	public Part getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(Part uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<Division> getDivisionList() {
		return divisionList;
	}

	public void setDivisionList(List<Division> divisionList) {
		this.divisionList = divisionList;
	}

	public int getDeleteStaffCount() {
		return deleteStaffCount;
	}

	public void setDeleteStaffCount(int deleteStaffCount) {
		this.deleteStaffCount = deleteStaffCount;
	}

	public int getInsertStaffCount() {
		return insertStaffCount;
	}

	public void setInsertStaffCount(int insertStaffCount) {
		this.insertStaffCount = insertStaffCount;
	}

}
