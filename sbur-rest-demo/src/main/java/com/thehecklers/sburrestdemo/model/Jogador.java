package com.thehecklers.sburrestdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jogador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do jogador é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A posição é obrigatória")
    @Column(nullable = false, length = 50)
    private String posicao;

    private String clube;

    private Integer numero;
    private String pais;
}