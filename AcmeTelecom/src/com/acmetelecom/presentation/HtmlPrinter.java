package com.acmetelecom.presentation;

import java.io.PrintStream;

public class HtmlPrinter implements Printer {

	//Remove singleton
    //private static Printer instance = new HtmlPrinter();
    private static PrintStream stream;

    public HtmlPrinter(PrintStream aStream) {
    	stream = aStream;
    }

    //Remove singleton
//    public static Printer getInstance(PrintStream aStream) {
//    	stream = aStream;
//        return instance;
//    }

    public void printHeading(String name, String phoneNumber, String pricePlan) {
        beginHtml();
        stream.println(h2(name + "/" + phoneNumber + " - " + "Price Plan: " + pricePlan));
        beginTable();
    }

    public void beginTable() {
    	stream.println("<table border=\"1\">");
    	stream.println(tr(th("Time") + th("Number") + th("Duration") + th("Cost")));
    }

    public void endTable() {
    	stream.println("</table>");
    }

    public String h2(String text) {
        return "<h2>" + text + "</h2>";
    }

    public void printItem(String time, String callee, String duration, String cost) {
    	stream.println(tr(td(time) + td(callee) + td(duration) + td(cost)));
    }

    public String tr(String text) {
        return "<tr>" + text + "</tr>";
    }

    public String th(String text) {
        return "<th width=\"160\">" + text + "</th>";
    }

    public String td(String text) {
        return "<td>" + text + "</td>";
    }

    public void printTotal(String total) {
        endTable();
        stream.println(h2("Total: " + total));
        endHtml();
    }

    public void beginHtml() {
        stream.println("<html>");
        stream.println("<head></head>");
        stream.println("<body>");
        stream.println("<h1>");
        stream.println("Acme Telecom");
        stream.println("</h1>");
    }

    public void endHtml() {
        stream.println("</body>");
        stream.println("</html>");
    }
}
