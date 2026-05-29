package com.thehecklers.sburrestdemo.repository;

import com.thehecklers.sburrestdemo.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    // Consulta derivada: encontra jogadores pelo NOME do clube (já que agora é String)
    List<Jogador> findByClube(String clube);
}