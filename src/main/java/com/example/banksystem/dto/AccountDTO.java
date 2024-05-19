package com.example.banksystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountDTO {
    private final Long id;
    @Setter
    private String name;
    @Setter
    private Double balance;

    public  AccountDTO(Long id, String name, Double balance){

        if(id < 0 || id == null){
            throw new IllegalArgumentException("Id can not be null!");
        }

        if(name.isBlank() || name == null){
            throw new IllegalArgumentException("Name can not be null!");
        }

        if(balance < 0){
            throw new IllegalArgumentException("Balance can not be negative");
        }

        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}
