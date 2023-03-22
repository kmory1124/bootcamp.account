package com.bootcamp.account.services;

import com.bootcamp.account.entity.AccountEntity;
import com.bootcamp.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

//Anotacion que indica que esta clase sera de tipo servicio,
@Service
public class AccountServiceImplementation implements AccountService {

    //anotacion para enlazar con la clase repositorio
    @Autowired
    private AccountRepository accountRepository;

    @Override
    //implementacion para listar todas las cuentas
    public Flux<AccountEntity> ListAll() {
        return accountRepository.findAll();
    }

    @Override
    //implementacion para listar cuentas por numero de cuenta (de uso interno) (no se expondrá en el controller)
    public Mono<AccountEntity>getByAccount(String AccountNumber){
        return accountRepository.findAll().filter(x -> x.getNumberAccount()!=null && x.getNumberAccount().equals(AccountNumber)).next();
    }

    @Override
    //implementacion para listar cuentas por numero de DOCUMENTO (de uso interno) (no se expondrá en el controller)
    public Mono<AccountEntity>getByDocument(String DocumentNumber){
        return accountRepository.findAll().filter(x -> x.getDocumentNumber()!=null && x.getDocumentNumber().equals(DocumentNumber)).next();
    }

    //implementacion para listar todas las cuentas filtrado por numero de cuenta (se añadirá validacion si no existe, arrojar mensaje perosnalizado)
    //se expondrá en el controller
    @Override
    public Mono<AccountEntity> filterByAccount(String AccountNumber) {
        return accountRepository.findAll().filter(x -> x.getNumberAccount() != null && x.getNumberAccount().equals(AccountNumber)).next();

    }

    //implementacion para realizar validaciones de cuenta, se ingrasa numero de cuenta y debe arrojar el tipo de cuenta
    //se realizará posteriormente por falta de tiempo.
    @Override
    public Mono<AccountEntity> validateCredit(String AccountNumber) {
        return accountRepository.findAll().filter(x -> x.getNumberAccount() != null && x.getNumberAccount().equals(AccountNumber)).next();

    }

    //implementacion para registrar cuentas sin validar si existen.
    @Override
    public Mono<AccountEntity> register(AccountEntity EntAccount) {
        EntAccount.setDateCreate(new Date());
        return accountRepository.save(EntAccount);
    }

    //export in controller (1)
    //implementacion para registrar cuentas de ahorro
    //se añadira posteriormente la validacion de que las condiciones apliquen dentro de un mismo mes.
    @Override
    public Mono<AccountEntity> registerAccountSaving(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5); // limite maximo de movimientos
        EntAccount.setComision(0.00); // 0 comision
        EntAccount.setDateCreate(new Date()); //fecha actual
        EntAccount.setTypeAccount("saving");
        EntAccount.setDescription("Cuenta de ahorros");
        EntAccount.setAmount(0.00);



        //EL FRONT DEBE VALIDAR POR SU LADO CON EL METODO checkClientPersona DE SU
        //MICROSERVICIO SI EL CLIENTE ES TIPO PERSONAL
        if (EntAccount.getTypeClient().equals("P"))
            return  getByDocument(EntAccount.getDocumentNumber()) //valida si existe cuentas para el cliente actual
                    .filter(i->i.getTypeClient().equals("P")) //si existe se valida si tambien es de tipo personal
                    .switchIfEmpty( //en caso no exista alguan cuenta para el cliente actual, se procede a registrar
                            getByAccount(EntAccount.getNumberAccount())//validando que el numero de cuenta a registrar tampoco exista
                                    .filter(i -> i.getTypeAccount().equals("saving")) // // y si tiene una cuenta que no sea de tipo ahorro
                                    .switchIfEmpty(accountRepository.save(EntAccount)));
        else
            return null;
    }
    //export in controller (1)
    //implementacion para registrar cuentas corrientes
    //se añadira posteriormente la validacion de que las condiciones apliquen dentro de un mismo mes.
    @Override
    public Mono<AccountEntity> registerAccountCurrent(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(5); //sin limite de movimientos -> se cambio el limite de movimiento por lo solicitando en la tarea de la semana 2
        EntAccount.setComision(10.00); // 10% comision
        EntAccount.setDateCreate(new Date()); //fecha actual
        EntAccount.setTypeAccount("Current");
        EntAccount.setDescription("Cuenta corriente");
        EntAccount.setAmount(0.00);


        //EL FRONT DEBE VALIDAR POR SU LADO CON EL METODO checkClientPersona DE SU
        //MICROSERVICIO SI EL CLIENTE ES TIPO PERSONAL
        //VALIDA SI ES PERSONAL O EMPRESARIAL
         if (EntAccount.getTypeClient().equals("P"))
             //si es cuenta personal solo se admite 1 si es empresarial adminte mas de 1
             return getByDocument(EntAccount.getDocumentNumber()) //se valida si el cliente actual tiene alguna cuenta registrada
                     .filter(i->i.getTypeClient().equals("P"))//en caso tenga alguna cuenta adicional, se valida si es de tipo personal como doble validacion.
                     .switchIfEmpty( //en caso el cliente actual no cuente con alguna cuenta segun lo filtrado en la linea anterior, se procede a registrarla
                             getByAccount(EntAccount.getNumberAccount()) //se valida tambien que el numero de cuenta a registrar tampoco exista
                                     .filter(i->i.getTypeAccount().equals("Current")) // y si tiene una cuenta que no sea de tipo cuenta corriente
                                     .switchIfEmpty(accountRepository.save(EntAccount)));
         else // si es tipo empresarial se permite registrar mas de una cuenta corriente, validando que no se repita el codigo de cuenta
             return getByAccount(EntAccount.getNumberAccount())
                     .switchIfEmpty(accountRepository.save(EntAccount));
    }

    //implementacion para registrar cuentas plazo fijo
    //pendiente modificar la fecha de pago (1)
    //se añadira posteriormente la validacion de que las condiciones apliquen dentro de un mismo mes.
    public Mono<AccountEntity> registerAccountPermanentDeadlines(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(1); //limite de movimiento en una fecha especifica del mes
        EntAccount.setComision(0.00); //0 comision
        EntAccount.setDateCreate(new Date()); //fecha actual - se considerá la fecha para el movimiento del mes
        EntAccount.setDatePay(null); //fecha de pago , se implementará posteriormente el aumentarle 1 mes la proxima fecha de pago
        EntAccount.setTypeAccount("Deadlines");
        EntAccount.setDescription("Cuenta plazo fijos");
        EntAccount.setAmount(0.00);

        //el front debe validar con el metodo checkClientPersona de su microservicio si el cliente es tipo persona
        if(EntAccount.getTypeClient().equals("P")) //si el cliente es tipo personal registra validando que este no tenga alguna cuenta registrada
        {
            return getByDocument(EntAccount.getDocumentNumber()) //valida que no exista una cuenta para un cliente
                    .filter(i -> i.getTypeClient().equals("P")) //se realiza doble validacion para ver si la cuenta que tiene tambien es de tipo personal
                    .switchIfEmpty( //en caso no exista ninguna cuenta se procede a registrar la cuenta.
                            getByAccount(EntAccount.getNumberAccount()) //validando tambien que el numero de cuenta ingresado tampoco exista
                                    .filter(i -> i.getTypeAccount().equals("Deadlines")) // y si tiene una cuenta que no sea de tipo plazo fijo
                                    .switchIfEmpty(accountRepository.save(EntAccount)));
        }
        else
            return null;

    }

    //implementacion para registrar credito personal
    //se creo la implementación, por falta de tiempo, pendiente realizar el codigo (este pertenece a otra implementación anterior)
    //solo debe permitir 1 por persona
    @Override
    public Mono<AccountEntity> registerCreditPersonal(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(null);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());
        EntAccount.setDatePay(null); //fecha de pago , se implementará posteriormente el aumentarle 1 mes la proxima fecha de pago
        EntAccount.setTypeAccount("CreditPersonal");
        EntAccount.setDescription("Credito Personal");


        if(EntAccount.getTypeClient().equals("P")) //si el cliente es tipo personal registra validando que este no tenga alguna cuenta registrada
        {
            return getByDocument(EntAccount.getDocumentNumber()) //valida que no exista una cuenta de credito para el cliente
                    .filter(i -> i.getTypeClient().equals("P")) //se realiza doble validacion para ver si la cuenta de credito que tiene tambien es de tipo personal
                    .switchIfEmpty( //en caso no exista ninguna cuenta se procede a registrar la cuenta.
                            getByAccount(EntAccount.getNumberAccount()) //validando tambien que el numero de cuenta para el credito ingresado tampoco exista
                                    .filter(i -> i.getTypeAccount().equals("CreditPersonal")) // y si tiene un credito persona
                                    .switchIfEmpty(accountRepository.save(EntAccount)));
        }
        else
            return null;

    }

    //implementacion para registrar credito empresa
    //se creo la implementación, por falta de tiempo, pendiente realizar el codigo (este pertenece a otra implementación anterior)
    //debe permitir mas de 1 credito por persona
    @Override
    public Mono<AccountEntity> registerCreditCompany(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(null);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());
        EntAccount.setDatePay(null); //fecha de pago , se implementará posteriormente el aumentarle 1 mes la proxima fecha de pago
        EntAccount.setTypeAccount("CreditCompany");
        EntAccount.setDescription("Credito Empresarial");

        if(EntAccount.getTypeClient().equals("C")) //si el cliente es tipo empresarial
        {
            return getByAccount(EntAccount.getNumberAccount()) //validando tambien que el numero de cuenta para el credito ingresado tampoco exista
                                    .filter(i -> i.getTypeAccount().equals("CreditCompany")) // y si tiene un credito persona
                                    .switchIfEmpty(accountRepository.save(EntAccount));
        }
        else
            return null;
    }

    //implementacion para registrar tarjeta de credito
    //se creo la implementación, por falta de tiempo, pendiente realizar el codigo (este pertenece a otra implementación anterior)
    @Override
    public Mono<AccountEntity> registerCreditCard(AccountEntity EntAccount) {
        EntAccount.setLimitMoviment(null);
        EntAccount.setComision(0.00);
        EntAccount.setDateCreate(new Date());
        EntAccount.setNumberAccount(EntAccount.getNumberCard()); // se le asigna el mismo numero de tarjeta a la cuenta  para el filtrado interno.
        EntAccount.setDatePay(new Date());
        EntAccount.setTypeAccount("CreditCard");
        EntAccount.setDescription("Tarjeta de credito");

        return getByAccount(EntAccount.getNumberAccount())
                //.filter(i -> i.getTypeAccount().equals("CreditCard")) // y si tiene un credito persona
                .switchIfEmpty(accountRepository.save(EntAccount));
    }
    // valida cuantas cuentas tiene el cliente
    @Override
    public Mono<Long> countAccount(String documentNumber)
    {
        return accountRepository.findAll().filter(
                        x -> x.getDocumentNumber() != null
                                && x.getDocumentNumber().equals(documentNumber))
                .count();
    }

    // valida si el cliente tiene tarjeta credito, como parte de la validacion para creacion de cuentas clientes VIP
    @Override
    public Mono<Boolean> haveCard(String documentNumber)
    {
        return accountRepository.findAll().filter(
                        x -> x.getDocumentNumber() != null
                                && x.getDocumentNumber().equals(documentNumber)
                                && x.getTypeAccount().equals("CreditCard"))
                .hasElements();
    }
    // valida si el cliente tiene tarjeta credito, como parte de la validacion para creacion de cuentas clientes VIP
    @Override
    public Mono<Boolean> haveAccountType(String documentNumber,String type)
    {
        return accountRepository.findAll().filter(
                        x -> x.getDocumentNumber() != null
                                && x.getDocumentNumber().equals(documentNumber)
                                && x.getTypeAccount().equals(type))
                .hasElements();
    }

    //implementacion para actualizar los montos de la cuenta.
    @Override
    public Mono<AccountEntity> updateAmount(String accountNumber, Double amount){
        return getByAccount(accountNumber).flatMap(c -> {
            c.setAmount(amount);
            c.setDateModify(new Date());
            return accountRepository.save(c);
        });
    }


}
