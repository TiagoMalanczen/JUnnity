package com.tiago.gerenciadoremprestimo.jpa.services;

import com.tiago.gerenciadoremprestimo.jpa.exceptions.RegraNegocioException;
import com.tiago.gerenciadoremprestimo.jpa.model.LivroEntity;
import com.tiago.gerenciadoremprestimo.jpa.model.UsuarioEntity;
import com.tiago.gerenciadoremprestimo.jpa.repository.LivroRepository;
import com.tiago.gerenciadoremprestimo.jpa.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmprestimoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Test
    @DisplayName("Deve realizar emprestimo com sucesso")
    void deveRealizarEmprestimoComSucesso(){

        UsuarioEntity usuario = new UsuarioEntity(UUID.randomUUID(), "Tiago", false);
        LivroEntity livro = new LivroEntity(UUID.randomUUID(), "Clean Code", 5);

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(livro.getId())).thenReturn(Optional.of(livro));

        emprestimoService.realizarEmprestimo(usuario.getId(), livro.getId());

        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(livroRepository, times(1)).findById(livro.getId());
        verify(livroRepository, times(1)).atualizarEstoque(livro.getId(), 4);

    }

}