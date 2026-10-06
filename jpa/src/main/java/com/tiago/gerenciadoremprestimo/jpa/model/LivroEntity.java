package com.tiago.gerenciadoremprestimo.jpa.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LivroEntity {

    private UUID id;
    private String title;
    private int quantity;

}
