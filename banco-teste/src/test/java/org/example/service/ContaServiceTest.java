package org.example.service;

import org.example.entities.Conta;
import org.example.repository.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    @DisplayName("Deve transferir valores com sucesso")
    public void transferir(){
        Conta conta1 = new Conta(1L, "Joao", new BigDecimal("100.00"));
        Conta conta2 = new Conta(2L, "Maria", new BigDecimal("200.00"));

        when(contaRepository.buscarPorId(1L)).thenReturn(Optional.of(conta1));
        when(contaRepository.buscarPorId(2L)).thenReturn(Optional.of(conta2));

        contaService.tranferir(conta1.getId(), conta2.getId(), new BigDecimal("40.00"));

        assertEquals(new BigDecimal("60.00"), conta1.getSaldo());
        assertEquals(new BigDecimal("240.00"), conta2.getSaldo());

       verify(contaRepository, times(1)).salvar(conta1);
       verify(contaRepository, times(1)).salvar(conta2);
    }

    @Test
    @DisplayName("")
}