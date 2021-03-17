package mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cdi.SearchConditionForCustomer;
import entity.Customer;

public interface CustomerMapper {

	//検索条件にかかるユーザーの人数を取得する
	public long getProjectCountByCondition(SearchConditionForCustomer searchCondition);

	//検索条件にかかるユーザーの情報を取得する
	public List<Customer> findByConditionLimit(SearchConditionForCustomer searchCondition);

	@Select("select count(*) from m_function where roleCode = #{roleCode} and useFunction = #{useFunction}")
	public int getFuncCount(@Param("roleCode") int roleCode, @Param("useFunction") int useFunction);

	@Select("select count(*) from m_customer where customerCode = #{customerCode}")
	public int getCustomerCodeCount(String customerCode);

	@Select("select count(*) from t_project where customerCode = #{customerCode}")
	public int reportsHaveThisCustomer(String customerCode);

	@Select("select count(*) from m_customer where customerCode = #{customerCode} and updateDate = #{updateDate}")
	public int getUpdateDateCount(@Param("customerCode") String customerCode, @Param("updateDate") Date updateDate);

	@Select("select count(*) from m_customer where customerCode = #{customerCode} and deleteFlag = 0")
	public int getDeleteFlagCount(String customerCode);

	@Update("update m_customer set deleteFlag = 1 where customerCode = #{customerCode} and updateDate = #{updateDate}")
	public int deleteCustomer(@Param("customerCode") String customerCode, @Param("updateDate") Date updateDate);

	@Insert("insert into m_customer values("
			+ "#{customerCode},#{customerName},#{address},"
			+ "#{phoneNumber}, #{url},0, sysdate(),#{createUser},"
			+ "sysdate(), #{updateUser})")
	public int createCustomer(Customer customer);

	@Update("update m_customer set customerName = #{customerName},"
			+ "address = #{address}, phoneNumber = #{phoneNumber},"
			+ "url = #{url},updateDate = sysdate(),updateUser = #{updateUser} where customerCode = #{customerCode}")
	public int updateCustomer(Customer customer);

}