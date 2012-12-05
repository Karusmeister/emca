package com.acmetelecom;

import dataLayer.ILocalCustomer;

import java.util.List;

import presentation.HtmlPrinter;
import presentation.Printer;


public class BillGenerator {

    public void send(ILocalCustomer customer, List<BillingSystem.LineItem> calls, String totalBill) {

        Printer printer = HtmlPrinter.getInstance(System.out);
        printer.printHeading(customer.getFullName(), customer.getPhoneNumber(), customer.getPricePlan());
        for (BillingSystem.LineItem call : calls) {
            printer.printItem(call.date(), call.callee(), call.durationMinutes(), MoneyFormatter.penceToPounds(call.cost()));
        }
        printer.printTotal(totalBill);
    }

}
