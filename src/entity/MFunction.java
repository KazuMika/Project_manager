package entity;

import java.io.Serializable;

public class MFunction implements Serializable {
	private static final long serialVersionUID = 1L;
	private int useFunction;
	private String codeName;

	public int getUseFunction() {
		return useFunction;
	}

	public void setUseFunction(int useFunction) {
		this.useFunction = useFunction;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
