package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;

import cdi.LoginInfo;
import cdi.StaffForm;
import constants.FunctionCons;
import db.SqlSessionManager;
import entity.Staff;
import mapper.StaffMapper;

@Transactional
@RequestScoped
public class StaffLogic {

	@Inject
	private SqlSessionManager sqlSessionManager;
	@Inject
	private LoginInfo loginInfo;

	private String message;

	@Inject
	private StaffForm form;

	public int updateStaff() throws SQLException, IOException {
		//スタッフ情報を更新
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			StaffMapper staffMapper = session.getMapper(StaffMapper.class);
			Staff staff = new Staff(this.form);
			staff.setUpdateUser(this.loginInfo.getLoginStaff().getStaffID());
			return staffMapper.updateStaff(staff);

		}
	}

	public int deleteStaff() throws SQLException, IOException {
		//スタッフを削除
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			StaffMapper staffMapper = session.getMapper(StaffMapper.class);
			String staffID = this.form.getStaffID();
			int c = staffMapper.deleteStaff(staffID);
			return c;
		}
	}

	public boolean isValueCorrect() {
		//新しいパスワードがからか
		//古いパスワードは正しいか
		//1.user編集権限を持つか
		//2.古いパスワードと新しいパスワードが異なるか
		//3.updateDateが同じか楽観ロック
		//4.staffIDは同じものが一つか
		if (!this.form.getOldPassword().equals(this.loginInfo.getPassword())) {
			this.message = "古いパスワードが間違っています正しいパスワードを入力してください";
			return false;
		}
		if (!this.form.isDeleteFlag() && this.form.getNewPassword().isEmpty()) {
			this.message = "編集の場合は新しいパスワードを入力してください";
			return false;
		}
		if (!this.hasEditAuth()) {
			this.message = "このスタッフはユーザ情報変更権限を持ちません";
			return false;
		}
		if (!this.form.isDeleteFlag() && this.form.getOldPassword().equals(this.form.getNewPassword())) {
			this.message = "異なるパスワードにしてください";
			return false;
		}
		if (!this.isSameUpdateDate()) {
			this.message = "他のユーザによって更新されています。一度戻り再び試してください";
			return false;
		}
		if (this.form.getStaffID() != this.loginInfo.getLoginStaff().getStaffID()) {
			this.message = "編集を行うスタッフと編集されるスタッフIDが異なります．同一のIDのみ編集可能です";
			return false;
		}

		return true;

	}

	public boolean hasEditAuth() {
		//1.user編集権限を持つか
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			StaffMapper staffMapper = session.getMapper(StaffMapper.class);
			int roleCode = this.form.getRoleCode();
			int c = staffMapper.hasEditAuth(roleCode, FunctionCons.F_USER_ALTER);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isSameUpdateDate() {
		//2.updateDateが同一か
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			StaffMapper staffMapper = session.getMapper(StaffMapper.class);
			String staffID = this.form.getStaffID();
			Date updateDate = this.form.getUpdateDate();
			int c = staffMapper.isSameUpdateDate(staffID, updateDate);
			System.out.println(c);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public SqlSessionManager getSqlSessionManager() {
		return sqlSessionManager;
	}

	public void setSqlSessionManager(SqlSessionManager sqlSessionManager) {
		this.sqlSessionManager = sqlSessionManager;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StaffForm getForm() {
		return form;
	}

	public void setForm(StaffForm form) {
		this.form = form;
	}

}
