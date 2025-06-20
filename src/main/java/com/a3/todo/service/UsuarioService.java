package com.a3.todo.service;

import com.a3.todo.dto.UsuarioResponseDTO;
import com.a3.todo.entity.Grupo;
import com.a3.todo.entity.Usuario;
import com.a3.todo.repository.GrupoRepository;
import com.a3.todo.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    /**
     * Adiciona um usuário já cadastrado a um grupo, com uma função específica.
     */
    public Usuario adicionarUsuario(Long grupoId, String email, String funcao) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));

        String emailAutenticado = getEmailUsuarioAutenticado();
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(emailAutenticado)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        // Verifica se o usuário autenticado é membro do grupo
        if (usuarioAutenticado.getGrupos().stream().noneMatch(g -> g.getId().equals(grupoId))) {
            throw new RuntimeException("Você não tem permissão para adicionar usuários neste grupo.");
        }

        // Usuário a ser adicionado
        Usuario usuarioParaAdicionar = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com este e-mail não encontrado."));

        // Adiciona o grupo à lista de grupos dele, se ainda não estiver
        if (usuarioParaAdicionar.getGrupos().stream().noneMatch(g -> g.getId().equals(grupoId))) {
            usuarioParaAdicionar.getGrupos().add(grupo);
        }

        usuarioParaAdicionar.setFuncao(funcao);
        return usuarioRepository.save(usuarioParaAdicionar);
    }

    public List<Usuario> listarUsuariosPorGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
        return grupo.getUsuarios(); // utiliza o relacionamento do Grupo
    }

    public void removerUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    public Usuario atualizarFuncao(Long usuarioId, String novaFuncao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setFuncao(novaFuncao);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    private String getEmailUsuarioAutenticado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setFuncao(usuario.getFuncao());

        // Se quiser apenas um grupo (como antes), pegue o primeiro:
        if (usuario.getGrupos() != null && !usuario.getGrupos().isEmpty()) {
            Grupo grupo = usuario.getGrupos().get(0);
            dto.setGrupoId(grupo.getId());
            dto.setGrupoNome(grupo.getNome());
        }

        return dto;
    }
}
