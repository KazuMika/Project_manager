package mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import entity.Staff;

public interface StaffMapper {

	@Update("update  m_staff set "
			+ "staffName = #{staffName}, password = #{password},"
			+ "divisionCode = #{divisionCode}, groupCode = #{groupCode}, roleCode = #{roleCode},"
			+ "updateDate = sysdate(),updateUser = #{updateUser} where staffId = #{staffID}")
	public int updateStaff(Staff staff);

	@Delete("delete from m_staff where staffID = #{staffID}")
	public int deleteStaff(String staffID);

	@Select("select count(*) from m_function where roleCode = #{roleCode} and useFunction = #{useFunction}")
	public int hasEditAuth(@Param("roleCode") int roleCode, @Param("useFunction") int useFunction);

	@Select("select count(*) from m_staff where staffID = #{staffID} and updateDate = #{updateDate}")
	public int isSameUpdateDate(@Param("staffID") String staffID, @Param("updateDate") Date updateDate);

}
