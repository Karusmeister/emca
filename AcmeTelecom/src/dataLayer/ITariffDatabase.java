package dataLayer;

import dataLayer.ILocalTariff;

public interface ITariffDatabase {

	public ILocalTariff tariffFor(ILocalCustomer customer);

}
