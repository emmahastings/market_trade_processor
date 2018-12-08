package com.hastings.market_trade_processor.model.dto;

/**
 * Created by emmakhastings on 04/12/2018
 *
 * @author emmakhastings
 * <p>
 * Model class to hold text for client instruction
 */
public final class MessageFormatInstruction {

    private final String instruction;

    public MessageFormatInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInstruction() {
        return instruction;
    }
}

