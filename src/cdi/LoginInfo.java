package cdi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotEmpty;

import entity.MFunctionForMenu;
import entity.Staff;

/**
 * ログイン情報を持つクラス
 */
@SessionScoped
public class LoginInfo implements Serializable {

	private boolean loggedIn = false;
	private Staff loginStaff;
	private String loginDate;
	private List<MFunctionForMenu> functions = new ArrayList<MFunctionForMenu>();

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	@NotEmpty(message = "入力してください")
//	@Size(max = 32, message = "32文字以下にしてください")
//	@Pattern(regexp = "^[A-Za-z0-9\\_\\-]+$", message = "IDは英数字，-と_のみです")
	private String staffID;

	@NotEmpty(message = "入力してください")
//	@Size(max = 32, message = "32文字以下にしてください")
//	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "PassWordは英数字のみです")
	private String password;

	public void clear() {
		this.loggedIn = false;
		this.loginStaff = null;
		this.staffID = null;
		this.password = null;
		this.functions.clear();
	}

	public Staff getLoginStaff() {
		return loginStaff;
	}

	public void setLoginStaff(Staff loginStaff) {
		this.loginStaff = loginStaff;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public List<MFunctionForMenu> getFunctions() {
		return functions;
	}

	public void setFunctions(List<MFunctionForMenu> functions) {
		this.functions = functions;
	}

}
