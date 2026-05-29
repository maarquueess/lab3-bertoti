package com.thehecklers.sburrestdemo.controller;

import com.thehecklers.sburrestdemo.model.Jogador;
import com.thehecklers.sburrestdemo.repository.JogadorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
@RequiredArgsConstructor
public class JogadorController {

    private final JogadorRepository jogadorRepository;

    @GetMapping
    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jogador registar(@Valid @RequestBody Jogador jogador) {
        return jogadorRepository.save(jogador);
    }

    // Endpoint para buscar jogadores de uma equipe específica (agora usando String)
    @GetMapping("/clube/{clube}")
    public List<Jogador> listarPorClube(@PathVariable String clube) {
        return jogadorRepository.findByClube(clube);
    }

    // Endpoint para Atualizar um jogador existente (PUT)
    @PutMapping("/{id}")
    public org.springframework.http.ResponseEntity<Jogador> atualizar(@PathVariable Long id, @RequestBody Jogador jogadorAtualizado) {
        return jogadorRepository.findById(id)
                .map(jogadorExistente -> {
                    jogadorExistente.setNome(jogadorAtualizado.getNome());
                    jogadorExistente.setPosicao(jogadorAtualizado.getPosicao());
                    jogadorExistente.setClube(jogadorAtualizado.getClube()); // Descomentado para o CRUD visual funcionar 100%!
                    return org.springframework.http.ResponseEntity.ok(jogadorRepository.save(jogadorExistente));
                })
                .orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    // Endpoint para Deletar um jogador (DELETE)
    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<Object> deletar(@PathVariable Long id) {
        return jogadorRepository.findById(id)
                .map(jogadorExistente -> {
                    jogadorRepository.delete(jogadorExistente);
                    return org.springframework.http.ResponseEntity.noContent().build();
                })
                .orElse(org.springframework.http.ResponseEntity.notFound().build());
    }
}