package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;

import cdi.LoginInfo;
import cdi.MatterForm;
import cdi.PageBean;
import cdi.SearchCondition;
import constants.CodeCons;
import constants.FunctionCons;
import constants.SearchCons;
import constants.StatusCons;
import db.SqlSessionManager;
import entity.Project;
import entity.Serial;
import mapper.MatterMapper;

@Transactional
@RequestScoped
public class MatterLogic {

	@Inject
	private SqlSessionManager sqlSessionManager;
	@Inject
	private LoginInfo loginInfo;

	@Inject
	private MatterForm form;

	public List<Project> getProjectList(PageBean page, SearchCondition searchCondition) {
		//案件一覧を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {

			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			Long countResult = matterMapper.getProjectCountByCondition(searchCondition);
			page.config(countResult, SearchCons.MAXDISP);
			List<Project> projectList = matterMapper.findByConditionLimit(searchCondition);
			this.changeString(projectList);

			return projectList;
		}
	}

	//住所が11文字以上だった場合、その先を「…」で表示する処理
	public void changeString(List<Project> projecttList) {

		//すべてのユーザーの住所をチェック
		for (Project r : projecttList) {

			// 11文字以上だった場合
			if (r.getProjectName().length() >= 11) {
				String name = r.getProjectName();
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append(name.substring(0, 10));
				stringBuilder.append("…");
				r.setProjectNameHidden(stringBuilder.toString());
			}
		}
	}
	public void createSerialNumber(){
		//採番を作成
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			Serial serial = new Serial();
			serial.setNumberingCode(this.form.getProjectCode());
			serial.setCreateUser(this.loginInfo.getStaffID());
			serial.setUpdateUser(this.loginInfo.getStaffID());
			matterMapper.createSerial(serial);

		}
	}

	@Transactional(rollbackOn = Exception.class)
	//案件を作成
	public int createProject() throws SQLException, IOException {

		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			this.createSerialNumber();
			Project project = new Project(this.form);
			project.setCreateUser(this.loginInfo.getStaffID());
			project.setUpdateUser(this.loginInfo.getStaffID());
			return matterMapper.create(project);

		}
	}

	public int updateProject(){
		//案件をアップデート
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);

			Project project = new Project(this.form);
			project.setUpdateUser(this.loginInfo.getStaffID());
			return matterMapper.updateProject(project);

		}
	}

	public String getStaffName(String staffID) {
		//スタッフ名を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			return matterMapper.getStaffName(staffID);

		}
	}

	public boolean isvaluesCorrect(boolean editFlag) {
		//整合性チェック
		if (this.form.getCustomerPhoneNumber().isEmpty() && this.form.getCustomerMailAddress().isEmpty()) {
			this.message = "電話番号かメールアドレスはいずれか入力してください";
			return false;
		}

		if (!this.hasLoginFunc()) {
			//案件管理機能存在[ログインした社員 ID のロールに案件管理機能が存在するか]
			this.message = "ログインユーザは案件管理機能を持ちません";
			return false;
		}
		if (!this.isSameCode()) {
			//案件コードと取引先コードの3桁が同一か
			this.message = "案件コードと取引先コードの先頭3桁が異なります，同一にしてください";
			return false;
		}

		if (!this.isProjectCodeNotDoubled(editFlag)) {
			//入力した案件コードがすでにあるか
			this.message = "同一の案件コードが既に存在します．異なるコードにしてください";
			return false;
		}
		if (!this.isAccInTable()) {
			//受注確度があるか
			this.message = "受注確度が存在しません，低中高何かにしてください";
			return false;
		}
		int status = this.form.getProjectStatus();
		if (!editFlag && (status != StatusCons.STATUS1_AP && status != StatusCons.STATUS2_HI)) {
			//案件ステータスが引合もしくはアプローチか
			this.message = "ステータスが正しくありません，正しいステータスにしてください";
			return false;
		}

		if (!this.isCustomerCodeCorrect()) {
			//取引先が存在するか
			this.message = "この取引先は存在しません";
			return false;
		}

		if (!this.isCustomerCodeDeleted()) {
			//取引先が存在するか
			this.message = "この取引先は削除されており不明です．";
			return false;
		}

		if (!this.isSectionCodeCorrect()) {
			///存在と削除チェック（所属コード)
			this.message = "この所属は存在しません";
			return false;
		}

		if (editFlag && !this.isNotEdited()) {
			System.out.println("etstEdit55555");
			this.message = "この案件情報は更新されています。一度戻り新しい情報に更新されてから再び試してください";
			return false;
		}
		System.out.println("testEdit5");

		return true;
	}

	//ここより下、insert時の入力整合性チェックのメソッド
	public boolean isNotEdited() {
		//楽観ロック
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			Date date = this.form.getUpdateDate();
			String projectCode = this.form.getProjectCode();
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getEdited(projectCode, date);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isSectionCodeCorrect() {
		//部署コードが正しいか判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String sectionCode = this.form.getSection();
			String divisionCode = sectionCode.substring(0, 3);
			String groupCode = sectionCode.substring(3, 6);
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getSectionCount(divisionCode, groupCode);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isCustomerCodeDeleted() {
		//削除されている取引先番号か判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String customerCode = this.form.getCustomerCode();
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getDeletedCustomerCount(customerCode);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isCustomerCodeCorrect() {
		//取引先コードが正しいか判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String customerCode = this.form.getCustomerCode();
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getCustomerCount(customerCode);
			if (c >= 1) {
				return true;

			} else {
				return false;
			}
		}
	}

	public boolean hasLoginFunc() {
		//ログイン機能があるか判定j：w
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int roleCode = this.loginInfo.getLoginStaff().getRoleCode();
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getFuncCount(roleCode, FunctionCons.F_MATTER);
			if (c >= 1) {
				return true;

			} else {
				return false;
			}
		}
	}

	public boolean isSameCode() {
		//同じ案件コードであるか判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = this.form.getProjectCode().substring(0, 3);
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getProjectCodeCount(projectCode);
			if (c >= 1) {
				return true;

			} else {
				return false;
			}
		}
	}

	public boolean isProjectCodeNotDoubled(boolean editFlag) {
		//同一の案件コードがあるか判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = this.form.getProjectCode();
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getProjectCodeInProjectTable(projectCode);
			System.out.println(editFlag);
			System.out.println("coutn;" + c);
			if (!editFlag && c == 0) {
				return true;
			} else if (editFlag && c == 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	public boolean isAccInTable() {
		//入力した受注確度がデータベースにあるか
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int acc = this.form.getAccuracy();
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getAccCount(CodeCons.CODEKEY2, acc);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	//ここまで整合性チェックのメソッド

	public boolean canDelete(String projectCode) {
		//削除できるかを判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.getReportFromProjectCode(projectCode);
			if (c < 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean deleteProject(String projectCode) {
		//案件を削除
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			MatterMapper matterMapper = session.getMapper(MatterMapper.class);
			int c = matterMapper.deleteProject(projectCode);
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

	private String message;
}
