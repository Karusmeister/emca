package com.acmetelecom;


import java.util.List;

import com.acmetelecom.dataLayer.ILocalCustomer;
import com.acmetelecom.presentation.HtmlPrinter;
import com.acmetelecom.presentation.Printer;



public class BillGenerator {

//	private Printer printer;

//	public BillGenerator(Printer printer){
//		this.printer = printer;
//	}

	//Tudor: I think it's better if we have the printer as a parameter for the method
	//as we do not need to create a new BillGenerator for each printer type (if we were to have more)
    public void send(ILocalCustomer customer, List<BillingSystem.LineItem> calls, String totalBill, Printer printer) {

        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (BillingSystem.LineItem call : calls) {
            printer.printItem(call.date(), call.callee().getPhoneNumber(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        printer.printTotal(totalBill);
    }

}
