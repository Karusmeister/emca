package dataLayer;

import java.math.BigDecimal;

import com.acmetelecom.customer.Tariff;

public class LocalTariff implements ILocalTariff {
	private Tariff tariff;

	public LocalTariff(Tariff tariff) {
		this.tariff = tariff;
	}

	@Override
	public BigDecimal offPeakRate() {
		BigDecimal tariffCost = new BigDecimal(0);
		try {
			if (tariff != null)
				tariffCost = tariff.offPeakRate();
		} catch (Exception e) {

		}
		return tariffCost;
	}

	@Override
	public BigDecimal peakRate() {
		BigDecimal tariffCost = new BigDecimal(0);
		try {
			if (tariff != null)
				tariffCost = tariff.peakRate();
		} catch (Exception e) {

		}
		return tariffCost;
	}
}