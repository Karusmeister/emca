package com.acmetelecom;

import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.Customer;
import com.acmetelecom.customer.Tariff;

import dataLayer.CustomerDatabase;
import dataLayer.ICustomerDatabase;
import dataLayer.ILocalCustomer;
import dataLayer.ILocalTariff;
import dataLayer.ITariffDatabase;
import dataLayer.LocalCustomer;
import dataLayer.TariffDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BillingSystem {

    public List<CallEvent> callLog = new ArrayList<CallEvent>();

    public void callInitiated(String caller, String callee) {
        callLog.add(new CallStart(caller, callee));
    }

    public void callCompleted(String caller, String callee) {
        callLog.add(new CallEnd(caller, callee));
    }

    public void createCustomerBills() {
    	//Tudor modified this
    	//Refactor to use only the dataLayer in order to connect to the extrnal.jar library

//        List<Customer> customers = CentralCustomerDatabase.getInstance().getCustomers();
//        for (Customer customer : customers) {
//            createBillFor(customer);
//        }
    	ICustomerDatabase customerDatabase = new CustomerDatabase();
    	List<ILocalCustomer> customers = customerDatabase.getCustomers();

    	for(ILocalCustomer customer : customers){
    		createBillFor(customer);
    	}

        callLog.clear();
    }

    public void createBillFor(ILocalCustomer customer) {

        List<Call> calls = generateCustomerCalls(generateCustomerEvents(customer));
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {
            items.add(new LineItem(call, computeCallCost(customer, call)));
        }

        BigDecimal totalBill = calculateTotalBill(items);

        new BillGenerator().send(customer, items, MoneyFormatter.penceToPounds(totalBill));
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

		//Tudor modified here
		//Tariff tariff = CentralTariffDatabase.getInstance().tarriffFor(customer);

		ITariffDatabase tariffDatabase = new TariffDatabase();
		ILocalTariff tariff = tariffDatabase.tariffFor(customer);
		DaytimePeakPeriod peakPeriod = new DaytimePeakPeriod();
		if (peakPeriod.offPeak(call.startTime()) && peakPeriod.offPeak(call.endTime()) && call.durationSeconds() < 12 * 60 * 60) {
		    cost = new BigDecimal(call.durationSeconds()).multiply(tariff.offPeakRate());
		} else {
		    cost = new BigDecimal(call.durationSeconds()).multiply(tariff.peakRate());
		}

		cost = cost.setScale(0, RoundingMode.HALF_UP);
		return cost;
	}

	private List<Call> generateCustomerCalls(List<CallEvent> customerEvents) {
		List<Call> calls = new ArrayList<Call>();

        CallEvent start = null;
        for (CallEvent event : customerEvents) {
            if (event instanceof CallStart) {
                start = event;
            }
            if (event instanceof CallEnd && start != null) {
                calls.add(new Call(start, event));
                start = null;
            }
        }
		return calls;
	}

	private List<CallEvent> generateCustomerEvents(ILocalCustomer customer) {
		List<CallEvent> customerEvents = new ArrayList<CallEvent>();
        for (CallEvent callEvent : callLog) {
            if (callEvent.getCaller().equals(customer.getPhoneNumber())) {
                customerEvents.add(callEvent);
            }
        }
		return customerEvents;
	}

    public static class LineItem {
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

        public String callee() {
            return call.callee();
        }

        public String durationMinutes() {
            return "" + call.durationSeconds() / 60 + ":" + String.format("%02d", call.durationSeconds() % 60);
        }

        public BigDecimal cost() {
            return callCost;
        }
    }
}
