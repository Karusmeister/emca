package com.acmetelecom;

import dataLayer.ILocalCustomer;

import java.util.List;

import presentation.Printer;

public class BillGenerator {

	Printer printer;
	
	public BillGenerator(Printer printer){
		this.printer = printer;
	}
	
	// Tudor: I think it's better if we have the printer as a parameter for the
	// method
	// as we do not need to create a new BillGenerator for each printer type (if
	// we were to have more)
	
	
	/*
	 * Not really Tudor, cause this way its difficult to test bill creation method 
	 * you can't pass printer to createBillFor method
	 */
	
	public void send(ILocalCustomer customer,
			List<BillingSystem.LineItem> calls, String totalBill) {

		printer.printHeading(customer.getFullName(), customer.getPhoneNumber(),
				customer.getPricePlan());
		for (BillingSystem.LineItem call : calls) {
			printer.printItem(call.date(), call.callee(),
					call.durationMinutes(),
					MoneyFormatter.penceToPounds(call.cost()));
		}
		printer.printTotal(totalBill);
	}


}
