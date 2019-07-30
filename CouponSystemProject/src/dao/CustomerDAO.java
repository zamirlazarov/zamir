package dao;

import java.util.Map;
import java.util.Set;

import beans.Customer;

public interface CustomerDAO {

	public void insertCustomer(Customer customer) throws Exception;

	public void removeCustomer(long id) throws Exception;

	public void updateCustomer(Customer customer,long id) throws Exception;

	Customer getCustomer(long id) throws Exception;
	public Customer getCustomerByName(String name) throws Exception;
	public Map<Long, Customer> getAllCustomers() throws Exception ;

	boolean login(String userName, String password) throws Exception;

}
