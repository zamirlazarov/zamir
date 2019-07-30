package facades;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.util.jar.Attributes.Name;
import exception.*;
import org.apache.derby.client.am.ClientTypes;

import beans.Company;
import beans.Customer;
import dbdao.CompanyDBDAO;
import dbdao.CustomerDBDAO;

public class AdminFacade implements CouponClientFacade {

	private CompanyDBDAO companyDBDAO = new CompanyDBDAO();

	private CustomerDBDAO customerDBDAO = new CustomerDBDAO();

	private Map<Long, Company> companyList = new Hashtable<>();

	private Map<Long, Customer> customerList = new Hashtable<>();

	// private Customer customer;
	// private Company company;

	// public AdminFacade(Customer cust, Company comp) {
	// this.customer = cust;
	// this.company = comp;
	// }

	// public AdminFacade() {

	// }

	public void insertCustomer(String customerName, String password) throws Exception {
		customerList = customerDBDAO.getAllCustomers();
		Customer customer = new Customer(customerName, password);
		if (!customerList.containsValue(customer)) {
			customerDBDAO.insertCustomer(customer);
		} else {
			throw new Exception("customer exist");
		}

	}

	public void removeCustomer(long id) throws Exception {
		customerDBDAO.removeCustomer(id);
		customerList.remove(id);
	}

	public void updateCustomer(long id,String customerName, String password) throws Exception {
		Customer customerUpdate = new Customer(id, customerName, password);
		customerDBDAO.updateCustomer(customerUpdate, id);

	}
	

	public void getallCustomers() throws Exception {
		customerList = customerDBDAO.getAllCustomers();

		System.out.println("Searching for all the customers");
		Iterator<Entry<Long, Customer>> iterator = customerList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Customer> entry = iterator.next();
			Customer customer = (Customer) entry.getValue();
			System.out.println(customer);
		}
	}

	public void insertCompany(String companyName, String password, String email) throws Exception {
		System.err.println(3);
		companyList = companyDBDAO.getAllCompanies();
		System.out.println(4);
		System.out.println(companyList);
		System.out.println(companyList.size());
		Company company = new Company(companyName, password, email);
		if (companyList.size()==0 || !companyList.containsValue(company)) {
			companyDBDAO.insertCompany(company);
		} else {
			throw new Exception("already exist");
			}
		}
	
	public void removeCompany(long id) throws Exception {
		companyDBDAO.removeCompany(id);
		companyList.remove(id);
	}

	public void updateCompany(Long id,String companyName, String password ,String email) throws Exception {
		Company companyUpdate = new Company(id, companyName, password, email);
		companyDBDAO.updateCompany(companyUpdate, id);

	}
	
	
	// company.setCompanyName(newCompanyName);
	// company.setPassword(newPassword);
	// company.setEmail(newemail);
	// compDAO.updateCompany(company);

	

	public void getallCompanies() throws Exception {
		companyList = companyDBDAO.getAllCompanies();
		System.out.println("Searching for all the companies");
		Iterator<Entry<Long, Company>> iterator = companyList.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Long, Company> entry = iterator.next();
			Company company = (Company) entry.getValue();
			System.out.println(company);
		}
	}

	@Override
	public CouponClientFacade login(String userName, String password, ClientType type) throws Exception {
		if (userName.equals("admin") && password.equals("1234")) {
			System.out.println("Welcome admin client type-" + type.toString());
			return this;
		}
		return null; 
		
		}
	}

