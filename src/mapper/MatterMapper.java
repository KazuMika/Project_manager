package mapper;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cdi.SearchCondition;
import entity.Project;
import entity.Serial;

public interface MatterMapper {

	@Insert("insert into t_project VALUES("
			+ "#{projectCode},#{projectName},#{receivedDate},#{accuracy},"
			+ "#{projectStatus},#{projectMemo},#{customerCode},#{customerPhoneNumber},"
			+ "#{customerMailAddress},#{sectionCode},#{sectionGroupCode},#{staffID},"
			+ "sysdate(),#{createUser},sysdate(),#{updateUser})")
	public int create(Project project);

	@Insert("insert into t_serial_number VALUES("
			+ "#{numberingCode},0,"
			+ "sysdate(),#{createUser},sysdate(),#{updateUser})")
	public int createSerial(Serial serial);

	@Update("update t_project set "
			+ " projectName = #{projectName},receivedDate = #{receivedDate},"
			+ " accuracy = #{accuracy},"
			+ " projectMemo = #{projectMemo}, customerCode = #{customerCode},"
			+ "customerPhoneNumber = #{customerPhoneNumber},"
			+ "customerMailAddress = #{customerMailAddress}, sectionCode = #{sectionCode},"
			+ "sectionGroupCode = #{sectionGroupCode}, staffID = #{staffID},"
			+ "updateDate = sysdate(), updateUser = #{updateUser} where projectCode = #{projectCode}")
	public int updateProject(Project project);

	@Select("select count(*) from t_project where projectCode = #{projectCode} and updateDate = #{updateDate}")
	public int getEdited(@Param("projectCode") String projectCode, @Param("updateDate") Date updateDate);

	@Select("select count(*) from m_function where roleCode = #{roleCode} and useFunction = #{useFunction}")
	public int getFuncCount(@Param("roleCode") int roleCode, @Param("useFunction") int useFunction);

	@Select("select count(*) from m_customer where customerCode = #{customerCode}")
	public int getProjectCodeCount(@NotNull String customerCode);

	@Select("select count(*) from t_project where projectCode = #{projectCode}")
	public int getProjectCodeInProjectTable(@NotNull String projectCode);

	@Select("select count(*) from m_code where codeKey= #{codeKey} and codeValue = #{codeValue}")
	public int getAccCount(@Param("codeKey") String codeKey, @Param("codeValue") int codeValue);

	@Select("select count(*) from m_customer where customerCode = #{customerCode}")
	public int getCustomerCount(@NotNull String customerCode);

	@Select("select count(*) from m_customer where customerCode = #{customerCode} and deleteFlag != 1")
	public int getDeletedCustomerCount(@NotNull String customerCode);

	@Select("select count(*) from m_division where divisionCode = #{divisionCode} and groupCode = #{groupCode}")
	public int getSectionCount(@Param("divisionCode") String devisionCode, @Param("groupCode") String groupCode);

	@Select("select staffName from m_staff where staffID = #{staffID}")
	public String getStaffName(String staffID);

	@Select("select count(*) from t_project_report where substring(projectCode,1,6) = #{projectCode} ")
	public int getReportFromProjectCode(@NotNull String projectCode);

	@Delete("delete from t_project where projectCode = #{projectCode}")
	public int deleteProject(String projectCode);

	//検索条件にかかるユーザーの人数を取得する
	public long getProjectCountByCondition(SearchCondition searchCondition);

	//検索条件にかかるユーザーの情報を取得する
	public List<Project> findByConditionLimit(SearchCondition searchCondition);

}