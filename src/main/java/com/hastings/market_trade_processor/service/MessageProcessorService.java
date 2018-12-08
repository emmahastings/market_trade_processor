package com.hastings.market_trade_processor.service;

import com.hastings.market_trade_processor.exception.InvalidTradeMessageDtoException;
import com.hastings.market_trade_processor.model.dto.TradeMessageDto;
import com.hastings.market_trade_processor.model.dto.TradeResponseDto;
import com.hastings.market_trade_processor.model.entity.TradeMessage;
import com.hastings.market_trade_processor.repository.TradeMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Created by emmakhastings on 04/12/2018
 *
 * @author emmakhastings
 * <p>
 * Service to process any trade messages received by controller
 */
@Service
public class MessageProcessorService {

    private TradeMessageRepository tradeMessageRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageProcessorService(TradeMessageRepository tradeMessageRepository) {
        this.tradeMessageRepository = tradeMessageRepository;
    }

    /**
     * Process trade message and store in underlying data store.
     * Will output response indicating total number of transactions and the total number for supplied user ID.
     *
     * @param tradeMessageDto the {@link TradeMessageDto}
     * @return {@link TradeResponseDto}
     * @throws InvalidTradeMessageDtoException if invalid trade message supplied
     */
    public TradeResponseDto processMessage(TradeMessageDto tradeMessageDto) throws InvalidTradeMessageDtoException {

        if (tradeMessageDto == null) {
            logger.error("Invalid trade message supplied");
            throw new InvalidTradeMessageDtoException("Invalid trade message supplied");
        }

        logger.debug("Processing message for user with ID: {}", tradeMessageDto.getUserId());
        TradeMessage tradeMessage = createTradeMessage(tradeMessageDto);
        tradeMessageRepository.save(tradeMessage);
        return buildResponse(tradeMessageDto.getUserId());
    }

    private TradeMessage createTradeMessage(TradeMessageDto tradeMessageDto) {
        logger.debug("Creating database entity for trade message");
        return new TradeMessage(tradeMessageDto.getUserId(),
                tradeMessageDto.getCurrencyFrom(),
                tradeMessageDto.getCurrencyTo(),
                tradeMessageDto.getAmountBuy(),
                tradeMessageDto.getAmountSell(),
                tradeMessageDto.getRate(),
                processDate(tradeMessageDto.getTimePlaced()),
                tradeMessageDto.getOriginatingCountry());
    }

    private LocalDateTime processDate(String timePlaced) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd-MMM-yy HH:mm:ss")
                .toFormatter();
        return LocalDateTime.parse(timePlaced, formatter);
    }

    private TradeResponseDto buildResponse(String userId) {
        logger.debug("Building response");
        Long totalTransactions = tradeMessageRepository.count();
        Long customerTransactions = tradeMessageRepository.countByUserId(userId);
        return new TradeResponseDto(totalTransactions, customerTransactions);
    }
}
