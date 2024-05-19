package com.example.banksystem.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TransactionDTO {
    private Double amount;
    private Long originatingAccountId;
    private Long resultingAccountId;
    private String transactionReason;
    private String transactionForm;
}
