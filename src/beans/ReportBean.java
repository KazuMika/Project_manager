package beans;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cdi.Conv;
import cdi.DBMap;
import cdi.PageBean;
import cdi.PageBean2;
import cdi.ReportForm;
import constants.URLCons;
import constants.UserCons;
import entity.Project;
import entity.Report;
import logic.ReportLogic;

@Named
@ConversationScoped
public class ReportBean implements Serializable {

	@Inject
	private ReportLogic reportLogic;

	@Inject
	private ReportForm reportForm;

	@Inject
	private PageBean page;

	@Inject
	private PageBean2 page2;

	@Inject
	private DBMap map;

	@Inject
	private Conv conv;

	private String staffName;
	private String completeMessage;

	private String projectCode;
	private String projectName;
	private int projectNum;
	private String editMessage1;
	private String editMessage2;
	private String deleteMessage;
	private String sectionName;

	@PostConstruct
	public void init() {
		this.conv.open();
		this.page.topPage();
	}

	public void messageClear() {
		//メッセージクリア
		this.editMessage1 = null;
		this.editMessage2 = null;
		this.deleteMessage = null;
		this.completeMessage = null;
	}

	public void movePageInReport(String mode) {
		//ページング
		this.messageClear();
		switch (mode) {
		case "top":
			page2.topPage();
			page2.firstResult();
			break;
		case "past":
			page2.pastPage();
			page2.firstResult();
			break;
		case "next":
			page2.nextPage();
			page2.firstResult();
			break;
		case "last":
			page2.lastPage();
			page2.firstResult();
			break;
		}

		this.page2.setReportList(reportLogic.getReportList(page2, this.reportForm.getProject().getProjectCode()));
	}

	public String report(Project p) {
		//報告ボタン押下時動作
		this.messageClear();
		this.page2.topPage();
		this.reportForm.setProject(p);
		this.reportForm.setStaffName(this.reportLogic.getStaffName(p.getStaffID()));
		if (Objects.isNull(this.reportForm.getStaffName())) {
			this.reportForm.setStaffName(UserCons.UNKNOWN);
		}
		String projectCode = p.getProjectCode();
		this.page2.setReportList(reportLogic.getReportList(page2, projectCode));
		String sectionCode = p.getSectionCode() + p.getSectionGroupCode();
		if (this.map.getSectionR().containsKey(sectionCode)) {
			this.reportForm.setSectionName(this.map.getSectionR().get(sectionCode));
		} else {
			this.reportForm.setSectionName(UserCons.UNKNOWN);
		}

		return URLCons.REPORT_I;
	}

	public String delete(Report r) {
		//削除ボタン押下時動作
		this.messageClear();
		System.out.println(r.getProjectCode());
		if (this.reportLogic.hasOnlyOneReport(r)) {
			this.deleteMessage = "案件報告が削除対処一件のみのため削除できません";

		} else if (this.reportLogic.hasNewerReport(r)) {
			this.deleteMessage = "案件報告が削除対処一件のみ、または削除対処より新しい案件報告があるため削除できません";
		} else {
			try {
				this.reportLogic.deleteReport(r);
				this.deleteMessage = "案件情報を削除しました";
				this.page2.setReportList(
						this.reportLogic.getReportList(page2, this.reportForm.getProject().getProjectCode()));
			} catch (Exception e) {
				e.printStackTrace();
				return URLCons.ERROR;
			}
		}
		return null;
	}

	public String edit(Report r) {
		//編集ボタン押下時動作
		this.messageClear();
		this.reportForm.setEditFlag(true);
		this.reportForm.set(r);
		this.reportForm.getProject().setProjectStatus(r.getStatus());
		if (this.reportLogic.hasNewerReport(r)) {
			this.reportForm.setFirstReport(true);
		} else {
			this.reportForm.setFirstReport(false);
		}

		this.map.setpStatus(this.reportLogic.getConditionalStatus());

		return URLCons.REPORT_R;

	}

	public String register() {
		//新規登録ボタン押下時動作
		this.messageClear();
		this.reportForm.clear();
		if (this.reportLogic.isFirstReport(this.reportForm.getProject())) {

			this.reportForm.setFirstReport(true);
			this.reportForm.setStatus(this.reportForm.getProject().getProjectStatus());
			//一件も案件報告がない場合projectのstatusセット
			//その場合、statusを変更できないようにする(boolean firstReport)
		} else {
			this.reportForm.setFirstReport(false);
		}
		this.reportForm.setEditFlag(false);
		this.reportForm.setProjectCode(this.reportForm.getProject().getProjectCode());
		this.reportForm.setProjectName(this.reportForm.getProject().getProjectName());
		this.map.setpStatus(this.reportLogic.getConditionalStatus());
		return URLCons.REPORT_R;
	}

	public String registerC() {
		//情報入力ページの確認ボタン押下時動作
		this.messageClear();
		if (this.reportLogic.isvaluesCorrectForReport(this.reportForm.isEditFlag(), this.reportForm.isFirstReport(),
				this.reportForm.getProject())) {
			if (this.reportForm.isCustomerAddFlg()) {
				this.reportForm.setCustomerAddFlagForDisplay(UserCons.TRAFFIC_TRUE);
			} else {
				this.reportForm.setCustomerAddFlagForDisplay(UserCons.TRAFFIC_FALSE);
			}
			if (this.reportForm.isStaffAddFlg()) {
				this.reportForm.setStaffAddFlagForDisplay(UserCons.TRAFFIC_TRUE);
			} else {
				this.reportForm.setStaffAddFlagForDisplay(UserCons.TRAFFIC_FALSE);
			}
			return URLCons.REPORT_C;
		} else {
			return null;
		}
	}

	public String registerF() {
		//確認画面の実行ボタン押下時動作
		this.messageClear();
		if (this.reportLogic.isvaluesCorrectForReport(this.reportForm.isEditFlag(), this.reportForm.isFirstReport(),
				this.reportForm.getProject())) {
			try {
				this.projectNum = this.reportLogic.createAndEdit();

			} catch (Exception ex) {
				ex.printStackTrace();
				return URLCons.ERROR;
			}
			this.completeMessage = "下記の情報を新規登録しました";
			this.projectCode = this.reportForm.getProjectCode();
			this.projectName = this.reportForm.getProjectName();
			this.conv.close();
			this.reportForm.clear();
			return URLCons.REPORT_F;
		} else {
			return URLCons.REPORT_R;
		}
	}

	//	public Map<String,Integer> getConditionalStatus(){
	//		return this.reportLogic.getConditionalStatus();
	//
	//	}

	public ReportLogic getReportLogic() {
		return reportLogic;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setReportLogic(ReportLogic reportLogic) {
		this.reportLogic = reportLogic;
	}

	public ReportForm getReportForm() {
		return reportForm;
	}

	public void setReportForm(ReportForm reportForm) {
		this.reportForm = reportForm;
	}

	public String getCompleteMessage() {
		return completeMessage;
	}

	public void setCompleteMessage(String completeMessage) {
		this.completeMessage = completeMessage;
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

	public int getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(int projectNum) {
		this.projectNum = projectNum;
	}

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}

	public String getEditMessage1() {
		return editMessage1;
	}

	public void setEditMessage1(String editMessage1) {
		this.editMessage1 = editMessage1;
	}

	public String getEditMessage2() {
		return editMessage2;
	}

	public void setEditMessage2(String editMessage2) {
		this.editMessage2 = editMessage2;
	}

	public String getDeleteMessage() {
		return deleteMessage;
	}

	public void setDeleteMessage(String deleteMessage) {
		this.deleteMessage = deleteMessage;
	}

	public PageBean2 getPage2() {
		return page2;
	}

	public void setPage2(PageBean2 page2) {
		this.page2 = page2;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public DBMap getMap() {
		return map;
	}

	public void setMap(DBMap map) {
		this.map = map;
	}

}