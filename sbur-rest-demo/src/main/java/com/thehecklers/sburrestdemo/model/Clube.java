package com.thehecklers.sburrestdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clube")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ideal para MySQL (Auto-Increment)
    private Long id;

    @NotBlank(message = "O nome do clube é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "ano_fundacao")
    private Integer anoFundacao;
}