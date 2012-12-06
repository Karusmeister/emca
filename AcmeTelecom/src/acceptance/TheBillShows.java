package acceptance;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import com.acmetelecom.BillGenerator;
import presentation.HtmlPrinter;

import fit.*;

/**
 * The Class TheBillShows.
 * 
 */
public class TheBillShows extends RowFixture {
	public static class Row {
		public int Line;
		public String Text;

		public Row(int line, String text) {
			Line = line;
			Text = text;
		}
	}

	@Override
	public Class<?> getTargetClass() {
		return Row.class;
	}

	@Override
	public Object[] query() throws Exception {

		HtmlPrinter printer;
		ByteArrayOutputStream baos;
		String html = new String();

		baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(html);
		printer = new HtmlPrinter(ps);

		BillGenerator billGen = new BillGenerator(printer);
		SystemUnderTest.mockBillingSystem.billGenerator = billGen;

		String cleanBill = html.replaceAll("<[^>]*>", "");

		List<Row> rows = new ArrayList<Row>();
		for (String line : cleanBill.split("\n")) {
			rows.add(new Row(rows.size() + 1, line));
		}
		return rows.toArray();
	}
}
