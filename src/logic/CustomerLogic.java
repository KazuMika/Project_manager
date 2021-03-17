package logic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;

import cdi.CustomerForm;
import cdi.LoginInfo;
import cdi.PageBeanForCustomer;
import cdi.SearchConditionForCustomer;
import constants.FunctionCons;
import constants.SearchCons;
import db.SqlSessionManager;
import entity.Customer;
import mapper.CustomerMapper;

@Transactional
@RequestScoped
public class CustomerLogic {

	@Inject
	private SqlSessionManager sqlSessionManager;

	@Inject
	private LoginInfo loginInfo;

	@Inject
	private CustomerForm form;

	public boolean reportHasThisCustomer() {
		//削除される取引先を取引先として登録している案件報告があるかを判定j
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			String customerCode = this.form.getCustomerCode();
			int c = customerMapper.reportsHaveThisCustomer(customerCode);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public List<Customer> getCustomerList(PageBeanForCustomer page, SearchConditionForCustomer searchCondition) {
		//取引先一覧を検索条件に一致するよう取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			Long countResult = customerMapper.getProjectCountByCondition(searchCondition);
			page.config(countResult, SearchCons.MAXDISP);
			return customerMapper.findByConditionLimit(searchCondition);
		}
	}

	public int createCustomer() throws SQLException, IOException {
		//新しい取引先を登録
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			Customer customer = new Customer(this.form);
			customer.setCreateUser(this.loginInfo.getStaffID());
			customer.setUpdateUser(this.loginInfo.getStaffID());
			return customerMapper.createCustomer(customer);

		}
	}

	public int updateCustomer() throws SQLException, IOException {
		//既存の取引先を更新
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			Customer customer = new Customer(this.form);
			customer.setUpdateUser(this.loginInfo.getStaffID());
			return customerMapper.updateCustomer(customer);

		}
	}

	public int deleteCustomer() throws SQLException, IOException {
		//取引先を削除
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			String customerCode = this.form.getCustomerCode();
			Date updateDate = this.form.getUpdateDate();
			return customerMapper.deleteCustomer(customerCode, updateDate);

		}
	}

	public boolean isValuesCorrect() {
		//入力情報の整合性チェック
		//整合性チェックメソッド
		//0.電話番号かURLは入力する
		//0-1.取引先管理権限をログインユーザが持つか
		//1.取引先が新規作成の時はあるか，
		//2.取引先がeditとdeleteの時は１レコードのみか
		//3.editとdeleteの時は楽観ロック
		//4.deleteFlagはcharで'0'か
		if (this.form.getPhoneNumber().isEmpty() && this.form.getUrl().isEmpty()) {
			this.message = "電話番号かURLはいずれか入力してください";
			return false;
		}

		if (!this.hasCustomerFunc()) {
			//取引先管理権限存在[ログインした社員 ID のロールに取引先管理権限が存在するか
			this.message = "ログインユーザは取引先管理権限を持ちません";
			return false;
		}

		if (this.form.isRegisterFlag() && this.hasCustomerCode()) {
			//新規作成時
			//レコードが存在しないか
			this.message = "同一の取引先コードがあります、異なるコードにしてください";
			return false;
		} else if ((this.form.isDeleteFlagForForm() || this.form.isEditFlag()) && !this.hasCustomerCode()) {
			this.message = "同一の取引先コードがありません";
			return false;
		} else {
			;
		}

		if ((this.form.isDeleteFlagForForm() || this.form.isEditFlag()) && !this.isSameUpdateDate()) {
			//入力した案件コードすでにあるか
			System.out.println(this.form.getUpdateDate());
			this.message = "他のユーザーによって更新されています、もう一度試してください";
			return false;
		}

		if ((this.form.isDeleteFlagForForm() || this.form.isEditFlag()) && !this.isNotDeleteFlag()) {
			this.message = "削除フラグが立っているため変更できません";
			return false;
		}
		return true;
	}

	//ここより下、入力整合性チェックのメソッド

	public boolean hasCustomerFunc() {
		//0-1.取引先管理権限をログインユーザが持つか
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int roleCode = this.loginInfo.getLoginStaff().getRoleCode();
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			int c = customerMapper.getFuncCount(roleCode, FunctionCons.F_CUSTOMER);
			if (c >= 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean hasCustomerCode() {
		//取引先があるか
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String customerCode = this.form.getCustomerCode();
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			int c = customerMapper.getCustomerCodeCount(customerCode);
			if (c == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isSameUpdateDate() {
		//楽観ロック
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			String customerCode = this.form.getCustomerCode();
			Date updateDate = this.form.getUpdateDate();
			int c = customerMapper.getUpdateDateCount(customerCode, updateDate);
			if (c == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isNotDeleteFlag() {
		//すでに登録された取引先コードであるか判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
			String customerCode = this.form.getCustomerCode();
			int c = customerMapper.getDeleteFlagCount(customerCode);
			if (c == 1) {
				return true;
			} else {
				return false;
			}

		}
	}
	//ここまで、入力整合性チェックのメソッド

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
