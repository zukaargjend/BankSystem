package com.example.banksystem.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Bank {
    @Id
    @SequenceGenerator(
            name = "bank_id_sequence"
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bankName;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Account> accounts;
    @Min(value = 0, message = "Value of totalTransactionFeeAmount should be 0 or greater!")
    private Double totalTransactionFeeAmount;
    @Min(value = 0, message = "Value of totalTransferAmount should be 0 or greater!")
    private Double totalTransferAmount;
    @Min(value = 0, message = "Value of transactionFlatFeeAmount should be 0 or greater!")
    private Double transactionFlatFeeAmount;
    @Min(value = 0, message = "Value of transactionPercentFeeValue")
    private Double transactionPercentFeeValue;

    public void addAccount(Account account){
        if(this.accounts == null){
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }
}
