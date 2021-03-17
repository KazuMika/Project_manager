package mapper;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import entity.Division;

public interface BelongMapper {

	@Delete("TRUNCATE table m_division")
	public void deleteAllStaff();

	@Insert("insert into m_division values (#{divisionCode},#{groupCode},#{divisionName},"
			+ " #{groupName},#{displayOrder},sysdate(),#{createUser},sysdate(), #{updateUser})")
	public void create(@NotNull Division division);

	@Select("select count(*) from m_division")
	public int countStaff();

}
