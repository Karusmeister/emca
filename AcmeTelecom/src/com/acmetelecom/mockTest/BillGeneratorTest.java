package com.acmetelecom.mockTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acmetelecom.BillGenerator;
import com.acmetelecom.BillingSystem;
import com.acmetelecom.model.Caller;
import com.acmetelecom.model.Callee;
import com.acmetelecom.MoneyFormatter;

import com.acmetelecom.BillingSystem.LineItem;
import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.dataLayer.ICustomerDatabase;
import com.acmetelecom.dataLayer.ILocalCustomer;
import com.acmetelecom.dataLayer.LocalCustomer;
import com.acmetelecom.model.Call;
import com.acmetelecom.model.CallEnd;
import com.acmetelecom.model.CallEvent;
import com.acmetelecom.model.CallStart;
import com.acmetelecom.model.Callee;
import com.acmetelecom.presentation.Printer;
import com.acmetelecom.presentation.PrinterFactory;


@RunWith(JMock.class)
public class BillGeneratorTest {

	Mockery context = new JUnit4Mockery();

	Printer printer = context.mock(Printer.class);

	final LocalCustomer customer1 = new LocalCustomer(new Customer("John",
			"07445544412", "Plan"));
	final LocalCustomer customer2 = new LocalCustomer(new Customer("Mary",
			"07521100322", "Plan"));

	BillGenerator billGenerator = new BillGenerator();

	List<LineItem> items = new ArrayList<LineItem>();

	private static void sleepSeconds(int n) throws InterruptedException {
		Thread.sleep(n * 1000);
	}

	private LineItem createItem(Caller caller, Callee callee, BigDecimal cost){
		// Create a call
		final CallEvent start = new CallStart(caller, callee);
		try {
			sleepSeconds(2);
		} catch (InterruptedException e) {}

		final CallEvent end = new CallEnd(caller, callee);
		final Call call = new Call(start, end);

		// Create item list -> not really clear right now
		final LineItem item = new LineItem(call, cost);

		return item;
	}

	@Before
	public void CreateTestContext(){
		// Create a context for testing
		Caller caller = new Caller("07445544412");
		Callee callee = new Callee("07521100322");
		items.add(createItem(caller, callee, new BigDecimal(100)));
	}

	@Test
	public void oneCallTest() {
		final LineItem item = items.get(0);
		//Don't like that the BillingSystem is not cohesive enough
		//Just copy pasted code from BillingSystem
		BigDecimal bill = new BigDecimal(0);
		for (LineItem i : items) {
			bill = bill.add(i.getCallCost());
		}
		final String totalBill = MoneyFormatter.penceToPounds(bill);

		context.checking(new Expectations() {
			{
				oneOf(printer).printHeading(customer1.getFullName(),
						customer1.getPhoneNumber(), customer1.getPricePlan());
				oneOf(printer).printItem(item.date(), item.callee().getPhoneNumber(),
						item.durationMinutes(), MoneyFormatter.penceToPounds(item.cost()));
				oneOf(printer).printTotal(totalBill);
			}
		});

		billGenerator.send(customer1, items, totalBill, printer);
	}

	@Test
	public void multipleCallTest(){
		Caller caller = new Caller("07441223335");
		Callee callee = new Callee("0778998885");
		items.add(createItem(caller, callee, new BigDecimal(200)));

		BigDecimal bill = new BigDecimal(0);
		for (LineItem i : items) {
			bill = bill.add(i.getCallCost());
		}
		final String totalBill = MoneyFormatter.penceToPounds(bill);

		context.checking(new Expectations() {
			{
				oneOf(printer).printHeading(customer1.getFullName(),
						customer1.getPhoneNumber(), customer1.getPricePlan());
				for(LineItem item : items){
					oneOf(printer).printItem(item.date(), item.callee().getPhoneNumber(),
							item.durationMinutes(), MoneyFormatter.penceToPounds(item.cost()));
				}
				oneOf(printer).printTotal(totalBill);
			}
		});

		billGenerator.send(customer1, items, totalBill, printer);
	}

}
