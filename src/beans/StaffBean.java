package beans;

import java.io.Serializable;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cdi.DBMap;
import cdi.LoginInfo;
import cdi.StaffForm;
import constants.URLCons;
import entity.Staff;
import logic.LoginLogic;
import logic.StaffLogic;

@Named
@RequestScoped
//@ConversationScoped
public class StaffBean implements Serializable {

	private String message;
	private String completeMessage;

	@Inject
	private LoginInfo loginInfo;

	@Inject
	private StaffForm form;

	@Inject
	private StaffLogic staffLogic;

	@Inject
	private DBMap map;

	@Inject
	private LoginLogic loginLogic;

	private int updateStaffNum;

	@PostConstruct
	public void init() {
		if (!form.isSecondAccess()) {
			this.form.set(loginInfo.getLoginStaff());
			String divisionAndGroupCode = this.form.getDivisionCode() + this.form.getGroupCode();
			this.form.setDivisionAndGroupCode(divisionAndGroupCode);
			this.form.setSecondAccess(true);
		}
	}

	public String editC() {
		//編集ボタン押下時動作
		this.form.setDeleteFlag(false);
		if (this.staffLogic.isValueCorrect()) {
			this.form.setDivisionCode(this.form.getDivisionAndGroupCode().substring(0, 3));
			this.form.setGroupCode(this.form.getDivisionAndGroupCode().substring(3, 6));
			String hiddenPass = String.join("", Collections.nCopies(this.form.getNewPassword().length(), "*"));
			this.form.setHiddenPass(hiddenPass);
			return URLCons.STAFF_C;
		} else {
			return null;
		}
	}

	public String deleteC() {
		//削除ボタン押下時動作
		this.form.setDeleteFlag(true);
		if (this.staffLogic.isValueCorrect()) {
			return URLCons.STAFF_C;
		} else {
			return null;
		}
	}

	public String doEditOrDelete() {
		//確認ページの実行ボタン押下時動作
		System.out.println(this.form.isDeleteFlag());
		try {
			if (this.form.isDeleteFlag() && this.staffLogic.isValueCorrect()) {
				this.updateStaffNum = this.staffLogic.deleteStaff();
				return this.logout();
			} else if (!this.form.isDeleteFlag() && this.staffLogic.isValueCorrect()) {
				this.updateStaffNum = this.staffLogic.updateStaff();
				this.completeMessage = "編集が完了しました";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return URLCons.ERROR;
		}
		this.loginInfo.setPassword(this.form.getNewPassword());
		this.form.clear();
		Staff loginStaff = this.loginLogic.getLoginUser();
		this.loginInfo.setLoginStaff(loginStaff);
		return URLCons.STAFF_F;

	}

	public String logout() {
		this.loginInfo.clear();
		this.form.clear();
		return URLCons.LOGIN;
	}

	public String complete() {
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public StaffForm getForm() {
		return form;
	}

	public void setForm(StaffForm form) {
		this.form = form;
	}

	public StaffLogic getStaffLogic() {
		return staffLogic;
	}

	public void setStaffLogic(StaffLogic staffLogic) {
		this.staffLogic = staffLogic;
	}

	public DBMap getMap() {
		return map;
	}

	public void setMap(DBMap map) {
		this.map = map;
	}

	public LoginLogic getLoginLogic() {
		return loginLogic;
	}

	public void setLoginLogic(LoginLogic loginLogic) {
		this.loginLogic = loginLogic;
	}

	public int getUpdateStaffNum() {
		return updateStaffNum;
	}

	public void setUpdateStaffNum(int updateStaffNum) {
		this.updateStaffNum = updateStaffNum;
	}

	public String getCompleteMessage() {
		return completeMessage;
	}

	public void setCompleteMessage(String completeMessage) {
		this.completeMessage = completeMessage;
	}

}