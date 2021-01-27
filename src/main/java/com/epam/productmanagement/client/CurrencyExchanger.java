package com.epam.productmanagement.client;

import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyExchanger {
    BigDecimal convert(BigDecimal value, Currency source, Currency target);
}
