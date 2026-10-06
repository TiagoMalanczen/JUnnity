package com.tiago.gerenciadoremprestimo.jpa.model;


import lombok.Getter;

import java.util.UUID;

@Getter
public class LivroEntity {

    private UUID id;
    private String title;
    private int quantity;

}
