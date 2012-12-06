package acceptance;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.acmetelecom.Call;
import com.acmetelecom.CallEvent;

import java.util.Date;
import fit.ColumnFixture;
import fit.Parse;

public class GivenTheFollowingCalls extends ColumnFixture {
	public String Caller, Callee, StartTime, EndTime;

	/** {@inheritDoc} */
	@Override
	public void doRows(Parse rows) {
		super.doRows(rows);
	}

	/** {@inheritDoc} */
	@Override
	public void reset() {
		Caller = null;
		Callee = null;
		StartTime = null;
		EndTime = null;
	}

	/** {@inheritDoc} */
	@Override
	public void execute() {

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/mm/yyyy HH:mm:ss");

		Date startCall = null;
		Date endCall = null;
		try {
			startCall = dateFormat.parse(StartTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			endCall = dateFormat.parse(EndTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CallEvent start = new CallEvent(Caller, Callee, startCall.getTime());
		CallEvent end = new CallEvent(Caller, Callee, endCall.getTime());

		Call newCall = new Call(start,end);
		
		SystemUnderTest.mockBillingSystem.calls.add(newCall);
	}
}
