package com.bootcamp.account.controller;

import com.bootcamp.account.entity.AccountEntity;
import com.bootcamp.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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

    //controler expuesto para el registro de credito personal
    @PostMapping(value = "/registerCreditPersonal")
    public Mono<AccountEntity> registerCreditPersonal(@RequestBody AccountEntity Aco){
        return accountService.registerCreditPersonal(Aco);
    }

    //controler expuesto para el registro de credito empresarial
    @PostMapping(value = "/registerCreditCompany")
    public Mono<AccountEntity> registerCreditCompany(@RequestBody AccountEntity Aco){
        return accountService.registerCreditCompany(Aco);
    }

    //controler expuesto para el registro de tarjeta de credito
    @PostMapping(value = "/registerCreditCard")
    public Mono<AccountEntity> registerCreditCard(@RequestBody AccountEntity Aco){
        return accountService.registerCreditCard(Aco);
    }

    //expone controller para validar cuantas cuentas tiene una persona
    @GetMapping(value = "/countAccount/{documentNumber}")
    public Mono<Long> countAccount(@PathVariable("documentNumber") String documentNumber){
        return accountService.countAccount(documentNumber);
    }

    //se expone controller para validar si el cliente tiene tarjeta de credito
    @GetMapping(value = "/haveCard/{documentNumber}")
    public Mono<Boolean> haveCard(@PathVariable("documentNumber") String documentNumber){
        return accountService.haveCard(documentNumber);
    }

    //se expone controller para validar si el cliente tiene alguna cuenta o credito
    @GetMapping(value = "/haveAccountType/{documentNumber}/{type}")
    public Mono<Boolean> haveAccountType(@PathVariable("documentNumber") String documentNumber, @PathVariable("type") String type){
        return accountService.haveAccountType(documentNumber, type);
    }

    //se expone controller para actualizar montos
    @PutMapping(value = "/updateAmount/{accountNumber}/{amount}")
    public Mono<AccountEntity> updateAmount(@PathVariable("accountNumber") String accountNumber, @PathVariable("amount") Double amount){
        return accountService.updateAmount(accountNumber,amount);
    }

}
