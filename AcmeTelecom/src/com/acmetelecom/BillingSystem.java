package com.acmetelecom;

import dataLayer.CustomerDatabase;
import dataLayer.ICustomerDatabase;
import dataLayer.ILocalCustomer;
import dataLayer.ILocalTariff;
import dataLayer.ITariffDatabase;
import dataLayer.TariffDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import presentation.HtmlPrinter;
import presentation.PrinterFactory;

// Class for Billing System
public class BillingSystem {

	// hashmap to store the startCall event affiliated with each caller,
	// facilitating easy retrieval
	public HashMap<String, CallEvent> callLog = new HashMap<String, CallEvent>();
	// list of all calls at the system so far
	public List<Call> calls = new ArrayList<Call>();

	//Remove singleton
//	public BillGenerator billGenerator = new BillGenerator(
//			new HtmlPrinter(System.out));

	public void callInitiated(Caller caller, Callee callee) {
		callLog.put(caller.getPhoneNumber(), new CallStart(caller, callee));
	}

	public void callCompleted(Caller caller, Callee callee) {
		// check if there's a callStart affiliated with that caller
		// needs lots of exceptions handling here
		if (callLog.containsKey(caller)
				&& (callLog.get(caller) instanceof CallStart)) {
			calls.add(new Call(callLog.get(caller), new CallEnd(caller, callee)));// generate
																					// the
																					// call
			callLog.remove(caller); // remove callStart from log
		}
	}

	public void createCustomerBills() {
		// Tudor modified this
		// Refactor to use only the dataLayer in order to connect to the
		// extrnal.jar library

		// List<Customer> customers =
		// CentralCustomerDatabase.getInstance().getCustomers();
		// for (Customer customer : customers) {
		// createBillFor(customer);
		// }
		ICustomerDatabase customerDatabase = new CustomerDatabase();
		List<ILocalCustomer> customers = customerDatabase.getCustomers();

		for (ILocalCustomer customer : customers) {

			List<LineItem> items = getCustomerBillItems(customer);
			createBillFor(customer, items);
		}

		callLog.clear();
		calls.clear();
	}

	public List<LineItem> getCustomerBillItems(ILocalCustomer customer) {
		List<LineItem> items = new ArrayList<LineItem>();

		for (Call call : calls) {
			if (call.caller().equals(customer.getPhoneNumber()))// fetch
																// customer's
																// calls
				items.add(new LineItem(call, computeCallCost(customer, call)));
		}

		return items;
	}

	public void createBillFor(ILocalCustomer customer, List<LineItem> items) {

		BigDecimal totalBill = calculateTotalBill(items);

		//Tudor: I think it's actually better if we create a new billGenerator object
		//each time we have to print a bill as we avoid bottlenecks (we can change this back)
		BillGenerator billGenerator = new BillGenerator();
		billGenerator.send(customer, items,
				MoneyFormatter.penceToPounds(totalBill),
				PrinterFactory.getPrinter(PrinterFactory.SYSTEM_OUT_PRINTER));
	}

	private BigDecimal calculateTotalBill(List<LineItem> items) {
		BigDecimal totalBill = new BigDecimal(0);
		for (LineItem item : items) {
			totalBill = totalBill.add(item.getCallCost());
		}
		return totalBill;
	}

	private BigDecimal computeCallCost(ILocalCustomer customer, Call call) {

		BigDecimal cost;

		ITariffDatabase tariffDatabase = new TariffDatabase();

		ILocalTariff tariff = tariffDatabase.tariffFor(customer);
		PeakCalculator peakCalculator = new PeakCalculator();
		int timeOnPeak = peakCalculator.onPeakTime(call.startTime(),
				call.durationSeconds());

		cost = new BigDecimal(0);
		cost = cost.add(new BigDecimal(timeOnPeak).multiply(tariff.peakRate()));// add
																				// on
																				// peak
																				// duration
																				// cost
		// add offpeak duration cost
		cost = cost.add(new BigDecimal(call.durationSeconds() - timeOnPeak)
				.multiply(tariff.offPeakRate()));
		cost = cost.setScale(0, RoundingMode.HALF_UP);

		return cost;
	}

	public static class LineItem {
		// added some getters and setters
		private Call call;

		public Call getCall() {
			return call;
		}

		public void setCall(Call call) {
			this.call = call;
		}

		public BigDecimal getCallCost() {
			return callCost;
		}

		public void setCallCost(BigDecimal callCost) {
			this.callCost = callCost;
		}

		private BigDecimal callCost;

		public LineItem(Call call, BigDecimal callCost) {
			this.call = call;
			this.callCost = callCost;
		}

		public String date() {
			return call.date();
		}

		public Callee callee() {
			return call.callee();
		}

		public String durationMinutes() {
			return "" + call.durationSeconds() / 60 + ":"
					+ String.format("%02d", call.durationSeconds() % 60);
		}

		public BigDecimal cost() {
			return callCost;
		}
	}
}