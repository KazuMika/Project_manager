package mapper;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import entity.Code;
import entity.Customer;
import entity.Division;
import entity.MFunction;
import entity.Staff;

public interface LoginMapper {

	@Select("select * from m_staff where staffID = #{staffID} and password=#{password} ")
	public Staff getStaff(@Param("staffID") String staffID, @Param("password") String password);

	@Select("select count(*) from m_function where roleCode = #{roleCode} and useFunction=#{function}")
	public int getLoginFunction(@Param("roleCode") int roleCode, @Param("function") int function);

	@Select("select * from m_customer where deleteFlag != 1")
	public List<Customer> getCustomerCode();

	@Select("select * from m_division")
	public List<Division> getSection();

	@Select("select * from m_code where codeKey = #{codeKey} order by displayOrder ASC")
	public List<Code> getCode(@NotNull String codeKey);

	@Select("select * from m_function where roleCode = #{roleCode}")
	public List<MFunction> getFunctions(@NotNull int roleCode);

	@Select("select useFunction,codeName from m_function f inner join m_code c on f.useFunction = c.codeValue where codeKey = #{codeKey} and "
			+ "f.roleCode = #{roleCode} order by displayOrder")
	public List<MFunction> getOrderedFunctions(@Param("roleCode") int roleCode, @Param("codeKey") String codeKey);

	//	@Select("select * from m_function f inner join m_code c on f.useFunction = c.codeValue "
	//			+ "where codeKey=#{codeKey} and roleCode = #{roleCode} and useFunction != 1")

}
