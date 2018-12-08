package com.hastings.market_trade_processor.model.dto;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Created by emmakhastings on 04/12/2018
 *
 * @author emmakhastings
 * <p>
 * Model class to represent JSON input, includes validation rules based on assumptions from challenge desciption.
 */
public final class TradeMessageDto {

    @NotEmpty(message = "User ID cannot be null or empty")
    private String userId;

    @NotEmpty(message = "Currency From cannot be null or empty")
    private String currencyFrom;

    @NotEmpty(message = "Currency To cannot be null or empty")
    private String currencyTo;

    @Positive(message = "Amount sell must be positive")
    @DecimalMin(value = "0.01", message = "Amount sell must be a valid currency decimal")
    private Double amountSell;

    @Positive(message = "Amount buy must be positive")
    @DecimalMin(value = "0.01", message = "Amount buy must be a valid currency decimal")
    private Double amountBuy;

    @Positive(message = "Rate must be positive")
    private BigDecimal rate;

    @NotEmpty(message = "Time placed cannot be null or empty")
    private String timePlaced;

    @NotEmpty(message = "Originating country cannot be null or empty")
    private String originatingCountry;

    // Constructor used for testing
    protected TradeMessageDto() {
    }

    public TradeMessageDto(String userId, String currencyFrom, String currencyTo, Double amountSell, Double amountBuy, BigDecimal rate, String timePlaced, String originatingCountry) {
        this.userId = userId;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amountSell = amountSell;
        this.amountBuy = amountBuy;
        this.rate = rate;
        this.timePlaced = timePlaced;
        this.originatingCountry = originatingCountry;
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

    public String getTimePlaced() {
        return timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }
}
