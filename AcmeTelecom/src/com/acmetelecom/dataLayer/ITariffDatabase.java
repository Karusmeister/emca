package com.acmetelecom.dataLayer;

import com.acmetelecom.dataLayer.ILocalTariff;

public interface ITariffDatabase {

	public ILocalTariff tariffFor(ILocalCustomer customer);

}
