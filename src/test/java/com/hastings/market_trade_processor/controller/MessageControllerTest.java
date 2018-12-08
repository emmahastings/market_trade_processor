package com.hastings.market_trade_processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hastings.market_trade_processor.exception.InvalidTradeMessageDtoException;
import com.hastings.market_trade_processor.model.dto.TradeMessageDto;
import com.hastings.market_trade_processor.model.dto.TradeResponseDto;
import com.hastings.market_trade_processor.service.MessageProcessorService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by emmakhastings on 05/12/2018
 *
 * @author emmakhastings
 *
 * Test for {@link MessageController}
 */

public class MessageControllerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private MessageProcessorService messageProcessorService;

    private MockMvc mockMvc;

    private String requestInput;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        MessageController messageController = new MessageController(messageProcessorService);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();

        // Create test input and convert to JSON
        TradeMessageDto tradeMessageDto = new TradeMessageDto("134256", "EUR", "GBP", 1000d, 747.10, BigDecimal.valueOf(0.7471), "24-JAN-18 10:27:44", "FR");
        requestInput = objectMapper.writeValueAsString(tradeMessageDto);
    }

    @Test
    public void getInstructionShouldReturnInstruction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("instruction").value("POST a trade message to the following endpoint in JSON format"));

        Mockito.verifyZeroInteractions(messageProcessorService);
    }

    @Test
    public void processMessageShouldReturnResponseIfValidMessageSupplied() throws Exception {
        Mockito.when(messageProcessorService.processMessage(any(TradeMessageDto.class))).thenReturn(new TradeResponseDto(1L, 1L));

        mockMvc.perform(post("/")
                .content(requestInput)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("totalTransactionsProcessed").value("1"))
                .andExpect(jsonPath("totalCustomerTransactions").value("1"));

        Mockito.verify(messageProcessorService).processMessage(any(TradeMessageDto.class));
    }

    @Test
    public void processMessageShouldHandleExceptionIfInvalidMessageSupplied() throws Exception {
        Mockito.when(messageProcessorService.processMessage(any(TradeMessageDto.class))).thenThrow(new InvalidTradeMessageDtoException("Invalid trade message supplied"));

        mockMvc.perform(post("/")
                .content(requestInput)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("message").value("Invalid trade message supplied"));

        Mockito.verify(messageProcessorService).processMessage(any(TradeMessageDto.class));
    }

    @Test
    public void processMessageShouldHandleInvalidArguments() throws Exception {

        TradeMessageDto invalidTradeMessageDto = new TradeMessageDto("", "", "", 0.00, 0.00, BigDecimal.valueOf(-0.00), null, null);
        String invalidRequestInput = objectMapper.writeValueAsString(invalidTradeMessageDto);
        String expectedValue = "Amount buy must be a valid currency decimal, Amount buy must be positive, Amount sell must be a valid currency decimal, Amount sell must be positive, Currency From cannot be null or empty, Currency To cannot be null or empty, Originating country cannot be null or empty, Rate must be positive, Time placed cannot be null or empty, User ID cannot be null or empty";

        mockMvc.perform(post("/")
                .content(invalidRequestInput)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("message").value(expectedValue));

        Mockito.verifyZeroInteractions(messageProcessorService);
    }
}