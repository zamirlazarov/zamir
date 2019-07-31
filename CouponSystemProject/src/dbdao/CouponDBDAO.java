package dbdao;

import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import beans.Coupon;
import beans.Customer;
import connections.ConnectionPool;
import dao.CouponDAO;
import main.CouponType;
import main.DataBase;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public long insertCoupon(Coupon coupon) throws Exception {
		Connection con = null;
		long id = 0;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "INSERT INTO Coupon (Title, startdate, endDate, Amount, Type, Message, price, image) values (?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, coupon.getStartDate());
			pstmt.setDate(3, coupon.getEndDate());
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().toString());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.executeUpdate();

			String query = "SELECT * FROM coupon where title = ?";
			 PreparedStatement pStatement = con.prepareStatement(query);
			 pStatement.setString(1, coupon.getTitle());
			 ResultSet resultSet = pStatement.executeQuery() ;
			if (resultSet.next()) {
				id = resultSet.getLong("id");
			}
			System.out.println("Coupon " + coupon.getTitle() + " was added succesfully");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
		return id;
	}

	@Override
	public void removeCoupon(long id) throws Exception {
		Connection con = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DELETE FROM coupon where id =?";
			PreparedStatement pstm1 = con.prepareStatement(sql);
			pstm1.setLong(1, id);
			pstm1.executeUpdate();
			pstm1.cancel();
			int count = pstm1.executeUpdate();
			if (count > 0) {
				System.out.println("Coupon with ID = " + id + " was deleted");
			} else {
				throw new Exception("Cannot remove coupon");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
	}

	@Override
	public void updateCoupon(Coupon coupon, long id) throws Exception {
		Connection con = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "UPDATE Coupon SET Title=?, startdate=?, endDate=?, Amount=?, Type=?, Message=?, price=?, image=? WHERE id=?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setDate(1, (Date) coupon.getEndDate());
			pstmt.setDouble(2, coupon.getPrice());
			pstmt.setLong(3, id);
			pstmt.executeUpdate();
			pstmt.cancel();
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("coupon with id=" + id + " was updated successfully");
			} else {
				throw new Exception("Cannot update coupon");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
	}

	@Override
	public Coupon getCoupon(long id) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Coupon coupon = new Coupon();

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "SELECT * from Coupon where id=" + id;
			PreparedStatement pstmt = con.prepareStatement(sql);
//*/
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				do {
					coupon.setCouponId(resultSet.getLong(1));
					coupon.setTitle(resultSet.getString(2));
					coupon.setStartDate(resultSet.getDate(3));
					coupon.setEndDate(resultSet.getDate(4));
					coupon.setAmount(resultSet.getInt(5));
					String type = resultSet.getString(6);
					switch (type) {
					case "FOOD":
						coupon.setType(CouponType.Food);
						break;
					case "RESTURANT":
						coupon.setType(CouponType.Resturant);
						break;
					case "ELECTRICITY":
						coupon.setType(CouponType.Electricity);
						break;
					case "HEALTH":
						coupon.setType(CouponType.Health);
						break;
					case "SPORTS":
						coupon.setType(CouponType.Sports);
						break;
					case "CAMPING":
						coupon.setType(CouponType.Camping);
						break;
					case "TRAVELLING":
						coupon.setType(CouponType.Travelling);
						break;
					}

					coupon.setMessage(resultSet.getString(7));
					coupon.setPrice(resultSet.getDouble(8));
					coupon.setImage(resultSet.getString(9));

				} while (resultSet.next());
			} else {
				throw new Exception("Cannot get this coupon");
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
		return coupon;

	}

	@Override
	public Map<Long, Coupon> getAllCoupons() throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Map<Long, Coupon> list = new HashMap<>();
		Coupon coupon = new Coupon();

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "SELECT * FROM coupon";
			PreparedStatement pstmt = con.prepareStatement(sql);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				do {
					// Coupon tmp = new Coupon();
					long id = resultSet.getLong("id");

					String Title = resultSet.getString("Title");

					System.out.println(resultSet.getDate("startdate"));
					Date startdate = resultSet.getDate("startdate");

					Date endDate = resultSet.getDate("endDate");

					Integer Amount = resultSet.getInt("Amount");

					String type = resultSet.getString("Type");

					CouponType couponType = CouponType.valueOf(type);
//					CouponType couponType = null;
//					switch (type) {
//					case "FOOD":
//						couponType = CouponType.Food;
//						// tmp.setType(CouponType.Food);
//						break;
//					case "RESTURANT":
//						couponType = CouponType.Resturant;
//
//						// tmp.setType(CouponType.Resturant);
//						break;
//					case "ELECTRICITY":
//						couponType = CouponType.Electricity;
//
//						// tmp.setType(CouponType.Electricity);
//						break;
//					case "HEALTH":
//						couponType = CouponType.Health;
//
//						// tmp.setType(CouponType.Health);
//						break;
//					case "SPORTS":
//						couponType = CouponType.Sports;
//
//						// tmp.setType(CouponType.Sports);
//						break;
//					case "CAMPING":
//						couponType = CouponType.Camping;
//
//						// tmp.setType(CouponType.Camping);
//						break;
//					case "TRAVELLING":
//						couponType = CouponType.Travelling;
//
//						// tmp.setType(CouponType.Travelling);
//						break;

					// }

					String Massage = resultSet.getString("Message");

					double Price = resultSet.getDouble("price");
					String Image = resultSet.getString("image");

					coupon = new Coupon(id, Title, startdate, endDate, Amount, couponType, Massage, Price, Image);
					list.put(id, coupon);

				} while (resultSet.next());
			} else {
				throw new Exception("Cannot get all coupons");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
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

	@Override
	public void getCouponByType(CouponType couponType) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "Select * from coupon where coupontype= ? ";
			PreparedStatement pStatement = con.prepareStatement(sql);

			pStatement.setString(1, couponType.name());
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				do {
					String type = resultSet.getString("coupontype");
					long id = resultSet.getLong("id");
					Date startDate = resultSet.getDate("startDate");
					Date endDate = resultSet.getDate("endDate");
					String title = resultSet.getString("title");
					int amount = resultSet.getInt("amount");
					Double price = resultSet.getDouble("prica");
					String image = resultSet.getString("image");
					String massage = resultSet.getString("massage");
					System.out.println(String.format(
							"id=%d , title=%s start date=%tD end date=%tD amount=%d type=%s message=%s price=%.2f image=%s",
							id, title, startDate, endDate, amount, type, massage, price, image));

				} while (resultSet.next());
			} else {
				throw new Exception("there is a problem with this coupon type");
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
	}

	@Override
	public boolean updateCouponAmount(int amount, long id) throws Exception {
		Connection con = null;
		boolean flag = false;
		try {

			try {
				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("error connection");
			}
			String sql = " UPDATE Coupon SET amount=amount-? where id=? and amount >0";

			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, amount);
			preparedStatement.setLong(2, id);
			int count = preparedStatement.executeUpdate();
			if (count > 0) {
				flag = true;
			} else {
				throw new Exception("illegal purchase");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}

		return flag;
	}

}
