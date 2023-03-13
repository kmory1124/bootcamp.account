package com.bootcamp.account.controller;

import com.bootcamp.account.entity.AccountEntity;
import com.bootcamp.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/Account")
public class AccountController {
    @Autowired
    AccountService accountService;

    //List all Account of bank
    @GetMapping(value = "/ListAll")
    public Flux<AccountEntity> ListAll(){
        return accountService.ListAll();
    }

    //List all the account by accountNumber
    @GetMapping(value = "/ListByAccount/{accountNumber}")
    public Mono<AccountEntity> ListByDocument(@PathVariable("accountNumber") String accountNumber){
        return accountService.filterByAccount(accountNumber);
    }

    //Insert new account with not validate
    @PostMapping(value = "/register")
    public Mono<AccountEntity> Save(@RequestBody AccountEntity Aco){
        return accountService.register(Aco);
    }

    //Insert new account with validate
    @PostMapping(value = "/RegisterSaving")
    public Mono<AccountEntity> registerClient(@RequestBody AccountEntity Aco){
        return accountService.registerAccountSaving(Aco);
    }

}
