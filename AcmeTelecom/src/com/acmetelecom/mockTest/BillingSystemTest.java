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
		billingSystem.callInitiated("07445335887", "07886632214");
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted("07445335887", "07886632214");

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
		billingSystem.callInitiated("07786632214", "07456339998");
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callInitiated("07663221445", "07122446988");
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted("07786632214", "07456339998");
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callInitiated("07411102300", "07002669881");
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted("07411102300", "07002669881");
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {
		}
		billingSystem.callCompleted("07663221445", "07122446988");
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
