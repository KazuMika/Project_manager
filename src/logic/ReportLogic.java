package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;

import cdi.LoginInfo;
import cdi.PageBean;
import cdi.PageBean2;
import cdi.ReportForm;
import cdi.SearchCondition;
import constants.CodeCons;
import constants.FunctionCons;
import constants.SearchCons;
import db.SqlSessionManager;
import entity.Code;
import entity.Project;
import entity.Report;
import entity.Serial;
import mapper.ReportMapper;

@Transactional
@RequestScoped
public class ReportLogic {

	@Inject
	private SqlSessionManager sqlSessionManager;
	@Inject
	private LoginInfo loginInfo;

	@Inject
	private ReportForm form;

	public Map<String, Integer> getConditionalStatus() {
		//表示するstatusの値を遷移可能なステータスのみにする
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int currProjectStatus = this.form.getProject().getProjectStatus();
			List<Code> codes = reportMapper.getConditionalStatus(CodeCons.CODEKEY1, currProjectStatus);
			Map<String, Integer> codesMap = new LinkedHashMap<>();
			for (Code c : codes) {
				codesMap.put(c.getCodeName(), c.getCodeValue());
				System.out.println(c.getCodeName());
			}
			return codesMap;
		}
	}

	public List<Report> getReportList(PageBean2 page, String projectCode) {
		//案件報告一覧を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int firstResult = page.firstResult();
			Long countResult = reportMapper.getReportFromProjectCode2(projectCode);
			page.config(countResult, SearchCons.MAXDISP);
			List<Report> reportList = reportMapper.findReportByConditionLimit(projectCode, firstResult,
					SearchCons.MAXDISP);
			this.changeString(reportList);
			return reportList;
		}
	}

	//住所が11文字以上だった場合、その先を「…」で表示する処理
	public void changeString(List<Report> reportList) {

		//すべてのユーザーの住所をチェック
		for (Report r : reportList) {

			// 11文字以上だった場合
			if (r.getMemo().length() >= 11) {
				String memo = r.getMemo();
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append(memo.substring(0, 10));
				stringBuilder.append("…");
				r.setMemoHidden(stringBuilder.toString());
			}
		}
	}

	public List<Project> getProjectList(PageBean page, SearchCondition searchCondition) {
		//案件一覧を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			Long countResult = reportMapper.getUserCountByCondition(searchCondition);
			page.config(countResult, SearchCons.MAXDISP);
			List<Project> projectList = reportMapper.findByConditionLimit(searchCondition);
			return projectList;
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public int createAndEdit() throws SQLException, IOException {
		//編集か作成を実行
		int c = 0;
		if (!this.form.isEditFlag()) {
			this.updateSerial();
			Serial serial = this.getSerial();
			String projectCode13 = this.createProjectCode13(serial);
			this.form.setProjectCode(projectCode13);
			c = this.createReport();
		} else {
			c = this.updateReport();
		}
		this.updateProjectWithReport();
		return c;

	}

	public int updateSerial() throws SQLException, IOException {
		//採番をアップデート
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			String projectCode6 = this.form.getProjectCode();
			String updateUser = this.loginInfo.getStaffID();
			return reportMapper.updateSerial(projectCode6, updateUser);

		}
	}

	public Serial getSerial() throws SQLException, IOException {
		//採番番号を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			String projectCode6 = this.form.getProjectCode();
			return reportMapper.getSerial(projectCode6);

		}
	}

	public String createProjectCode13(Serial serial) {
		//serialNum = projectCode
		//案件報告コードを取得
		String numberingCode = serial.getNumberingCode();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numberingCode.length(); i++) {
			if (!Character.isDigit(numberingCode.charAt(i))) {
				sb.append("0");
			} else {
				sb.append(numberingCode.charAt(i));
			}
		}
		int saiban = Integer.parseInt(sb.toString()) + (serial.getCurrentCount());
		String saibanStr = String.valueOf(saiban);
		int checkDegit = this.checkDegit(saibanStr);
		saibanStr = saibanStr + String.valueOf(checkDegit);
		String projectCode13 = this.form.getProjectCode() + saibanStr;
		return projectCode13;

	}

	public int checkDegit(String saiban) {
		//デジットチェック
		int checkDegit = 0;
		for (int i = saiban.length() - 1; i >= 0; i--) {
			if ((i + 1) % 2 == 0) { //index+1が桁数
				checkDegit += Character.getNumericValue(saiban.charAt(i));
			} else {
				checkDegit += Character.getNumericValue(saiban.charAt(i)) * 3;
			}
		}
		checkDegit %= 10;
		return checkDegit;

	}

	public int createReport() throws SQLException, IOException {
		//案件報告を作成

		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);

			Report report = new Report(this.form);
			report.setCreateUser(this.loginInfo.getStaffID());
			report.setUpdateUser(this.loginInfo.getStaffID());
			return reportMapper.createReport(report);

		}
	}

	public int updateReport() throws SQLException, IOException {
		//案件報告をアップデート
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			Report report = new Report(this.form);
			report.setUpdateUser(this.loginInfo.getStaffID());
			return reportMapper.updateReport(report);

		}
	}

	@Transactional(rollbackOn = Exception.class)
	public int updateProjectWithReport() throws SQLException, IOException {
		//案件報告をアップデート時、案件のステータスなどを同時にアップデート
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			Project project = this.form.getProject();
			String projectCode = project.getProjectCode();
			projectCode = projectCode + "%";
			Report report = reportMapper.getMostRecentUpdateDateReport(projectCode);
			project.setAccuracy(report.getAccuracy());
			project.setReceivedDate(report.getReceivedDate());
			project.setProjectStatus(report.getStatus());
			project.setUpdateUser(this.loginInfo.getLoginStaff().getStaffID());
			return reportMapper.updateProjectWithReport(project);

		}
	}

	public String getStaffName(String staffID) {
		//スタッフ名を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			return reportMapper.getStaffName(staffID);

		}
	}

	public boolean isvaluesCorrectForReport(boolean editFlag, boolean firstReport, Project p) {
		//整合性チェック
		if (this.form.getCustomer1().isEmpty() && this.form.getCustomer2().isEmpty()
				&& this.form.getCustomer3().isEmpty()) {
			this.message = "取引先側の参加者は1人以上にしてください";
			return false;
		}
		if (this.form.getStaff1().isEmpty() && this.form.getStaff2().isEmpty()
				&& this.form.getStaff3().isEmpty()) {
			this.message = "当社側の参加者は1人以上にしてください";
			return false;
		}

		if (!this.hasLoginFunc()) {
			//案件管理機能存在[ログインした社員 ID のロールに案件管理機能が存在するか]
			this.message = "ログインユーザは案件管理機能を持ちません";
			return false;
		}

		if (!editFlag && !this.hasProjectCode()) {
			//入力した案件コードがすでにあるか
			this.message = "案件コードが存在しません．存在する案件を報告してください";
			return false;
		}

		//		if (!editFlag && this.form.getProjectCode().length() != LengthCons.PROJECTCODE_LENGTH) {
		//			this.message = "案件コードが6桁ではありません";
		//			return false;
		//		}
		//		if (editFlag && this.form.getProject().getProjectCode().length() != LengthCons.PROJECTCODE_LENGTH) {
		//			this.message = "案件コードが6桁ではありません";
		//			return false;
		//		}
		//postで持ってきた時必要

		int previousStatus = p.getProjectStatus();
		int currentStatus = this.form.getStatus();
		if (previousStatus != currentStatus) {
			//ステータス遷移が正しいか
			if (!this.isCorrectStatus(previousStatus, currentStatus)) {
				this.message = "ステータスの遷移が適切ではありません。適切な遷移にしてください";
				return false;
			}
		}

		if (editFlag && !this.isOptimisticUpdate()) {
			//楽観ロック
			this.message = "他のユーザによって編集されています．一度ページを更新して再び編集を行ってください";
			return false;
		}

		return true;
	}

	public boolean isOptimisticUpdate() {
		//楽観ロック
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			String projectCode = this.form.getProjectCode();
			Date updateDate = this.form.getUpdateDate();
			int c = reportMapper.getUpdateCount(projectCode, updateDate);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isCorrectStatus(int preStatus, int currStatus) {
		//正しいステータス遷移か
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getStatusTransitionNum(preStatus, currStatus);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isNotEdited() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			Date date = this.form.getUpdateDate();
			String projectCode = this.form.getProjectCode();
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getEdited(projectCode, date);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean hasLoginFunc() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int roleCode = this.loginInfo.getLoginStaff().getRoleCode();
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getFuncCount(roleCode, FunctionCons.F_MATTER);
			if (c >= 1) {
				return true;

			} else {
				return false;
			}
		}
	}

	public boolean isSameCode() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = this.form.getProjectCode().substring(0, 3);
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getProjectCodeCount(projectCode);
			if (c >= 1) {
				return true;

			} else {
				return false;
			}
		}
	}

	public boolean hasProjectCode() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = this.form.getProjectCode();
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getProjectCodeInProjectTable(projectCode);
			if (c == 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	public boolean isAccInTable() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int acc = this.form.getAccuracy();
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getAccCount(CodeCons.CODEKEY2, acc);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isFirstReport(Project p) {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = p.getProjectCode();
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getReportFromProjectCode(projectCode);
			if (c == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean hasOnlyOneReport(Report r) {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = r.getProjectCode().substring(0, 6);
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getReportFromProjectCode(projectCode);
			if (c == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean hasNewerReport(Report r) {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String projectCode = r.getProjectCode().substring(0, 6);
			Date receivedDate = r.getReceivedDate();
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			int c = reportMapper.getNewReport(projectCode, receivedDate);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public int deleteReport(Report r) throws SQLException, IOException {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			ReportMapper reportMapper = session.getMapper(ReportMapper.class);
			String projectCode = r.getProjectCode();
			int c = reportMapper.deleteReport(projectCode);
			this.updateProjectWithReport();
			return c;
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
