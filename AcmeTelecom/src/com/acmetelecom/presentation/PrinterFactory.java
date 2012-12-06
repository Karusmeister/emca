package com.acmetelecom.presentation;

//Factory for creating different printers
public class PrinterFactory {
	public static final String SYSTEM_OUT_PRINTER = "system_out";

	public static Printer getPrinter(String printerType){
		Printer printer = null;

		if(printerType.equalsIgnoreCase(SYSTEM_OUT_PRINTER))
			printer = new HtmlPrinter(System.out);
		//Other cases here

		return printer;
	}
}
