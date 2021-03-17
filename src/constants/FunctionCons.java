package constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionCons {

	public FunctionCons() {

	}

	public static final Integer F_LOGIN = 1;
	public static final Integer F_USER_ALTER = 2;
	public static final Integer F_MATTER = 3;
	public static final Integer F_REPORT = 4;
	public static final Integer F_CUSTOMER = 5;
	public static final Integer F_BELONG = 6;

	public static final Map<Integer, String> FUNCTION = new LinkedHashMap<Integer, String>() {
		{
			//            put(1,"ログアウト");
			put(3, "案件管理");
			put(4, "案件報告");
			//            put(5,"取引先管理");
			put(6, "所属管理");
			put(2, "ユーザ情報変更");
		}
	};

	public static final Map<Integer, String> FUNCTIONTOURL = new LinkedHashMap<Integer, String>() {
		{
			put(1, URLCons.MENU);
			put(2, URLCons.STAFF_R);
			put(3, URLCons.MATTER_S);
			put(4, URLCons.REPORT_S);
			put(6, URLCons.BELONG_R);
			put(5, URLCons.CUSTOMER_S);
		}
	};
}
