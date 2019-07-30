package beans;

import java.util.Hashtable;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class Company.
 */
public class Company {

	private long id;
	
	private String CompanyName;
	
	private String Password;
	
	private String Email;
	
	private Map<Long,Coupon> couponsCollection = new Hashtable<>();


	/**
	 * Instantiates a new company.
	 */
	public Company() {

	}

	/**
	 * Instantiates a new company.
	 *
	 * @param id the company id
	 * @param companyName the company name
	 * @param password the password
	 * @param email the email
	 */
	public Company(long id, String companyName, String password, String email) {
		setId(id);
		setCompanyName(companyName);
		setPassword(password);
		setEmail(email);
		setList(couponsCollection);
	}
	public Company(String companyName, String password, String email) throws Exception {
		setId(id);
		setCompanyName(companyName);
		setPassword(password);
		setEmail(email);
	}
	
	public Company(Long id ,String password, String email) throws Exception {
		setId(id);
		setPassword(password);
		setEmail(email);
	}
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the company name.
	 *
	 * @return the company name
	 */
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName the new company name
	 */
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		Email = email;
	}

	public Map<Long, Coupon> getList() {
		return couponsCollection;
	}

	public void setList(Map<Long, Coupon> list) {
		this.couponsCollection = list;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", CompanyName=" + CompanyName + ", Password=" + Password + ", Email=" + Email
				+ "]";
	}

}
