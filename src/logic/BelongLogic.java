package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;

import cdi.FileFolder;
import cdi.LoginInfo;
import constants.FileCons;
import db.SqlSessionManager;
import entity.Division;
import exception.MyException;
import mapper.BelongMapper;

@Transactional
@RequestScoped

/**
 * 所属登録機能のロジッククラス
 */
public class BelongLogic {

	/**
	 * SQLのSession情報を管理するCDI
	 */
	@Inject
	private SqlSessionManager sqlSessionManager;

	/**
	 * ログインしているstaff情報を保持するCDI
	 */
	@Inject
	private LoginInfo loginInfo;

	/**
	 * アップロードされたファイル，ファイル名，pathなどを管理
	 */
	@Inject
	private FileFolder fileFolder;

	/**
	 * 画面上に表示するメッセージ
	 */
	private String message;

	/**
	* 所属情報一件登録を行うメソッド
	* @param 所属登録
	*/
	public void createDivision(Division division) {
		String staffID = this.loginInfo.getLoginStaff().getStaffID();
		division.setCreateUser(staffID);
		division.setUpdateUser(staffID);
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			BelongMapper belongMapper = session.getMapper(BelongMapper.class);
			belongMapper.create(division);
		}

	}

	/**
	* 所属情報を全件削除
	*/
	public void deleteAllStaff() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			BelongMapper belongMapper = session.getMapper(BelongMapper.class);
			belongMapper.deleteAllStaff();
		}
	}

	public int countSfaff() {
		try (SqlSession session = sqlSessionManager.getSqlSessionFactory().openSession()) {
			BelongMapper belongMapper = session.getMapper(BelongMapper.class);
			return belongMapper.countStaff();
		}

	}

	public void upload(String filePath) throws IOException, MyException {
		//uploadボタン押下時動作
		//ファイルをアップロードし、内容をfileFolderのdivisionListにadd
		Part uploadFile = this.fileFolder.getUploadFile();

		if (uploadFile == null) {
			this.message = "ファイルが存在しません";
			throw new MyException();
		}
		String name = uploadFile.getSubmittedFileName();
		String ext = name.substring(name.lastIndexOf(".") + 1);

		if (!ext.equals(FileCons.CSV)) {
			this.message = "CSVではありません";
			throw new MyException();
		}
		InputStream is = uploadFile.getInputStream();

		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();

		String realPath = ctx.getRealPath(filePath);

		Date date = new Date();
		String fileName = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(date) + ".csv";
		int counter = 0;

		File file = new File(realPath, fileName);
		boolean flag = true;
		while (flag) {
			if (file.exists()) {
				date = new Date();
				fileName = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(date) + counter + ".csv";
				counter += 1;
				file = new File(realPath, fileName);
			} else {
				flag = false;
			}
		}

		Files.copy(is, file.toPath());

		System.out.println("保存先Path" + file.toPath());
		BufferedReader br = new BufferedReader(new InputStreamReader(uploadFile.getInputStream()));

		String line;
		while ((line = br.readLine()) != null) {
			String[] d = line.split(",");
			if (d.length != FileCons.UPLOAD_FILE_COLUMN_NUM) {
				this.message = "CSVの項目数が異なります";
				throw new MyException();
			}
			Division division = new Division(d[0], d[1], d[2], d[3], Integer.parseInt(d[4]));
			this.fileFolder.getDivisionList().add(division);
		}

	}

	/**
	* 実行ボタンを押下時に動作
	* 所属データの全件削除と新しいデータの挿入を実行
	*/
	@Transactional(rollbackOn = Exception.class)
	public void replace() throws IOException, SQLException {
		this.fileFolder.setDeleteStaffCount(this.countSfaff());
		this.deleteAllStaff();
		this.fileFolder.setInsertStaffCount(this.fileFolder.getDivisionList().size());
		for (Division d : this.fileFolder.getDivisionList()) {
			this.createDivision(d);
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

	public FileFolder getFileFolder() {
		return fileFolder;
	}

	public void setFileFolder(FileFolder fileFolder) {
		this.fileFolder = fileFolder;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
