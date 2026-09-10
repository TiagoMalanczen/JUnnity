package org.example.repository;

import org.example.entities.Conta;

import java.util.Optional;

public interface ContaRepository {

    Optional<Conta> buscarPorId(Long id);
    void salvar(Conta conta);

}
