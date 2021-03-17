package beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cdi.DBMap;
import cdi.LoginInfo;
import cdi.StaffForm;
import constants.CodeCons;
import constants.FunctionCons;
import constants.StatusCons;
import constants.URLCons;
import entity.MFunction;
import entity.MFunctionForMenu;
import logic.LoginLogic;

@Named
@RequestScoped
public class LoginBean implements Serializable {

	@Inject
	private LoginLogic loginLogic;

	@Inject
	private LoginInfo loginInfo;

	@Inject
	private DBMap map;

	@Inject
	private StaffForm staffForm;


	public String login() {
		//ログイン時のメソッド
		//メニューやプルダウン表示用マップを作成
		if (this.loginLogic.canLogin()) {
			this.createMap();
			return URLCons.MENU;
		} else {
			return URLCons.LOGIN;
		}

	}

	public void createMap() {
		this.map.clear();
		List<MFunction> functionNum = this.loginLogic.getOrderedFunctions();
		//Mfunction (useFunction,codeKye)
		this.loginInfo.getFunctions().clear();
		for (MFunction f : functionNum) {
			int funcNum = f.getUseFunction();
			this.map.getFuncListForFilter().add(funcNum);

			if (funcNum != FunctionCons.F_LOGIN) {
				MFunctionForMenu func = new MFunctionForMenu();
				func.setName(f.getCodeName());
				func.setUrl(FunctionCons.FUNCTIONTOURL.get(funcNum));
				this.loginInfo.getFunctions().add(func);
			}
		}

		this.map.setAcc(this.loginLogic.getCode(CodeCons.CODEKEY2));
		this.map.getAcc().forEach((k, v) -> {
			this.map.getAccR().put(v, k);
		});
		this.map.setTrafficCode(this.loginLogic.getCode(CodeCons.CODEKEY5));
		this.map.getTrafficCode().forEach((k, v) -> {
			this.map.getTrafficCodeR().put(v, k);
		});

		Map<String, Integer> temp = this.loginLogic.getCode(CodeCons.CODEKEY1);
		for (Map.Entry<String, Integer> entry : temp.entrySet()) {
			String k = entry.getKey();
			Integer v = entry.getValue();
			this.map.getpStatus().put(k, v);
			this.map.getpStatusR().put(v, k);
			if (v == StatusCons.STATUS1_AP || v == StatusCons.STATUS2_HI) {
				this.map.getpStatusForRegister().put(k, v);
				this.map.getpStatusRForRegister().put(v, k);
			}
		}
		this.map.setCustomerCode(this.loginLogic.getCustomerCode());

		this.map.getCustomerCode().forEach((k, v) -> {
			this.map.getCustomerCodeR().put(v, k);
		});
		this.map.setSection(this.loginLogic.getSection());
		this.map.getSection().forEach((k, v) -> {
			this.map.getSectionR().put(v, k);
		});

	}

	public String logout() {
		//ログアウト
		this.loginInfo.clear();
		this.staffForm.clear();
		this.map.clear();
		return URLCons.LOGIN;

	}

	public String reset() {
		//リセット
		this.loginInfo.clear();
		return null;

	}

	public LoginLogic getLoginLogic() {
		return loginLogic;
	}

	public void setLoginLogic(LoginLogic loginLogic) {
		this.loginLogic = loginLogic;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public DBMap getMap() {
		return map;
	}

	public void setMap(DBMap map) {
		this.map = map;
	}

}
