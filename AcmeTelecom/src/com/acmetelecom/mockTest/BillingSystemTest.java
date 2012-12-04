package com.acmetelecom.mockTest;

import static org.junit.Assert.*;

import org.junit.Test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acmetelecom.BillingSystem;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.Customer;

@RunWith(JMock.class)
public class BillingSystemTest {

	Mockery context = new JUnit4Mockery();

	BillingSystem billingSystem = new BillingSystem();

	Customer customer = new Customer("aa", "bb", "cc");

	@Test
	public void testBillsCreation() {
		context.checking(new Expectations() {{
        }});

        billingSystem.createBillFor(customer);
	}

}
