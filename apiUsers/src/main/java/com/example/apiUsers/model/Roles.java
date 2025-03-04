package com.example.apiUsers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "roles")
public class Roles {
    //annotazione che identifica la variabile come chiave primaria dell'Entità
    @Id
    //identificazione dell id come autoincrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
