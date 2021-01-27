package com.epam.productmanagement.client;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class FixerResponse {
    boolean success;
    String base;
    Map<String, BigDecimal> rates;
}
