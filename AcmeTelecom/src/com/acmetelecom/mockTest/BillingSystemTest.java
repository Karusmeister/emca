package com.acmetelecom.mockTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.runner.RunWith;


import com.acmetelecom.BillGenerator;
import com.acmetelecom.BillingSystem;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;
import com.acmetelecom.dataLayer.ICustomerDatabase;
import com.acmetelecom.dataLayer.ILocalCustomer;
import com.acmetelecom.dataLayer.ILocalTariff;
import com.acmetelecom.dataLayer.ITariffDatabase;
import com.acmetelecom.dataLayer.LocalCustomer;
import com.acmetelecom.dataLayer.LocalTariff;
import com.acmetelecom.model.Callee;
import com.acmetelecom.model.Caller;
import com.acmetelecom.presentation.Printer;


@RunWith(JMock.class)
public class BillingSystemTest {

	Mockery context = new JUnit4Mockery();

	ICustomerDatabase customerDatabase = context.mock(ICustomerDatabase.class);
	ITariffDatabase tariffDatabase = context.mock(ITariffDatabase.class);
	final ILocalTariff tariff = context.mock(ILocalTariff.class);

	BillingSystem billingSystem = new BillingSystem(customerDatabase,
			tariffDatabase);

	List<ILocalCustomer> customers1 = new ArrayList<ILocalCustomer>();

	private static void sleepSeconds(int n) throws InterruptedException {
		Thread.sleep(n * 1000);
	}

	@Before
	public void addACall() {
		Caller caller = new Caller("07445335887");
		Callee callee = new Callee("07886632214");
		billingSystem.callInitiated(caller, callee);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted(caller, callee);

		ILocalCustomer customer = context.mock(ILocalCustomer.class);
		customers1.add(customer);
	}

	@Test
	public void oneCustomerTest() {
		final List<ILocalCustomer> customers = new ArrayList<ILocalCustomer>(
				customers1);
		final ILocalCustomer customer = customers.get(0);

		final String phoneNo = "07445335887";
		final String fullName = "John";
		final String pricePlan = "Plan";
		final BigDecimal offPeakRate = new BigDecimal(100);
		final BigDecimal peakRate = new BigDecimal(200);

		context.checking(new Expectations() {
			{
				allowing(customerDatabase).getCustomers();
				will(returnValue(customers));
				atLeast(1).of(customer).getPhoneNumber();
				will(returnValue(phoneNo));
				atLeast(1).of(customer).getFullName();
				will(returnValue(fullName));
				atLeast(1).of(customer).getPricePlan();
				will(returnValue(pricePlan));
				oneOf(tariffDatabase).tariffFor(customer);
				will(returnValue(tariff));
				atLeast(1).of(tariff).offPeakRate();
				will(returnValue(offPeakRate));
				atLeast(1).of(tariff).peakRate();
				will(returnValue(peakRate));
			}
		});

		billingSystem.createCustomerBills();
	}

	private void createContextForMultipleCustomers() {
		Caller caller1 = new Caller("07786632214");
		Callee callee1 = new Callee("07456339998");
		Caller caller2 = new Caller("07663221445");
		Callee callee2 = new Callee("07122446988");
		Caller caller3 = new Caller("07411102300");
		Callee callee3 = new Callee("07002669881");

		billingSystem.callInitiated(caller1, callee1);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callInitiated(caller2, callee2);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted(caller1, callee1);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callInitiated(caller3, callee3);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted(caller3, callee3);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted(caller2, callee2);
	}

	@Test
	public void multipleCustomerTest() {
		final List<ILocalCustomer> customers = new ArrayList<ILocalCustomer>(customers1);

		final String phoneNo = "07445335887";
		final String fullName = "John";
		final String pricePlan = "Plan";
		final BigDecimal offPeakRate = new BigDecimal(100);
		final BigDecimal peakRate = new BigDecimal(200);

		context.checking(new Expectations() {
			{
				allowing(customerDatabase).getCustomers();
					will(returnValue(customers));
				for (ILocalCustomer customer : customers) {
					atLeast(1).of(customer).getPhoneNumber();
						will(returnValue(phoneNo));
					atLeast(1).of(customer).getFullName();
						will(returnValue(fullName));
					atLeast(1).of(customer).getPricePlan();
						will(returnValue(pricePlan));
					oneOf(tariffDatabase).tariffFor(customer);
						will(returnValue(tariff));
					atLeast(1).of(tariff).offPeakRate();
						will(returnValue(offPeakRate));
					atLeast(1).of(tariff).peakRate();
						will(returnValue(peakRate));
				}
			}
		});

		billingSystem.createCustomerBills();
	}

}
