package com.acmetelecom;

import dataLayer.ILocalCustomer;

import java.util.List;


public class BillGenerator {
	
	private Printer printer;
	
	public BillGenerator(Printer printer){
		this.printer = printer;
	}

    public void send(ILocalCustomer customer, List<BillingSystem.LineItem> calls, String totalBill) {

        
        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (BillingSystem.LineItem call : calls) {
            printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        printer.printTotal(totalBill);
    }

}
