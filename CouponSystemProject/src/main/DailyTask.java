package main;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import beans.Coupon;
import dbdao.CouponDBDAO;

public class DailyTask implements Runnable {
	/** The Coupon DBDAO. */
	private CouponDBDAO CouponDBDAO = new CouponDBDAO();

	/** The coupon map. */
	Map<Long, Coupon> couponMap = new Hashtable<>();

	/** The quit. */
	private boolean quit = false;

	/** The SLEE P time. */
	private final long SLEEP_time = 24 * 60 * 60 * 1000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (!quit) {
			try {

				couponMap = CouponDBDAO.getAllCoupons();

				Iterator<Entry<Long, Coupon>> iterator = couponMap.entrySet().iterator();
				int count = 0;
				while (iterator.hasNext()) {
					Entry<Long, Coupon> entry = iterator.next();
					Coupon coupon = (Coupon) entry.getValue();
					String endDate = coupon.getEndDate().toString();
					String format = DateEvents.sqlDateStringToDate(endDate);
					if (format.equals(DateEvents.formatCurrentTime())) {
						System.out.println(coupon);
						CouponDBDAO.removeCoupon(coupon.getCouponId());
						count++;
					}
				}
				if (count == 0) {
					System.out.println("There are no expired coupons");
				} else {
					System.out.println(count + " coupons deleted");
				}

				Thread.sleep(SLEEP_time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Stop task.
	 */
	public void stopTask() {
		quit = true;
	}

}
