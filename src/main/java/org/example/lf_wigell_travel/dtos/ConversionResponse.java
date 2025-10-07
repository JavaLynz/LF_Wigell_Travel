package org.example.lf_wigell_travel.dtos;

import java.math.BigDecimal;
import java.util.Map;

public class ConversionResponse {
    private Map<String, BigDecimal> rates;

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
