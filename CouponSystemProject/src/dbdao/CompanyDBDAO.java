package dbdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.derby.database.Database;

import beans.Company;
import beans.Coupon;
import connections.ConnectionPool;
import dao.CompanyDAO;
import main.CouponType;
import main.DataBase;

public class CompanyDBDAO implements CompanyDAO {
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public void insertCompany(Company company) throws Exception {
		Connection con = null;
		try {
				
				con = connectionPool.getInstance().getConnection();
				
				String sql = "INSERT INTO Company (companyname,password,email) values (?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql) ;
					pstmt.setString(1, company.getCompanyName());
					pstmt.setString(2, company.getPassword());
					pstmt.setString(3, company.getEmail());
					pstmt.executeUpdate();

					System.out.println("company created" + company.toString());
			} 
		catch (Exception e) {
			System.err.println("this e");
				System.out.println(e.getMessage());
				throw new Exception("Error connection");
		}
		finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
				
			}
			
			
			
		
		} 
	

	}

	@Override
	public void removeCompany(long id) throws Exception {
		Connection con = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
		String sql = "DELETE FROM Company where id =?";
		PreparedStatement pstm1 = con.prepareStatement(sql);
			// con.setAutoCommit(false);
			pstm1.setLong(1, id);
			pstm1.executeUpdate();
			pstm1.cancel();
			int count = pstm1.executeUpdate();
			if (count > 0) {
				System.out.println("company with ID = " + id + " was deleted");
			} else {
				
				throw new Exception("Cannot remove company");
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
	public void updateCompany(Company company, long id) throws Exception {
		Connection con = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
		String sql = " UPDATE Company SET companyname=?,password=?,email=? WHERE id=?";
		

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, company.getCompanyName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			pstmt.setLong(4, company.getId());

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
	public Company getCompanyByName(String name) throws Exception{
		
		Connection con = null;
		Company company = new Company();
		ResultSet resultSet = null;
		try {
			try {
			
				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("cannot get connection");
			}
		String sql = "SELECT * FROM company where comapnyname = ?";
	 PreparedStatement pStatement = con.prepareStatement(sql);
	 pStatement.setString(1, name);
	 resultSet = pStatement.executeQuery() ;
		if (resultSet .next()) {
			company.setId(resultSet.getLong("id"));
			company.setCompanyName(resultSet.getString("companyname"));
			company.setEmail(resultSet.getString("email"));
			company.setPassword(resultSet.getString("password"));
			
		
	} else {
		throw new Exception("company not found");
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	if (connectionPool != null) {
		connectionPool.returnConnection(con);
		connectionPool=null;
	}
	if (resultSet != null) {
		resultSet.close();
	}
}
	return company;	
		
	}

	@Override
	public Company getCompany(long id) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Company company = new Company();
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = " SELECT * from Company where id=" + id;
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			resultSet  = pstmt.executeQuery(sql);
			if (resultSet.next()) {
			resultSet.next();
			company.setId(resultSet.getLong(1));
			company.setCompanyName(resultSet.getString(2));
			company.setPassword(resultSet.getString(3));
			company.setEmail(resultSet.getString(4));
			System.out.println(company);
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
		return company;
	}

	@Override
	public Map<Long, Company> getAllCompanies() throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Map<Long, Company> listCompany = new Hashtable<>();
		Company company = new Company();
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception(" cannot get connection");
			}
			String sql = "SELECT * FROM company";
			PreparedStatement pStatement = con.prepareStatement(sql);
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				do {
					long id = resultSet.getLong(1);
					String comapnyName = resultSet.getString(2);
					String password = resultSet.getString(3);
					String email = resultSet.getString(4);

					company = new Company(id, comapnyName, password, email);
					listCompany.put(id, company);
				} while (resultSet.next());
			} else {
				throw new Exception("cannot get all companies");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				connectionPool = null;
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return listCompany;

	}

	@Override
	public boolean login(String userName, String password) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		boolean flag = false;
		con = connectionPool.getInstance().getConnection();

		String sql = "SELECT * FROM company where companyname= ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userName);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				do {

					String companypassword = resultSet.getString("password");
					if (companypassword.equals(password)) {
						flag = true;

					}
				} while (resultSet.next());
			}

		} catch (Exception e) {
			System.out.println(e);

		} finally {
			connectionPool.returnConnection(con);
		}

		return flag;
	}

}
