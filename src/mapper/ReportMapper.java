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
import entity.Code;
import entity.Project;
import entity.Report;
import entity.Serial;

public interface ReportMapper {

	@Select("select * from m_code where codeKey = #{codeKey} order by displayOrder ASC")
	public List<Code> getCode(@Param("codeKye") String codeKey, @Param("value") String value);

	@Insert("insert into t_project_report VALUES("
			+ "#{projectCode},#{receivedDate},#{accuracy},"
			+ "#{status},#{meetingDate},#{trafficCode},#{customer1},"
			+ "#{customer2},#{customer3},#{staff1},#{staff2},"
			+ "#{staff3},#{customerAddFlg},#{staffAddFlg},#{memo},"
			+ "sysdate(),#{createUser},sysdate(),#{updateUser})")
	public int createReport(Report report);

	@Update("update  t_project_report set "
			+ " receivedDate = #{receivedDate}, accuracy = #{accuracy},"
			+ "status = #{status}, meetingDate = #{meetingDate}, trafficCode = #{trafficCode}, customer1 = #{customer1},"
			+ "customer2 = #{customer2},customer3 = #{customer3},staff1 = #{staff1},staff2 =#{staff2},"
			+ "staff3 = #{staff3},customerAddFlg = #{customerAddFlg}, staffAddFlg = #{staffAddFlg},memo = #{memo},"
			+ "updateDate = sysdate(),updateUser = #{updateUser}   where projectCode = #{projectCode}")
	public int updateReport(Report report);

	@Update("update t_serial_number set "
			+ " currentCount= (currentCount+1), updateUser = #{staffID},"
			+ "updateDate = sysdate() where numberingCode = #{projectCode}")
	public int updateSerial(@Param("projectCode") String projectCode, @Param("staffID") String staffID);

	@Update("update t_project set projectStatus = #{projectStatus},"
			+ "receivedDate = #{receivedDate}, accuracy = #{accuracy},"
			+ "updateDate = sysdate(),updateUser = #{updateUser} "
			+ "where projectCode = #{projectCode}")
	public int updateProjectWithReport(Project project);

	@Select("SELECT * FROM t_project_report where projectCode like #{projectCoe} ORDER BY updateDate DESC LIMIT 1")
	public Report getMostRecentUpdateDateReport(String projectCode);

	@Select("select count(*) from t_project_report where updateDate > #{updateDate}")
	public int hasNewerReport(Date updateDate);

	@Select("select * from t_serial_number where numberingCode = #{projectCode}")
	public Serial getSerial(String projectCode);

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

	@Select("select count(*) from m_status_transition where currentStatus = #{currentStatus} and possibleStatus = #{possibleStatus}")
	public int getStatusTransitionNum(@Param("currentStatus") int currentStatus,
			@Param("possibleStatus") int possibleStatus);

	//検索条件にかかるprojectの人数を取得する
	public long getUserCountByCondition(SearchCondition searchCondition);

	//検索条件にかかるprojectの情報を取得する
	public List<Project> findByConditionLimit(SearchCondition searchCondition);

	@Select("select count(*) from t_project_report where substring(projectCode,1,6) = #{projectCode} ")
	public Long getReportFromProjectCode2(@NotNull String projectCode);

	@Select("select * from t_project_report where substring(projectCode,1,6) = #{projectCode} order by receivedDate desc"
			+ " limit #{firstRecordIndex},#{maxDisp} ")
	public List<Report> findReportByConditionLimit(@Param("projectCode") String projectCode,
			@Param("firstRecordIndex") int firstReportIndex,
			@Param("maxDisp") int maxDisp);

	@Select("select count(*) from t_project_report where projectCode = #{projectCode} and updateDate = #{updateDate}")
	public int getUpdateCount(@Param("projectCode") String projectCode, @Param("updateDate") Date updateDate);

	@Select("select count(*) from t_project_report where substring(projectCode,1,6) = #{projectCode} and receivedDate > #{receivedDate}")
	public int getNewReport(@Param("projectCode") String projectCode, @Param("receivedDate") Date receivedDate);

	@Delete("delete from t_project_report where projectCode = #{projectCode}")
	public int deleteReport(String projectCode);

	@Select(" select * from m_code where codeKey = #{codeKey} and codeValue in "
			+ "(select possibleStatus from m_status_transition where currentstatus = #{status}) order by displayOrder ASC")
	public List<Code> getConditionalStatus(@Param("codeKey") String codeKey, @Param("status") int currProjectStatus);

}
