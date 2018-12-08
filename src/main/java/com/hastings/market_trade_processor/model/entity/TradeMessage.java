package com.hastings.market_trade_processor.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by emmakhastings on 08/12/2018
 *
 * @author emmakhastings
 * <p>
 * Entity class to store trade message in data store
 */
@Entity
public class TradeMessage {

    @Id
    @GeneratedValue
    private Long id;

    private final String userId;

    private final String currencyFrom;

    private final String currencyTo;

    private final Double amountSell;

    private final Double amountBuy;

    private final BigDecimal rate;

    private final LocalDateTime timePlaced;

    private final String originatingCountry;

    public TradeMessage(String userId, String currencyFrom, String currencyTo, Double amountSell, Double amountBuy, BigDecimal rate, LocalDateTime timePlaced, String originatingCountry) {
        this.userId = userId;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amountSell = amountSell;
        this.amountBuy = amountBuy;
        this.rate = rate;
        this.timePlaced = timePlaced;
        this.originatingCountry = originatingCountry;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public Double getAmountSell() {
        return amountSell;
    }

    public Double getAmountBuy() {
        return amountBuy;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDateTime getTimePlaced() {
        return timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }
}
