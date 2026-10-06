package com.tiago.gerenciadoremprestimo.jpa.repository;

import com.tiago.gerenciadoremprestimo.jpa.model.LivroEntity;

import java.util.Optional;
import java.util.UUID;

public interface LivroRepository {

    Optional<LivroEntity> findById(UUID id);
    void atualizarEstoque(UUID idLivro, int quantidade);
}
