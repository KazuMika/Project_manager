package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cdi.Conv;
import cdi.DBMap;
import cdi.MatterForm;
import cdi.PageBean;
import cdi.SearchCondition;
import constants.URLCons;
import constants.UserCons;
import entity.Project;
import logic.MatterLogic;

@Named
//@RequestScoped
@ConversationScoped
public class MatterBean implements Serializable {

	@Inject
	private MatterLogic matterLogic;

	@Inject
	private MatterForm matterForm;

	@Inject
	private Conv conv;

	@Inject
	private PageBean page;

	@Inject
	private SearchCondition searchCondition;

	@Inject
	private DBMap map;

	private String staffName;
	private String completeMessage;

	private String projectCode;
	private String projectName;
	private int projectNum;
	private String editMessage1;
	private String editMessage2;
	private String deleteMessage;

	@PostConstruct
	public void init() {
		//開始時メソッド
		conv.open();
		this.page.topPage();
		searchCondition.clear();
		this.searchCondition.setFirstRecordIndex(page.firstResult());
		this.page.setProjectList(this.matterLogic.getProjectList(page, searchCondition));
		this.setCustomerToUnKnown();

	}

	public void messageClear() {
		//メッセージ削除
		this.editMessage1 = null;
		this.editMessage2 = null;
		this.deleteMessage = null;
		this.completeMessage = null;

	}

	public void movePage(String mode) {
		//ページング
		this.messageClear();

		switch (mode) {

		case "top":
			page.topPage();
			searchCondition.setFirstRecordIndex(page.firstResult());
			break;
		case "past":
			page.pastPage();
			searchCondition.setFirstRecordIndex(page.firstResult());
			break;
		case "next":
			page.nextPage();
			searchCondition.setFirstRecordIndex(page.firstResult());
			break;
		case "last":
			page.lastPage();
			searchCondition.setFirstRecordIndex(page.firstResult());
			break;
		}

		this.page.setProjectList(this.matterLogic.getProjectList(page, searchCondition));
		this.setCustomerToUnKnown();
	}

	public String searchClear() {
		//検索内容クリア
		this.messageClear();
		this.searchCondition.clear();
		this.page.topPage();
		this.searchCondition.setFirstRecordIndex(page.firstResult());
		this.page.setProjectList(this.matterLogic.getProjectList(page, searchCondition));
		this.setCustomerToUnKnown();
		return null;

	}

//	public void StringToTenLength() {
//		for(Project p : this.page.getProjectList()) {
//
//		}
//
//	}

	public String search() {
		//検索
		this.messageClear();
		movePage("top");
		if (Objects.nonNull(this.searchCondition.getReceivedDateEnd())) {
			Date endDate = setCreateDateEndTime(this.searchCondition.getReceivedDateEnd());
			searchCondition.setReceivedDateEnd(endDate);

		}
		this.page.setProjectList(this.matterLogic.getProjectList(page, searchCondition));
		this.setCustomerToUnKnown();

		return null;
	}

	public Date setCreateDateEndTime(Date date) {
		//時間を23:59:59に
		this.messageClear();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	public String edit(Project p) {
		//編集
		this.messageClear();
		this.matterForm.set(p);
		if (!this.map.getSectionR().containsKey(this.matterForm.getSection())) {
			this.editMessage1 = "この所属は削除されており不明です、異なる主管部署を選択してください";
		}
		if (!this.map.getCustomerCode().containsKey(this.matterForm.getCustomerCode())) {
			this.editMessage2 = "この取引先は削除されており不明です．";
		} else {
			this.matterForm.setCustomerCode(this.map.getCustomerCode().get(this.matterForm.getCustomerCode()));
		}
		return URLCons.MATTER_R;

	}

	public String delete(Project p) {
		//削除
		this.messageClear();
		String projectCode = p.getProjectCode();
		if (this.matterLogic.canDelete(projectCode)) {
			this.matterLogic.deleteProject(projectCode);
			this.deleteMessage = "案件情報を削除しました";
			this.page.setProjectList(this.matterLogic.getProjectList(page, searchCondition));
		} else {
			this.deleteMessage = "案件報告があるため削除できませんでした";
		}
		return null;
	}

	public String register() {
		//登録
		this.messageClear();
		this.matterForm.clear();
		this.matterForm.setEditFlag(false);
		this.matterForm.setAccuracy(5);
		return URLCons.MATTER_R;
	}

	public String registerC() {
		//変更確認
		this.messageClear();
		if (this.matterLogic.isvaluesCorrect(this.matterForm.isEditFlag())) {
			this.staffName = this.matterLogic.getStaffName(this.matterForm.getStaffID());
			if (Objects.isNull(this.staffName)) {
				this.staffName = UserCons.UNKNOWN;
			}
			return URLCons.MATTER_C;
		} else {
			return null;
		}
	}

	public String registerF() {
		//変更完了
		this.messageClear();
		if (this.matterLogic.isvaluesCorrect(this.matterForm.isEditFlag())) {
			try {
				if (this.matterForm.isEditFlag()) {
					this.projectNum = this.matterLogic.updateProject();
				} else {
					this.projectNum = this.matterLogic.createProject();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return URLCons.ERROR;
			}
			this.completeMessage = "下記の情報を新規登録しました";
			this.projectCode = this.matterForm.getProjectCode();
			this.projectName = this.matterForm.getProjectName();
			this.matterForm.clear();
			this.conv.close();
			return URLCons.MATTER_F;
		} else {
			return URLCons.MATTER_R;
		}
	}

	public void setCustomerToUnKnown() {
		//取引先の名前を不明に変更
		for (Project p : this.page.getProjectList()) {
			if (!this.map.getCustomerCodeR().containsKey(p.getCustomerCode())) {
				p.setCustomerCode(UserCons.UNKNOWN);
			} else {
				p.setCustomerCode(this.map.getCustomerCodeR().get(p.getCustomerCode()));
			}
		}
	}

	public MatterLogic getMatterLogic() {
		return matterLogic;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setMatterLogic(MatterLogic matterLogic) {
		this.matterLogic = matterLogic;
	}

	public MatterForm getMatterForm() {
		return matterForm;
	}

	public void setMatterForm(MatterForm matterForm) {
		this.matterForm = matterForm;
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

	public SearchCondition getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(SearchCondition searchCondition) {
		this.searchCondition = searchCondition;
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

	public DBMap getMap() {
		return map;
	}

	public void setMap(DBMap map) {
		this.map = map;
	}

	public Conv getConv() {
		return conv;
	}

	public void setConv(Conv conv) {
		this.conv = conv;
	}

}