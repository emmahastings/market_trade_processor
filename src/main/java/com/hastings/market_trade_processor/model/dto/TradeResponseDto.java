package com.hastings.market_trade_processor.model.dto;

/**
 * Created by emmakhastings on 05/12/2018
 *
 * @author emmakhastings
 * <p>
 * Model class to represent JSON response
 */
public final class TradeResponseDto {

    private final Long totalTransactionsProcessed;

    private final Long totalCustomerTransactions;

    public TradeResponseDto(Long totalTransactionsProcessed, Long totalCustomerTransactions) {
        this.totalTransactionsProcessed = totalTransactionsProcessed;
        this.totalCustomerTransactions = totalCustomerTransactions;
    }

    public Long getTotalTransactionsProcessed() {
        return totalTransactionsProcessed;
    }

    public Long getTotalCustomerTransactions() {
        return totalCustomerTransactions;
    }
}

