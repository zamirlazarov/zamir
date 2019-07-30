package dbdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import connections.ConnectionPool;
import dao.CustomerDAO;
import main.DataBase;

public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public void insertCustomer(Customer customer) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new Exception("Error connection");
			}
			String sql = "INSERT INTO Customer (customername,password) values (?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getCustomerName());
			pstmt.setString(2, customer.getPassword());

			pstmt.executeUpdate();

			System.out.println("customer created" + customer.toString());
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
	public void removeCustomer(long id) throws Exception {
		Connection con = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "DELETE FROM Customer where id =?";
			PreparedStatement pstm1 = con.prepareStatement(sql);
			pstm1.setLong(1, id);
			pstm1.executeUpdate();
			pstm1.cancel();
			int count = pstm1.executeUpdate();
			if (count > 0) {
				System.out.println("Customer with ID = " + id + " was deleted");
			} else {
				throw new Exception("Cannot remove this customer");
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
	public void updateCustomer(Customer customer, long id) throws Exception {
		Connection con = null;
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = " UPDATE Customer SET customername=?,password=? WHERE id=?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, customer.getCustomerName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setLong(3, customer.getId());

			pstmt.executeUpdate();
			pstmt.cancel();
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("updated" + customer + " succesfully");
			} else {
				throw new Exception("Cannot update this customer");
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
	public Customer getCustomer(long id) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Customer customer = new Customer();
		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = " SELECT * from Customer where id=" + id;
			PreparedStatement pstmt = con.prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				customer.setId(resultSet.getLong(1));
				customer.setCustomerName(resultSet.getString(2));
				customer.setPassword(resultSet.getString(3));
				System.out.println(customer);
			} else {
				throw new Exception("Cannot get this customer");
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
		return customer;
	}
	
	@Override
	public Customer getCustomerByName(String name) throws Exception{
		Connection con = null;
		Customer customer = new Customer();
		ResultSet resultSet = null;
		try {
			try {
			
				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("cannot get connection");
			}
		String sql = "SELECT * FROM customer where customername = ?";
	 PreparedStatement pStatement = con.prepareStatement(sql);
	 pStatement.setString(1, name);
	 resultSet = pStatement.executeQuery() ;
		if (resultSet .next()) {
			customer.setId(resultSet.getLong("id"));
			customer.setCustomerName(resultSet.getString("customername"));
			
			customer.setPassword(resultSet.getString("password"));
			
		
	} else {
		throw new Exception("customer not found");
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
	return customer;	
		
	}

	

	@Override
	public Map<Long, Customer> getAllCustomers() throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
		Map<Long, Customer> list = new HashMap<>();
		Customer customer = new Customer();

		try {
			try {

				con = connectionPool.getInstance().getConnection();
			} catch (Exception e) {
				throw new Exception("Error connection");
			}
			String sql = "SELECT * FROM customer";
			PreparedStatement pstmt = con.prepareStatement(sql);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				do {
					long id = resultSet.getLong(1);
					String customerName = resultSet.getString(2);
					String password = resultSet.getString(3);

					customer = new Customer(id, customerName, password);
					list.put(id, customer);

					list.put(id, customer);

				} while (resultSet.next());
			} else {
				throw new Exception("Cannot get all coupons");
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connectionPool != null) {
				connectionPool.returnConnection(con);
				
				if (resultSet != null) {
					resultSet.close();
				}

			}

		}
		return list;

	}

	@Override
	public boolean login(String userName, String password) throws Exception {
		Connection con = null;
		ResultSet resultSet = null;
				boolean flag = false;

				try {
					try {

						con = connectionPool.getInstance().getConnection();
					} catch (Exception e) {
						throw new Exception("Error connection");
					}

				String sql = "SELECT * FROM customer where customername= ?";

				PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, userName);
					resultSet = pstmt.executeQuery();
					if (resultSet.next()) {
						do {

							String companypassword = resultSet.getString("password");
							if (companypassword.equals(password)) {
								flag = true;

							}
						} while (resultSet.next());
					} else {
						throw new Exception("invalid user");
					}
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					if (connectionPool != null) {
						connectionPool.returnConnection(con);
					}
						if (resultSet != null) {
							resultSet.close();
						}

				}
				
				return flag;
				
	}
}
