package facades;


import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import beans.Company;
import beans.Coupon;
import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyCouponsDBDAO;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import main.CouponType;
import main.DateEvents;


public class CompanyFacade implements CouponClientFacade {

	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();

	private CouponDBDAO couponDBDAO = new CouponDBDAO();

	private CompanyCouponsDBDAO companyCouponsDBDAO = new CompanyCouponsDBDAO();

	private Company company;
	private long companyID = 0;

	@Override
	public CouponClientFacade login(String userName, String password, ClientType type) throws Exception {
		if (companyDBDAO.login(userName, password)) {
			company = companyDBDAO.getCompanyByName(userName);
			companyID = company.getId();
			System.out.println("Welcome " + company.getCompanyName() + " client type-" + type.companyfacade.toString());
			System.out.println("Loading your company current coupons...");
			try {
				company.setList(companyCouponsDBDAO.getAllCompanyCoupons(company));
			} catch (Exception e) {
				throw new Exception("doesnt exist");
			}

			if (company.getList().size() > 0) {
				Iterator<Entry<Long, Coupon>> iterator = company.getList().entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<Long, Coupon> entry = iterator.next();
					Coupon value = (Coupon) entry.getValue();
					System.out.println(value);
				}
			}

			return this;
		}
		return null;
	}

	public void insertCoupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) throws Exception {

		Coupon coupon = new Coupon(title, startDate, endDate, amount, type, message, price, image);

		if (!company.getList().containsValue(coupon)) {
			long id = couponDBDAO.insertCoupon(coupon);

			companyCouponsDBDAO.InsertCompanyCoupon(company.getId(), id);
			Coupon insertedCoupon = new Coupon(id, title, startDate, endDate, amount, type, message, price, image);
			company.getList().put(id, insertedCoupon);
		} else {
			throw new Exception("this coupon already exist");
		}
	}
	
	public void deleteCoupon(long id) throws Exception {
		couponDBDAO.removeCoupon(id);
		company.getList().remove(id);
	}
	
	public void updateCoupon(Date endDate, double price, long id) throws Exception {
		Coupon couponUpdate = new Coupon(endDate, price);
		couponDBDAO.updateCoupon(couponUpdate, id);
		Coupon coupon = company.getList().get(id);
		coupon.setPrice(price);
		coupon.setEndDate(endDate);
	}
	
	public void getCompany() throws Exception {
		if (companyID != 0) {
			companyDBDAO.getCompany(companyID);
		}
		}
	
	public void getAllCompanysCoupons() throws Exception {
		if (company.getList() != null) {
			System.out.println("Loading all your Company coupons...");
			Iterator<Entry<Long, Coupon>> iterator = company.getList().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Long, Coupon> entry = iterator.next();
				Coupon coupon = (Coupon) entry.getValue();
				System.out.println(coupon);
			}
		}
	}
	
	public void getCouponsByType(CouponType type) {
		System.out.println("Searching for coupons from type " + type.toString() + "...");
		Iterator<Entry<Long, Coupon>> iterator = company.getList().entrySet().iterator();
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
	public void getCouponsByPrice(double price) {
		System.out.println("Searching for coupons with the price " + price + "...");
		Iterator<Entry<Long, Coupon>> iterator = company.getList().entrySet().iterator();
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
	
	public void getCouponsByEndDate(int day, int month, int year) throws Exception {
		System.out.println("Searching for coupons with end Date of " + day + "/" + month + "/" + year + "...");
		Iterator<Entry<Long, Coupon>> iterator = company.getList().entrySet().iterator();
		int count = 0;
		while (iterator.hasNext()) {
			Entry<Long, Coupon> entry = iterator.next();
			Coupon coupon = (Coupon) entry.getValue();
			String endDate = coupon.getEndDate().toString();
			String format = DateEvents.sqlDateStringToDate(endDate);
			if (format.equals(DateEvents.intToDate(day, month, year))) {
				System.out.println(coupon);
				count++;
			}
		}
		if (count == 0) {
			System.out.println("No coupons with end Date of " + day + "/" + month + "/" + year + " found");
		}
	}


}