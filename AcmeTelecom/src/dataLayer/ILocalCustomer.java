package dataLayer;

import com.acmetelecom.customer.Customer;

public interface ILocalCustomer {
	public String getPhoneNumber();

	public String getFullName();

	public String getPricePlan();

	public Customer getCustomer();
}
