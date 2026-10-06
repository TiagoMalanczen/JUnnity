package com.tiago.gerenciadoremprestimo.jpa.repository;

import com.tiago.gerenciadoremprestimo.jpa.model.UsuarioEntity;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Optional<UsuarioEntity> findById( UUID id);

}
