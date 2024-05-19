package com.example.banksystem.utils;

import com.example.banksystem.dto.TransactionDTO;
import lombok.Getter;

@Getter
public abstract class TransactionPerformer {
    private Double feeAmount;
    public TransactionPerformer(Double feeAmount){
        this.feeAmount = feeAmount;
    }

    public abstract Double performTransaction(Double amount);
}
