package dataLayer;

import com.acmetelecom.customer.Customer;

public class LocalCustomer implements ILocalCustomer {

	private Customer customer = null;

	public LocalCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPhoneNumber() {
		//Exception handling needed
		if (customer != null)
			return customer.getPhoneNumber();

		return "";
	}

	@Override
	public String getFullName() {
		//Exception handling needed
		if (customer != null)
			return customer.getFullName();

		return "";
	}

	@Override
	public String getPricePlan() {
		//Exception handling needed
		if (customer != null)
			return customer.getPricePlan();

		return "";
	}

	@Override
	public Customer getCustomer() {
		//Exception handling needed
		return customer;
	}
}
