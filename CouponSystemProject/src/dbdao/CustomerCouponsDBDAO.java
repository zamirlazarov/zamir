package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import beans.Coupon;
import beans.Customer;
import connections.ConnectionPool;
import dao.CustomerCouponsDAO;

public class CustomerCouponsDBDAO implements CustomerCouponsDAO {
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	private CouponDBDAO couponDBDAO = new CouponDBDAO();

	@Override
	public void insertCustomerCoupons(long customerID, long couponID) throws Exception {
		Connection con = null;

		try {

			con = connectionPool.getInstance().getConnection();

			String sql = "INSERT INTO Customercoupons (customerid ,couponid) values (?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, customerID);
			pstmt.setLong(2, couponID);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connectionPool != null)
				connectionPool.returnConnection(con);
			connectionPool = null;
		}

	}

	@Override
	public Map<Long, Coupon> getAllCustomerCoupons(Customer customer) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Map<Long, Coupon> list = new HashMap<>();
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
		String sql = "SELECT * FROM Customercoupons where id=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setLong(1, customer.getId());
		resultSet  = pstmt.executeQuery();
		if (resultSet.next()) {
			do {
				
				long id = resultSet.getLong(1);
				Coupon coupon =couponDBDAO.getCoupon(id);
				list.put(id, coupon);
			} while (resultSet.next());
		} else {
			throw new Exception("Cannot get all customercoupons");
		}
	} catch (Exception e) {
		e.printStackTrace();
	
} finally {
	if (connectionPool != null) {
		connectionPool.returnConnection(con);
		connectionPool = null;
		if (resultSet != null) {
			resultSet.close();
		}
	
	}

	
		}
		return list;
	}

}
