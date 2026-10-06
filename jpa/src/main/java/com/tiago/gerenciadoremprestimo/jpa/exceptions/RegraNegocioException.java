package com.tiago.gerenciadoremprestimo.jpa.exceptions;

public class RegraNegocioException extends RuntimeException{

    public RegraNegocioException(String msg) {
        super(msg);
    }
}
