package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import beans.Company;
import beans.Coupon;
import connections.ConnectionPool;
import dao.CompanyCouponsDAO;

public class CompanyCouponsDBDAO implements CompanyCouponsDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();
	private CouponDBDAO couponDBDAO = new CouponDBDAO();

	@Override
	public void InsertCompanyCoupon(long companyID, long couponID) throws InterruptedException, SQLException {
		Connection con = null;

		try {

			con = connectionPool.getInstance().getConnection();

			String sql = "INSERT INTO Companycoupons (companyid ,couponid) values (?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, companyID);
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
	public Map<Long, Coupon> getAllCompanyCoupons(Company company) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Map<Long, Coupon> list = new HashMap<>();
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
		String sql = "SELECT * FROM Companycoupons where companyid=? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setLong(1, company.getId());
		resultSet  = pstmt.executeQuery();
		if (resultSet.next()) {
			do {
				
				long id = resultSet.getLong(1);
				Coupon coupon =couponDBDAO.getCoupon(id);
				list.put(id, coupon);
			} while (resultSet.next());
		} else {
			throw new Exception("Cannot get all companycoupons");
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