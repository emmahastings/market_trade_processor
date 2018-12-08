package com.hastings.market_trade_processor.controller;

import com.hastings.market_trade_processor.exception.InvalidTradeMessageDtoException;
import com.hastings.market_trade_processor.model.ExceptionDetails;
import com.hastings.market_trade_processor.model.dto.MessageFormatInstruction;
import com.hastings.market_trade_processor.model.dto.TradeMessageDto;
import com.hastings.market_trade_processor.model.dto.TradeResponseDto;
import com.hastings.market_trade_processor.service.MessageProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by emmakhastings on 04/12/2018
 *
 * @author emmakhastings
 * <p>
 * <p>
 * Rest controller to pass requests to service handling business logic
 */
@RestController
public class MessageController {

    private final MessageProcessorService messageProcessorService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MessageController(MessageProcessorService messageProcessorService) {
        this.messageProcessorService = messageProcessorService;
    }

    /**
     * Get instructions
     *
     * @return {@link MessageFormatInstruction}
     */
    @GetMapping
    public MessageFormatInstruction getInstruction() {
        logger.debug("Getting instructions");
        return new MessageFormatInstruction("POST a trade message to the following endpoint in JSON format");
    }

    /**
     * Handle supplied trade message
     *
     * @param tradeMessageDto the {@link TradeMessageDto}
     * @return {@link TradeResponseDto}
     * @throws InvalidTradeMessageDtoException if invalid trade message supplied
     */
    @PostMapping
    public TradeResponseDto processMessage(@RequestBody @Valid TradeMessageDto tradeMessageDto) throws InvalidTradeMessageDtoException {
        logger.debug("Processing message");
        return messageProcessorService.processMessage(tradeMessageDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ExceptionDetails
    handleInvalidArgumentsRequest(MethodArgumentNotValidException ex) {
        logger.error("Invalid arguments supplied");
        BindingResult result = ex.getBindingResult();
        List<String> errorMessage = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).sorted().collect(Collectors.toList());
        logger.error("Attempted POST request with following errors: " + errorMessage);
        return new ExceptionDetails(HttpStatus.BAD_REQUEST, String.join(", ", errorMessage));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTradeMessageDtoException.class)
    @ResponseBody
    ExceptionDetails
    handleInvalidTradeMessageDto(InvalidTradeMessageDtoException ex) {
        logger.error("Invalid trade message supplied");
        return new ExceptionDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
