package dao;

import java.util.Map;

import beans.Coupon;
import beans.Customer;

public interface CustomerCouponsDAO {
	public void insertCustomerCoupons(long customerID, long couponID) throws Exception;
	public Map<Long, Coupon> getAllCustomerCoupons(Customer customer) throws Exception;
}
