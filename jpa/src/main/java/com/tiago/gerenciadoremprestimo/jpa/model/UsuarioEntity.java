package com.tiago.gerenciadoremprestimo.jpa.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UsuarioEntity {

    private UUID id;
    private String nome;
    private boolean lock;

}
