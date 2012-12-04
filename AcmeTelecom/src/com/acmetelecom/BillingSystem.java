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

	//hashmap to store the startCall event affiliated with each caller, facilitating easy retrieval
    public HashMap<String, CallEvent> callLog = new HashMap<String, CallEvent>();
    //list of all calls at the system so far
    public List<Call> calls = new ArrayList<Call>();

    public void callInitiated(String caller, String callee) {
        callLog.put(caller, new CallStart(caller, callee));
    }

    public void callCompleted(String caller, String callee) {
    	//check if there's a callStart affiliated with that caller
    	//needs lots of exceptions handling here
    	if(callLog.containsKey(caller)&&(callLog.get(caller) instanceof CallStart))
    	{
    		calls.add(new Call(callLog.get(caller), new CallEnd(caller, callee)));//generate the call
    		callLog.remove(caller); //remove callStart from log
    	}
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
        calls.clear();
    }
    public void createBillFor(ILocalCustomer customer) {
        List<LineItem> items = new ArrayList<LineItem>();

        for (Call call : calls) {
        	if(call.caller().equals(customer.getPhoneNumber()))//fetch customer's calls
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
				ITariffDatabase tariffDatabase = new TariffDatabase();
		ILocalTariff tariff = tariffDatabase.tariffFor(customer);
		PeakCalculator peakCalculator = new PeakCalculator();
	    int timeOnPeak = peakCalculator.onPeakTime(call.startTime(), call.durationSeconds());

	    cost = new BigDecimal(0);
	    cost = cost.add(new BigDecimal(timeOnPeak).multiply(tariff.peakRate()));//add on peak duration cost
	    //add offpeak duration cost
	    cost = cost.add(new BigDecimal(call.durationSeconds()-timeOnPeak).multiply(tariff.offPeakRate()));
		cost = cost.setScale(0, RoundingMode.HALF_UP);

		return cost;
	}

    public static class LineItem {
    	//added some getters and setters
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
