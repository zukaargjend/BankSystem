package com.example.banksystem.controllers;

import com.example.banksystem.dto.TransactionDTO;
import com.example.banksystem.entities.Account;
import com.example.banksystem.excpetions.BankSystemRequestException;
import com.example.banksystem.services.AccountService;
import com.example.banksystem.services.BankService;
import com.example.banksystem.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    public AccountService accountService;
    public BankService bankService;
    public TransactionService transactionService;

    @PostMapping("/bank")
    public void saveBank(@RequestParam String bankName){
        bankService.createBank(bankName);
    }

    @GetMapping("/bank/{bankId}")
    public String getAllBanks(@PathVariable Long bankId) throws BankSystemRequestException {
        return bankService.getBankById(bankId).toString();
    }

    @PostMapping("/account/{bankId}")
    public void saveAccount(@RequestParam String name, @PathVariable Long bankId){
        this.accountService.createAccount(name, bankId);
    }

    @GetMapping("/account/{accountId}")
    public String getAllAccounts(@PathVariable Long accountId){
        return accountService.getAccountById(accountId);
    }

    @PostMapping("/transaction")
    public void performTransaction(@RequestBody TransactionDTO transactionDTO) throws BankSystemRequestException{
        this.accountService.performTransaction(transactionDTO);
    }

    @PostMapping("/transaction/withdraw")
    public void withdraw(@RequestBody TransactionDTO transactionDTO){
        this.accountService.withdraw(transactionDTO);
    }

    @PostMapping("/transaction/deposit")
    public  void deposit(@RequestBody TransactionDTO transactionDTO){
        this.accountService.deposit(transactionDTO);
    }

    @GetMapping("/transaction/{accountId}")
    public Set<String> transactionPerAccount(@PathVariable Long accountId){
        return this.transactionService.transactionOverviewsPerAccount(accountId);
    }

    @GetMapping("/account/balance/{accountId}")
    public String accountBalance(@PathVariable Long accountId){
        return this.accountService.accountsBalance(accountId);
    }

    @GetMapping("/bank/account/{bankId}")
    public List<Account> accountPerBank(@PathVariable Long bankId) {
        return this.bankService.getAllAccounts(bankId);
    }

    @GetMapping("/bank/totalTransactionFeeAmount")
    public String getTotalTransactionFeeAmountPerBank(@RequestParam String bankName) {
        return this.bankService.checkTotalTransactionFeeAmount(bankName);
    }

    @GetMapping("/bank/totalTransferAmountPerBank")
    public String getTotalTransferAmountPerBank(@RequestParam String bankName) {
        return this.bankService.checkTotalTransferAmount(bankName);
    }
}
