package com.acmetelecom.dataLayer;

import com.acmetelecom.customer.Customer;

public class LocalCustomer implements ILocalCustomer {
	private Customer customer = null;

	public LocalCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPhoneNumber() {
		String customerNr = "";
		try {
			if (customer != null)
				customerNr = customer.getPhoneNumber();
		} catch (Exception e) {
		}
		return customerNr;
	}

	@Override
	public String getFullName() {
		String fullName = "";
		try {
			if (customer != null)
				fullName = customer.getFullName();
		} catch (Exception e) {

		}
		return fullName;
	}

	@Override
	public String getPricePlan() {
		String pricePlan = "";
		try {
			if (customer != null)
				pricePlan = customer.getPricePlan();
		} catch (Exception e) {

		}
		return pricePlan;
	}

	@Override
	public Customer getCustomer() {
		try {
			return customer;
		} catch (Exception e) {
		}
		return customer;
	}
}