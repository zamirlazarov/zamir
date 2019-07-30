package facades;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import beans.Coupon;
import beans.Customer;
import dbdao.CouponDBDAO;
import dbdao.CustomerCouponsDBDAO;
import dbdao.CustomerDBDAO;
import main.CouponType;

public class CustomerFacade implements CouponClientFacade {

	
	private CouponDBDAO couponDBDAO = new CouponDBDAO();

	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();

	private CustomerCouponsDBDAO customerCouponsDBDAO = new CustomerCouponsDBDAO();

	Customer customer = new Customer();

	long customerID = 0;
	
	@Override
	public CouponClientFacade login(String userName, String password, ClientType type) throws Exception {
		if (customerDBDAO.login(userName, password)) {
			customer = customerDBDAO.getCustomerByName(userName);
			customerID = customer.getId();
			System.out.println("Welcome " + customer.getCustomerName() + " client type-" + type.toString());
			System.out.println("Loading your customer current coupons...");
			try {
				customer.setList(customerCouponsDBDAO.getAllCustomerCoupons(customer));
			} catch (Exception e) {
				throw new Exception("invalid user");
			}
			if (customer.getList().size() > 0) {
				Iterator<Entry<Long, Coupon>> iterator = customer.getList().entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<Long, Coupon> entry = iterator.next();
					Coupon coupon = (Coupon) entry.getValue();
					System.out.println(coupon);
				}

			}
			return this;
		}
		return null;
	}
	public void purchaseCouponById(long id) throws Exception {

		Coupon coupon = couponDBDAO.getCoupon(id);
		if (!customer.getList().containsValue(coupon)) {
			if (couponDBDAO.updateCouponAmount(1, id)) {
				customerCouponsDBDAO.insertCustomerCoupons(customer.getId(), id);
				System.out.println("Purchased coupon with ID = " + id);
			}
		} else {
			System.out.println("Cannot purchase coupon with ID = " + id + " again");
		}
	}
	
	public void getPurchaseHistory() {
		System.out.println("Searching for purchase history...");
		if (customer.getList().size() > 0 && customer.getList() != null) {
			System.out.println(customer.getList().values());
		}
	}
	public void getPurchaseHistoryByType(CouponType type) {
		System.out.println("Searching for purchased coupons from type " + type.toString() + "...");
		Iterator<Entry<Long, Coupon>> iterator = customer.getList().entrySet().iterator();
		int count = 0;
		while (iterator.hasNext()) {
			Entry<Long, Coupon> entry = iterator.next();
			Coupon coupon = (Coupon) entry.getValue();
			if (coupon.getType() == type) {
				System.out.println(coupon);
				count++;
			}
		}
		if (count == 0) {
			System.out.println("Coupons from type " + type.toString() + " not found");
		}
	}
	
	public void getPurchaseHistoryByPrice(double price) {
		System.out.println("Searching for purchased coupons with price  " + price + "...");
		Iterator<Entry<Long, Coupon>> iterator = customer.getList().entrySet().iterator();
		int count = 0;
		while (iterator.hasNext()) {
			Entry<Long, Coupon> entry = iterator.next();
			Coupon coupon = (Coupon) entry.getValue();
			if (coupon.getPrice() == price) {
				System.out.println(coupon);
				count++;
			}
		}
		if (count == 0) {
			System.out.println("Coupons with the price " + price + " not found");
		}
	}
}