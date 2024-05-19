package com.example.banksystem.utils;

import com.example.banksystem.dto.TransactionDTO;

public class TransactionFlatFee extends TransactionPerformer {
    public TransactionFlatFee(Double feeAmount){
        super(feeAmount);
    }

    @Override
    public Double performTransaction(Double amount){
        return this.getFeeAmount();
    }
}
