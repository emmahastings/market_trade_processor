package com.hastings.market_trade_processor.exception;

import com.hastings.market_trade_processor.model.dto.TradeMessageDto;

/**
 * Created by emmakhastings on 08/12/2018
 *
 * @author emmakhastings
 * <p>
 * Exception class to handle potential invalid {@link TradeMessageDto}
 */
public class InvalidTradeMessageDtoException extends Exception {
    public InvalidTradeMessageDtoException(String message) {
        super(message);
    }
}
