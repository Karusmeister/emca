package com.acmetelecom.dataLayer;

import com.acmetelecom.customer.CentralTariffDatabase;
import com.acmetelecom.customer.Tariff;

public class TariffDatabase implements ITariffDatabase {
	private ILocalTariff localTariff;
	private Tariff tariff;

	public TariffDatabase() {
	}

	@Override
	public ILocalTariff tariffFor(ILocalCustomer customer) {
		localTariff = null;
		try {
			tariff = CentralTariffDatabase.getInstance().tarriffFor(
					customer.getCustomer());
			localTariff = new LocalTariff(tariff);
			return localTariff;
		} catch (Exception e) {
		}
		return localTariff;
	}
}