//Il model è dove vengono definite tutte le entità fondamentali
package com.example.apiUsers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Le prime due annotazioni generano automaticamente un costruttore
//default ed un altro contente tutti gli attributi
@NoArgsConstructor
@AllArgsConstructor
//La Data annotation genera automaticamente i metodi fondamentali come getter setter e toString
@Data
//L'Entity annotation serve ad associare questa classe,quindi entità ad una tabella
@Entity
//La Table annotion permette di customizzare l associazione dell'entità al database
//specificando il nome della tabella e potendo specificare altri parametri
@Table(name = "users")
public class User {
    //credenziali che andremo a chiedere all'utente

    //annotazione che identifica la variabile come chiave primaria dell'Entità
    @Id
    //annotazione che identifica l id come autoincrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
