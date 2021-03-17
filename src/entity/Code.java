package entity;

import java.io.Serializable;

public class Code implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codeKey;
	private int codeValue;
	private String codeName;
	private int displayOrder;

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public int getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(int codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
