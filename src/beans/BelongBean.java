package beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import cdi.FileFolder;
import constants.URLCons;
import exception.MyException;
import logic.BelongLogic;

@Named
@RequestScoped
public class BelongBean implements Serializable {

	private String filePath = "resources/files";

	private boolean uploadErrorFlag;

	private String message;

	@Inject
	BelongLogic belongLogic;

	@Inject
	FileFolder fileFolder;

	private String errorMessage;

	public void setUploadFile(Part uploadFile) {
		this.fileFolder.setUploadFile(uploadFile);
	}

	public String upload() {
		//確認ボタンを押した時に動作
		//ファイルを保存
		if(Objects.isNull(this.fileFolder.getUploadFile())) {
			this.errorMessage = "ファイルが選択されていません。ファイルを選択してください";
			return null;
		}
		this.uploadErrorFlag = false;
		this.fileFolder.getDivisionList().clear();
		this.fileFolder.setDeleteStaffCount(0);
		this.fileFolder.setInsertStaffCount(0);
		try {
			this.belongLogic.upload(this.filePath);
		} catch (IOException ex) {

			this.uploadErrorFlag = true;
		} catch (MyException ex) {
			return null;
		} catch (Exception ex) {
			this.uploadErrorFlag = true;
		}
		if (this.uploadErrorFlag) {
			return URLCons.ERROR;
		} else {
			this.uploadErrorFlag = false;
			return URLCons.BELONG_C;
		}
	}

	public String replace() {
		//所属を入れ変え実行メソッド
		this.uploadErrorFlag = false;
		try {
			belongLogic.replace();
		} catch (IOException ex) {
			this.uploadErrorFlag = true;
		} catch (SQLException ex) {
			this.uploadErrorFlag = true;
		} catch (Exception ex) {
			this.uploadErrorFlag = true;
		}

		if (this.uploadErrorFlag) {
			return URLCons.ERROR;
		} else {
			this.uploadErrorFlag = false;
			this.fileFolder.clear();
			return URLCons.BELONG_F;
		}

	}

	public boolean isUploadErrorFlag() {
		return uploadErrorFlag;
	}

	public void setUploadErrorFlag(boolean uploadErrorFlag) {
		this.uploadErrorFlag = uploadErrorFlag;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BelongLogic getBelongLogic() {
		return belongLogic;
	}

	public void setBelongLogic(BelongLogic belongLogic) {
		this.belongLogic = belongLogic;
	}

	public FileFolder getFileFolder() {
		return fileFolder;
	}

	public void setFileFolder(FileFolder fileFolder) {
		this.fileFolder = fileFolder;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}