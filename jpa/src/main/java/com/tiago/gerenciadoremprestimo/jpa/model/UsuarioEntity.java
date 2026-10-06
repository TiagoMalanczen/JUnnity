package com.tiago.gerenciadoremprestimo.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UsuarioEntity {

    private UUID id;
    private String nome;
    private boolean lock;

}
