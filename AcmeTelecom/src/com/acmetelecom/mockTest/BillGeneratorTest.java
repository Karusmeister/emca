package com.acmetelecom.mockTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.acmetelecom.Call;
import com.acmetelecom.CallEnd;
import com.acmetelecom.CallEvent;
import com.acmetelecom.CallStart;
import com.acmetelecom.MoneyFormatter;
import presentation.Printer;
import com.acmetelecom.BillingSystem.LineItem;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.Customer;

import dataLayer.ICustomerDatabase;
import dataLayer.ILocalCustomer;
import dataLayer.LocalCustomer;

@RunWith(JMock.class)
public class BillGeneratorTest {

	Mockery context = new JUnit4Mockery();

	BillGenerator billGenerator = new BillGenerator();
	Printer printer = context.mock(Printer.class);

	final LocalCustomer customer1 = new LocalCustomer(new Customer("John", "07445544412", "Plan"));
	final LocalCustomer customer2 = new LocalCustomer(new Customer("Mary", "07521100322", "Plan"));

	@Test
	public void testBillsCreation() {
		//Create a context for testing
		final String totalBill = MoneyFormatter.penceToPounds(new BigDecimal(100));
		List<LineItem> items = new ArrayList<LineItem>();
		//Create a call
		Call call = new Call(new CallStart("07445544412","07521100322"), new CallEnd("07445544412","07521100322"));
		//Create item list -> not really clear right now
		items.add(new LineItem(call, new BigDecimal(100)));

		context.checking(new Expectations() {{
			//oneOf(printer).printTotal(totalBill);
        }});

        billGenerator.send(customer1, items, totalBill);
	}

}
