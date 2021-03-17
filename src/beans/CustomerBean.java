package beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cdi.CustomerForm;
import cdi.DBMap;
import cdi.PageBeanForCustomer;
import cdi.SearchConditionForCustomer;
import constants.URLCons;
import entity.Customer;
import logic.CustomerLogic;
import logic.LoginLogic;

@Named
@RequestScoped
public class CustomerBean implements Serializable {

	private String staffName;
	private String completeMessage;

	private String customerCode;
	private String customerName;
	private int resultNum;
	private String deleteMessage;

	@Inject
	private CustomerForm form;

	@Inject
	private LoginLogic loginLogic;

	@Inject
	private CustomerLogic logic;

	@Inject
	private DBMap map;

	@Inject
	private SearchConditionForCustomer condition;

	@Inject
	private PageBeanForCustomer page;

	@PostConstruct
	public void init() {
		//ページ表示時実行メソッド
		if (!condition.isSecondAccess()) {
			this.page.topPage();
			this.condition.clear();
			this.condition.setFirstRecordIndex(page.firstResult());
			this.page.setCustomerList(this.logic.getCustomerList(page, this.condition));
			this.condition.setSecondAccess(true);
		}
	}

	public void movePage(String mode) {
		//ページングメソッド
		switch (mode) {
		case "top":
			page.topPage();
			this.condition.setFirstRecordIndex(page.firstResult());
			break;
		case "past":
			page.pastPage();
			this.condition.setFirstRecordIndex(page.firstResult());
			break;
		case "next":
			page.nextPage();
			this.condition.setFirstRecordIndex(page.firstResult());
			break;
		case "last":
			page.lastPage();
			this.condition.setFirstRecordIndex(page.firstResult());
			break;
		}
		this.page.setCustomerList(this.logic.getCustomerList(page, this.condition));
	}

	public String searchClear() {
		//検索条件初期化メソッド
		this.condition.clear();
		this.page.topPage();
		this.page.setCustomerList(this.logic.getCustomerList(page, this.condition));
		return null;

	}

	public String search() {
		//検索メソッド
		movePage("top");
		if (Objects.nonNull(this.condition.getUpdateDateEnd())) {
			Date endDate = setCreateDateEndTime(this.condition.getUpdateDateEnd());
			condition.setUpdateDateEnd(endDate);
		}

		this.page.setCustomerList(this.logic.getCustomerList(page, this.condition));
		return null;
	}

	public Date setCreateDateEndTime(Date date) {
		//時間を00:00;00->23:59:59に変更するメソッド
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public String edit(Customer c) {
		//編集メソッド
		this.form.setRegisterFlag(false);
		this.form.setEditFlag(true);
		this.form.setDeleteFlagForForm(false);
		this.form.setDeleteFlag('0');
		this.form.set(c);
		return URLCons.CUSTOMER_R;
	}

	public String delete(Customer c) {
		//削除メソッド
		this.form.setRegisterFlag(false);
		this.form.setEditFlag(false);
		this.form.setDeleteFlagForForm(true);
		this.form.setDeleteFlag('1');
		this.form.set(c);
		if (this.logic.reportHasThisCustomer()) { //削除される取引先が取引先と登録されている案件の件数を取得するメソッド
			this.deleteMessage = "この取引先は案件に登録されていますが、削除してもよろしいでしょうか？";
		}
		return URLCons.CUSTOMER_R;

	}

	public String register() {
		//登録情報入力メソッド
		this.form.clear();
		this.form.setRegisterFlag(true);
		this.form.setEditFlag(false);
		this.form.setDeleteFlagForForm(false);
		this.form.setDeleteFlag('0');
		return URLCons.CUSTOMER_R;
	}

	public String registerC() {
		//登録情報確認メソッド
		if (this.logic.isValuesCorrect()) {
			return URLCons.CUSTOMER_C;
		} else {
			return null;
		}
	}

	public String registerF() {
		//登録完了メソッド
		if (this.logic.isValuesCorrect()) {
			try {
				if (this.form.isEditFlag()) {
					this.resultNum = this.logic.updateCustomer();
					this.completeMessage = "下記の情報を編集しました";
				} else if (this.form.isDeleteFlagForForm()) {
					this.resultNum = this.logic.deleteCustomer();
					this.completeMessage = "下記の情報を削除しました";
				} else {
					this.resultNum = this.logic.createCustomer();
					this.completeMessage = "下記の情報を新規登録しました";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return URLCons.ERROR;
			}

			this.map.getCustomerCode().clear();
			this.map.getCustomerCodeR().clear();
			this.map.setCustomerCode(this.loginLogic.getCustomerCode());
			this.map.getCustomerCode().forEach((k, v) -> {
				this.map.getCustomerCodeR().put(v, k);
			});
			this.customerCode = this.form.getCustomerCode();
			this.customerName = this.form.getCustomerName();
			this.form.clear();
			this.page.topPage();
			this.condition.clear();
			this.condition.setFirstRecordIndex(page.firstResult());
			this.page.setCustomerList(this.logic.getCustomerList(page, this.condition));
			return URLCons.CUSTOMER_F;
		} else {
			return URLCons.CUSTOMER_R;
		}
	}

	public CustomerForm getForm() {
		return form;
	}

	public void setForm(CustomerForm form) {
		this.form = form;
	}

	public CustomerLogic getLogic() {
		return logic;
	}

	public void setLogic(CustomerLogic logic) {
		this.logic = logic;
	}

	public DBMap getMap() {
		return map;
	}

	public void setMap(DBMap map) {
		this.map = map;
	}

	public SearchConditionForCustomer getCondition() {
		return condition;
	}

	public void setCondition(SearchConditionForCustomer condition) {
		this.condition = condition;
	}

	public PageBeanForCustomer getPage() {
		return page;
	}

	public void setPage(PageBeanForCustomer page) {
		this.page = page;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCompleteMessage() {
		return completeMessage;
	}

	public void setCompleteMessage(String completeMessage) {
		this.completeMessage = completeMessage;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeleteMessage() {
		return deleteMessage;
	}

	public void setDeleteMessage(String deleteMessage) {
		this.deleteMessage = deleteMessage;
	}

	public int getResultNum() {
		return resultNum;
	}

	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}

	public LoginLogic getLoginLogic() {
		return loginLogic;
	}

	public void setLoginLogic(LoginLogic loginLogic) {
		this.loginLogic = loginLogic;
	}

}