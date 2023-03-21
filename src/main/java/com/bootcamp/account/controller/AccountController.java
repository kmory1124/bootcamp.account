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

    //List all the account by document Number
    @GetMapping(value = "/getByDocument/{DocumentNumber}")
    public Mono<AccountEntity> getByDocument(@PathVariable("DocumentNumber") String DocumentNumber){
        return accountService.getByDocument(DocumentNumber);
    }

    //Insert new account with not validate
    @PostMapping(value = "/register")
    public Mono<AccountEntity> Save(@RequestBody AccountEntity Aco){
        return accountService.register(Aco);
    }

    //controller expuesto para el registro de cuenta de ahorro
    @PostMapping(value = "/RegisterSaving")
    public Mono<AccountEntity> registerAccountSaving(@RequestBody AccountEntity Aco){
        return accountService.registerAccountSaving(Aco);
    }

    //controller expuesto para el registro de cuenta corriente
    @PostMapping(value = "/registerAccountCurrent")
    public Mono<AccountEntity> registerAccountCurrent(@RequestBody AccountEntity Aco){
        return accountService.registerAccountCurrent(Aco);
    }

    //controler expuesto para el registro de cuentas a plazo fijo
    @PostMapping(value = "/registerAccountPermanentDeadlines")
    public Mono<AccountEntity> registerAccountPermanentDeadlines(@RequestBody AccountEntity Aco){
        return accountService.registerAccountPermanentDeadlines(Aco);
    }

    //expone controller para validar cuantas cuentas tiene una persona
    @GetMapping(value = "/countAccount/{documentNumber}")
    public Mono<Long> countAccount(@PathVariable("documentNumber") String documentNumber){
        return accountService.countAccount(documentNumber);
    }

}
