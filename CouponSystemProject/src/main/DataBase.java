package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.omg.CORBA.PUBLIC_MEMBER;

import connections.ConnectionPool;

public class DataBase {

	private static ConnectionPool connectionPool = ConnectionPool.getInstance();

	public static void Createcompanytable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}

			String sql = "create table Company (id integer not null primary key auto_increment, companyname text not null, password text not null, email text not null)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("company:" + sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}

	}

	public static void Createcoupon() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}

			String sql = "create table Coupon (id integer not null primary key auto_increment, Title text not null, startdate text not null, endDate text not null, Amount integer not null ,Type text not null, Message text not null, price double not null, image text not null)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("coupon:" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
	}

	public static void CreatecustomerTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}

			String sql = "create table Customer (id integer not null primary key auto_increment, customername text not null, password text not null)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("customer:" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
	}

	public static void CreateCustomerCouponsTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}

			String sql = "create table Customercoupons ( customerid integer not null, couponid integer not null,"
					+ "foreign key(customerid) references customer(id) on delete cascade, foreign key (couponid) references coupon (id) on delete cascade , primary key (customerid, couponid) );";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("customercoupons:" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
	}

	public static void CreateCompanyCouponsTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}

			String sql = "create table Companycoupons (companyid integer not null, couponid integer not null,"
					+ " foreign key (companyid) references company (id) on delete cascade ,"
					+ " foreign key (couponid) references coupon (id) on delete cascade , primary key (companyid , couponid) )";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("companycoupons:" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
		}
	}

	public static void dropCouponTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
				} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DROP TABLE COUPON";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("table deleted");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
			}
		}
	}

	public static void dropCompanyTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DROP TABLE COMPANY";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("company drop succses");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
			}
		}
	}

	public static void dropCustomerTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DROP TABLE CUSTOMER";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("table customer deleted");
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				System.out.println(e.getMessage());
			} finally {
				if (connectionPool != null) {
					connectionPool.returnConnection(con);
				}
			}
	}

	public static void dropCompanyCouponsTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DROP TABLE Companycoupons";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("table Companycoupons deleted");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
			}
		}
	}

	public static void dropCustomercouponsTable() throws SQLException, InterruptedException {
		Connection con = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DROP TABLE Customercoupons";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			System.out.println("table customercoupon deleted");
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
			}
		}
	}

	public static void CreateAllTabales() throws SQLException, InterruptedException {
		Createcoupon();
		Createcompanytable();
		CreatecustomerTable();
		CreateCustomerCouponsTable();
		CreateCompanyCouponsTable();
		
	}
	
	public static void DropAllTables() throws SQLException, InterruptedException {
		
		dropCompanyCouponsTable();
		dropCustomercouponsTable();
		dropCompanyTable();
		dropCouponTable();
		dropCustomerTable();
	}

}
