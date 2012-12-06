package acceptance;

import com.acmetelecom.BillingSystem;

import fit.*;

/**
 * <p>
 * GivenThatTheSystemHasBeenInitialised class.
 * </p>
 * 
 */
public class GivenThatTheSystemHasBeenInitialised extends Fixture {
	
	/** {@inheritDoc} */
	@Override
	public void doTable(Parse p) {
		SystemUnderTest.mockBillingSystem= new BillingSystem();
	
	}
}
