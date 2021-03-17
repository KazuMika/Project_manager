package logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;

import cdi.LoginInfo;
import constants.CodeCons;
import constants.RoleCons;
import db.SqlSessionManager;
import entity.Code;
import entity.Customer;
import entity.Division;
import entity.MFunction;
import entity.Staff;
import mapper.LoginMapper;

@Transactional
@RequestScoped
public class LoginLogic {

	@Inject
	private SqlSessionManager sqlSessionManager;
	@Inject
	private LoginInfo loginInfo;

	private String message;

	public List<MFunction> getFunctions() {
		//roleが持つ機能を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int roleCode = this.loginInfo.getLoginStaff().getRoleCode();
			LoginMapper menuMapper = session.getMapper(LoginMapper.class);
			List<MFunction> functions = menuMapper.getFunctions(roleCode);
			return functions;
		}

	}

	public List<MFunction> getOrderedFunctions() {
		//roleが持つ機能をdisplayOrderの値順で取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			int roleCode = this.loginInfo.getLoginStaff().getRoleCode();
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			List<MFunction> functions = loginMapper.getOrderedFunctions(roleCode, CodeCons.CODEKEY4);
			return functions;
		}

	}

	public Staff getLoginUser() {
		//passwordとIDでStaffを取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			String staffID = this.loginInfo.getStaffID();
			String password = this.loginInfo.getPassword();
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			Staff loginStaff = loginMapper.getStaff(staffID, password);
			return loginStaff;
		}

	}

	public int getLoginFunction(int roleCode) {
		//ログイン機能の個数を取得
		//0か1
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			int hasLoginFunction = loginMapper.getLoginFunction(roleCode, RoleCons.LOGIN_FUNCTION);
			return hasLoginFunction;
		}

	}

	public boolean hasUserFunction(int roleCode, int loginFunctionNum) {
		//ログイン機能を持つか判定
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			int functionCount = loginMapper.getLoginFunction(roleCode, loginFunctionNum);
			if (functionCount >= 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	public boolean canLogin() {
		//ログインできたか判定
		try {
			Staff loginStaff = this.getLoginUser();

			if (Objects.isNull(loginStaff)) {
				this.message = "存在しないユーザーかパスワードが異なります";
				return false;
			}

			int roleCode = loginStaff.getRoleCode();
			int hasLoginFunction = this.getLoginFunction(roleCode);
			if (hasLoginFunction < 1) {
				this.message = "このユーザーはログイン権限を持ちません";
				return false;
			} else {
				this.loginInfo.setLoginStaff(loginStaff);
				this.loginInfo.setLoggedIn(true);
				Date date = new Date();
				this.loginInfo.setLoginDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));

				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public Map<String, Integer> getCode(String codeKey) {
		//codeKeyを元にm_codeからcodeを取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			List<Code> codes = loginMapper.getCode(codeKey);
			Map<String, Integer> codesMap = new LinkedHashMap<>();
			for (Code c : codes) {
				codesMap.put(c.getCodeName(), c.getCodeValue());
			}
			return codesMap;

		}
	}

	public Map<String, String> getCustomerCode() {
		//取引先のマップを取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			List<Customer> customers = loginMapper.getCustomerCode();
			Map<String, String> customerCode = new LinkedHashMap<String, String>();
			for (Customer c : customers) {
				customerCode.put(c.getCustomerName(), c.getCustomerCode());

			}
			return customerCode;

		}
	}

	public Map<String, String> getSection() {
		//部署を取得
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			LoginMapper loginMapper = session.getMapper(LoginMapper.class);
			List<Division> divisionList = loginMapper.getSection();
			Map<String, String> sectionMap = new LinkedHashMap<String, String>();
			for (Division d : divisionList) {
				sectionMap.put(d.getDivisionName() + d.getGroupName(), d.getDivisionCode() + d.getGroupCode());
			}
			return sectionMap;

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

}
