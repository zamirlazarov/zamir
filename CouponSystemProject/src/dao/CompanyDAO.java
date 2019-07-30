package dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import beans.Company;
import beans.Coupon;

public interface CompanyDAO {

	public void insertCompany(Company company) throws Exception;

	public void removeCompany(long id) throws Exception;

	public void updateCompany(Company company, long id) throws Exception;

	
	public Company getCompanyByName(String name) throws Exception;

	public Company getCompany(long id) throws Exception;

	public Map<Long, Company> getAllCompanies() throws Exception;

	public boolean login(String userName, String password) throws Exception;

}
