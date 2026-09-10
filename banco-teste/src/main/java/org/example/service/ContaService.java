package org.example.service;


import org.example.entities.Conta;
import org.example.repository.ContaRepository;

import java.math.BigDecimal;

public class ContaService {

    private ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }
    public void tranferir(Long idOrigem, Long idDestino, BigDecimal valor){
        Conta origem = contaRepository.buscarPorId(idOrigem)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem nao encontrada"));
        Conta destino = contaRepository.buscarPorId(idDestino)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino nao encontrada"));

        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0   ) {
            throw new IllegalArgumentException("O valor eh negativo");
        }
        if(valor.compareTo(origem.getSaldo()) > 0){
            throw new IllegalArgumentException("Saldo da conta e origem insuficiente");
        }
        origem.debitar(valor);
        destino.creditar(valor);

         contaRepository.salvar(origem);
         contaRepository.salvar(destino);
    }
}
