package com.hastings.market_trade_processor.service;

import com.hastings.market_trade_processor.exception.InvalidTradeMessageDtoException;
import com.hastings.market_trade_processor.model.dto.TradeMessageDto;
import com.hastings.market_trade_processor.model.dto.TradeResponseDto;
import com.hastings.market_trade_processor.repository.TradeMessageRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by emmakhastings on 05/12/2018
 *
 * @author emmakhastings
 * <p>
 * Test for {@link MessageProcessorService}
 */
public class MessageProcessorServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private TradeMessageDto tradeMessageDto;

    private MessageProcessorService messageProcessorService;

    @Mock
    private TradeMessageRepository tradeMessageRepository;

    @Before
    public void setUp() {
        messageProcessorService = new MessageProcessorService(tradeMessageRepository);
        tradeMessageDto = new TradeMessageDto("134256", "EUR", "GBP", 1000d, 747.10, BigDecimal.valueOf(0.7471), "24-JAN-18 10:27:44", "FR");
    }

    @Test
    public void processMessageShouldReturnTotalTransactionsAndTotalCustomerTransactions() throws Exception {
        Mockito.when(tradeMessageRepository.count()).thenReturn(1L);
        Mockito.when(tradeMessageRepository.countByUserId("134256")).thenReturn(1L);

        TradeResponseDto tradeResponseDto = messageProcessorService.processMessage(tradeMessageDto);
        assertThat(tradeResponseDto.getTotalTransactionsProcessed()).isEqualTo(1);
        assertThat(tradeResponseDto.getTotalCustomerTransactions()).isEqualTo(1);
    }

    @Test(expected = InvalidTradeMessageDtoException.class)
    public void processMessageShouldHandleInvalidTradeMessage() throws Exception {
        messageProcessorService.processMessage(null);
    }
}