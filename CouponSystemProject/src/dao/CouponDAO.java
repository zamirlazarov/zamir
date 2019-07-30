package dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import beans.Coupon;
import beans.Customer;
import main.CouponType;

public interface CouponDAO {


	
	long insertCoupon(Coupon coupon) throws Exception;

	
	public void removeCoupon(long id) throws Exception;

	
	void updateCoupon(Coupon coupon,  long id) throws Exception;

	
	Coupon getCoupon(long id) throws Exception;

	
	public Map<Long, Coupon> getAllCoupons() throws Exception;
	public void getCouponByType(CouponType couponType) throws Exception;
	public boolean updateCouponAmount(int amount, long id) throws Exception ;
	
}
