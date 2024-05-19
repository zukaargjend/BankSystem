package com.example.banksystem.utils;

import com.example.banksystem.dto.TransactionDTO;

public class TransactionPercentFee extends TransactionPerformer{
    public TransactionPercentFee(Double feeAmount){
        super(feeAmount);
    }

    @Override
    public Double performTransaction(Double amount){
        return amount * this.getFeeAmount();
    }
}
