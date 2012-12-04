package com.acmetelecom.mockTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acmetelecom.BillGenerator;
import com.acmetelecom.BillingSystem;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.Customer;

@RunWith(JMock.class)
public class BillGeneratorTest {

	Mockery context = new JUnit4Mockery();

	BillGenerator billGenerator = new BillGenerator();

	//CentralCustomerDatabase centralCustomerDatabase = context.mock(CentralCustomerDatabase.class);

	Customer customer = new Customer("aa", "bb", "cc");
	List<BillingSystem.LineItem> calls = context.mock(List.class);
	private String totalBill = "100";

	@Test
	public void testBillsCreation() {
		context.checking(new Expectations() {{
        }});

        //billGenerator.send(customer, calls, totalBill);
	}

}
