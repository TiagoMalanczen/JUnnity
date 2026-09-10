package org.example.entities;

import java.math.BigDecimal;

public class Conta {

    private Long id;
    private String titular;
    private BigDecimal saldo;

    public Conta(Long id, String titular, BigDecimal saldo) {
        this.id = id;
        this.titular = titular;
        this.saldo = saldo;
    }

    public Long getId() { return id; }
    public String getTitular() { return titular; }
    public BigDecimal getSaldo() { return saldo; }

    public void debitar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }
}
