package presentation;

import java.io.PrintStream;

public class HtmlPrinter implements Printer {

	private PrintStream stream;

	public HtmlPrinter(PrintStream aStream) {
		stream = aStream;
	}

	public void printHeading(String name, String phoneNumber, String pricePlan) {
		beginHtml();
		stream.append(h2(name + "/" + phoneNumber + " - " + "Price Plan: "
				+ pricePlan)
				+ "\n");
		beginTable();
	}

	public void beginTable() {
		stream.append("<table border=\"1\">" + "\n");
		stream.append(tr(th("Time") + th("Number") + th("Duration")
				+ th("Cost"))
				+ "\n");
	}

	public void endTable() {
		stream.append("</table>" + "\n");
	}

	public String h2(String text) {
		return "<h2>" + text + "</h2>" + "\n";
	}

	public void printItem(String time, String callee, String duration,
			String cost) {
		stream.append(tr(td(time) + td(callee) + td(duration) + td(cost)));
	}

	public String tr(String text) {
		return "<tr>" + text + "</tr>" + "\n";
	}

	public String th(String text) {
		return "<th width=\"160\">" + text + "</th>" + "\n";
	}

	public String td(String text) {
		return "<td>" + text + "</td>" + "\n";
	}

	public void printTotal(String total) {
		endTable();
		stream.append(h2("Total: " + total + "\n"));
		endHtml();
	}

	public void beginHtml() {
		stream.append("<html>" + "\n");
		stream.append("<head></head>" + "\n");
		stream.append("<body>" + "\n");
		stream.append("<h1>" + "\n");
		stream.append("Acme Telecom" + "\n");
		stream.append("</h1>" + "\n");
	}

	public void endHtml() {
		stream.append("</body>" + "\n");
		stream.append("</html>" + "\n");
	}
}
