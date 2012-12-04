package dataLayer;

import java.math.BigDecimal;

import com.acmetelecom.customer.Tariff;

public class LocalTariff implements ILocalTariff {

	private Tariff tariff;

	public LocalTariff(Tariff tariff){
		this.tariff = tariff;
	}

	@Override
	public BigDecimal offPeakRate() {
		//Exception handling needed
		if(tariff != null)
			return tariff.offPeakRate();
		return new BigDecimal(0);
	}

	@Override
	public BigDecimal peakRate() {
		//Exception handling needed
		if(tariff != null)
			return tariff.peakRate();
		return new BigDecimal(0);
	}


}
