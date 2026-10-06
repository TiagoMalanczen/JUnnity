package com.tiago.gerenciadoremprestimo.jpa.services;

import com.tiago.gerenciadoremprestimo.jpa.exceptions.RegraNegocioException;
import com.tiago.gerenciadoremprestimo.jpa.model.LivroEntity;
import com.tiago.gerenciadoremprestimo.jpa.model.UsuarioEntity;
import com.tiago.gerenciadoremprestimo.jpa.repository.LivroRepository;
import com.tiago.gerenciadoremprestimo.jpa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class EmprestimoService {

    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public void realizarEmprestimo(UUID usuarioId, UUID livroId){
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraNegocioException("Erro ao encontrar usuario"));
        if(usuario.isLock()){
            throw new RegraNegocioException("Usuario com bloqueio");
        }
        LivroEntity livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RegraNegocioException("Erro ao encontrar livro"));
        if(livro.getQuantity() <= 0){
            throw new RegraNegocioException("Estoque insuficiente");
        }
        livroRepository.atualizarEstoque(livroId, livro.getQuantity() -1 );
    }

}
