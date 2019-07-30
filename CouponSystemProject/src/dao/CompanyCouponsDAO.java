package dao;

import java.sql.SQLException;
import java.util.Map;

import beans.Company;
import beans.Coupon;

public interface CompanyCouponsDAO {
	public void InsertCompanyCoupon(long companyID, long couponID) throws InterruptedException, SQLException;

	public Map<Long, Coupon> getAllCompanyCoupons(Company company) throws Exception;
}
