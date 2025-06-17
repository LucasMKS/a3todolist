package com.a3.todo.service;

import com.a3.todo.entity.Grupo;
import com.a3.todo.entity.Usuario;
import com.a3.todo.repository.GrupoRepository;
import com.a3.todo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public Usuario adicionarUsuario(Long grupoId, Usuario usuario) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
        usuario.setGrupo(grupo);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuariosPorGrupo(Long grupoId) {
        return usuarioRepository.findByGrupoId(grupoId);
    }

    public void removerUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    public Usuario atualizarFuncao(Long usuarioId, String novaFuncao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setFuncao(novaFuncao);
        return usuarioRepository.save(usuario);
    }
}