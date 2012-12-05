package dataLayer;

import java.util.ArrayList;
import java.util.List;

import com.acmetelecom.customer.CentralCustomerDatabase;
import com.acmetelecom.customer.Customer;

public class CustomerDatabase implements ICustomerDatabase {

	private List<Customer> customers = null;
	private List<ILocalCustomer> localCustomers = null;

	public CustomerDatabase() {
	}

	@Override
	public List<ILocalCustomer> getCustomers()  {
		
		
			//Exception handling needed
		try {
			customers = CentralCustomerDatabase.getInstance().getCustomers();
		} catch (Exception e){
			e.getMessage();
			
			return new ArrayList<ILocalCustomer>();
		}
		
		
			localCustomers = new ArrayList<ILocalCustomer>();
			for (Customer customer : customers) {
				localCustomers.add(new LocalCustomer(customer));
	        }
			return localCustomers;
		

		}
		

}
