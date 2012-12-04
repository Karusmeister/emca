package dataLayer;

import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.Tariff;

public class TariffDatabase implements ITariffDatabase {

	private ILocalTariff localTariff;
	private Tariff tariff;

	public TariffDatabase(){
	}

	@Override
	public ILocalTariff tariffFor(ILocalCustomer customer) {
		//Exception handling needed
		tariff = CentralTariffDatabase.getInstance().tarriffFor(customer.getCustomer());
		localTariff = new LocalTariff(tariff);
		return localTariff;
	}

}
