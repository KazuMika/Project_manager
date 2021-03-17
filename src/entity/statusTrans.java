package entity;

import java.io.Serializable;

public class statusTrans implements Serializable {
	private static final long serialVersionUID = 1L;
	private int currentStatus;
	private int possibleStatus;

	public int getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(int currentStatus) {
		this.currentStatus = currentStatus;
	}

	public int getPossibleStatus() {
		return possibleStatus;
	}

	public void setPossibleStatus(int possibleStatus) {
		this.possibleStatus = possibleStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
