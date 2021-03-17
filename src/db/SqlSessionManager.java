package db;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@ApplicationScoped
public class SqlSessionManager {

	public static final String CONFIG_FILE = "mybatis-config.xml";

	private SqlSessionFactory sqlSessionFactory;

	/** 初期化処理 */
	@PostConstruct
	public void prepare() {
		try (InputStream inputStream = Resources.getResourceAsStream(CONFIG_FILE)) {
			this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
