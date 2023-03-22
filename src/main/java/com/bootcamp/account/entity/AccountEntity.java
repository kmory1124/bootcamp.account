package com.bootcamp.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//anotacion que ahorra codigo para la obtencion y configuracion de los atributos de la clase.
@Data
//anotacion para ahorrar codigo de constructor con 1 parametro
@AllArgsConstructor
//anotacion para ahorrar codigo de constructor sin parametros
@NoArgsConstructor
//anotacion que indica que se utilizará para mapear a la collection "Product" de la bd mongo
@Document(collection = "Product")
public class AccountEntity {

    @Id
    private String id;
    private String numberAccount;
    private String typeAccount; //saving - Current - Deadlines - CreditPersonal -CreditCompany
    private String documentNumber;
    private String description;
    private Integer limitMoviment;
    private Double comision;
    private Double amount;
    private Double credit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dateModify;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date datePay;
    private String numberCard;
    private String typeClient;


}
