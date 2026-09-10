package org.example.service;

import org.example.entities.Conta;
import org.example.repository.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @DisplayName("Erro caso a conta de origem nao tenha saldo suficiente")
    public void saldoInsuficiente(){
        Conta conta1 = new Conta(1L, "Joao", new BigDecimal("50.00"));
        Conta conta2 = new Conta(2L, "Maria", new BigDecimal("100.00"));

        when(contaRepository.buscarPorId(1L)).thenReturn(Optional.of(conta1));
        when(contaRepository.buscarPorId(2L)).thenReturn(Optional.of(conta2));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> contaService.tranferir(
                conta1.getId(), conta2.getId(), new BigDecimal("100.00")));

        assertEquals("Saldo da conta e origem insuficiente", exception.getMessage());

        verify(contaRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Conta origem inexistente")
    public void contaOrigemInexistente(){

    when(contaRepository.buscarPorId(1L)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            contaService.tranferir(1L, 2L, new BigDecimal("10")));

    assertEquals("Conta de origem nao encontrada", exception.getMessage());

        verify(contaRepository, never()).buscarPorId(2L);
        verify(contaRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Conta destino nao existe")
    public void contaDestinoInexistente(){
        Conta conta = new Conta(1L, "Marco", new BigDecimal("100"));

        when(contaRepository.buscarPorId(1L)).thenReturn(Optional.of(conta));
        when(contaRepository.buscarPorId(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contaService.tranferir(1L, 2L, new BigDecimal("299")));

        assertEquals("Conta de destino nao encontrada", exception.getMessage());
        verify(contaRepository, times(1)).buscarPorId(1L);
        verify(contaRepository, times(1)).buscarPorId(2L);
        verify(contaRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Verifica valor nulo")
    public void nulo(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contaService.tranferir(1L, 2L, null));
        assertEquals("O valor eh negativo ou nulo", exception.getMessage());
        verifyNoInteractions(contaRepository);
    }
    @Test
    @DisplayName("Verifica valor negativo")
    public void negativo(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                contaService.tranferir(1L, 2L, new BigDecimal("-10")));
        assertEquals("O valor eh negativo ou nulo", exception.getMessage());
        verifyNoInteractions(contaRepository);

    }
}