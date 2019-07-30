package beans;

import java.util.Hashtable;
import java.util.Map;

public class Customer {
	private long id;
	private String CustomerName;
	private String Password;
	private Map<Long,Coupon> couponsCollection = new Hashtable<>();
	public Customer() {

	}

	public Customer(long id, String customerName, String password) {
		setId(id);
		setCustomerName(customerName);
		setPassword(password);
		setList(couponsCollection);
	}

	public Customer(String customerName, String password) {
		setId(id);
		setCustomerName(customerName);
		setPassword(password);
	}
	public Customer (String password) {
		setPassword(password);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Map<Long, Coupon> getList() {
		return couponsCollection;
	}

	public void setList(Map<Long, Coupon> list) {
		this.couponsCollection = list;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", CustomerName=" + CustomerName + ", Password=" + Password + "]";
	}

}
