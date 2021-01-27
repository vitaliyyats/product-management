package com.epam.productmanagement.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.time.LocalDate;
import java.util.Currency;


@RequiredArgsConstructor
@Component
@Slf4j
public class FixerCurrencyExchanger implements CurrencyExchanger {
    private final RestTemplate restTemplate;

    @Value("${fixer.access_key}")
    private final String accessKey;

    @Value("${fixer.uri}")
    private final String fixerUri;

    @Cacheable("rates")
    @Override
    public BigDecimal convert(BigDecimal value, Currency source, Currency target) {

        FixerResponse fixerResponse = callFixerService(source, target);

        BigDecimal rate;
        BigDecimal result;
        if (fixerResponse.getBase().equals(source.getCurrencyCode())) {
            rate = fixerResponse.getRates().get(target.getCurrencyCode());
            result = value.multiply(rate);
        } else {
            rate = fixerResponse.getRates().get(source.getCurrencyCode());
            result = value.setScale(2).divide(rate, RoundingMode.HALF_UP);
        }
        result.setScale(2, RoundingMode.HALF_UP);
        log.info("Converted {} {} to {} {}", value, source.getCurrencyCode(), result, target.getCurrencyCode());
        return result;
    }

    private FixerResponse callFixerService(Currency source, Currency target) {
        String currDate = LocalDate.now().toString();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(fixerUri + currDate)
                .queryParam("access_key", accessKey)
                .queryParam("symbols", source.getCurrencyCode() + ',' + target.getCurrencyCode())
                .build().toUri();
        log.info("Calling external service...");
        return restTemplate.getForObject(uri, FixerResponse.class);
    }
}
