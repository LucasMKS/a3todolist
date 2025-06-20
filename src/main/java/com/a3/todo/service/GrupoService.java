package com.a3.todo.service;

import com.a3.todo.dto.GrupoResponseDTO;
import com.a3.todo.dto.TarefaSimplesDTO;
import com.a3.todo.dto.UsuarioSimplesDTO;
import com.a3.todo.entity.Grupo;
import com.a3.todo.entity.Usuario;
import com.a3.todo.repository.GrupoRepository;
import com.a3.todo.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Grupo criarGrupo(String nome) {
        Grupo grupo = new Grupo();
        grupo.setNome(nome);
        grupo.setTarefas(new ArrayList<>());
        grupo.setUsuarios(new ArrayList<>());

        Grupo grupoSalvo = grupoRepository.save(grupo);


        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        // Associa o grupo ao usuário
        if (usuario.getGrupos() == null) {
            usuario.setGrupos(new ArrayList<>());
        }

        // Adiciona o grupo na lista de grupos do usuário
        usuario.getGrupos().add(grupoSalvo);
        usuarioRepository.save(usuario);

        return grupoSalvo;
    }

    public List<Grupo> listarGruposDoUsuario() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        return usuario.getGrupos(); // lista de grupos associados ao usuário
    }

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> buscarPorId(Long id) {
        return grupoRepository.findById(id);
    }

    @Transactional // Ensures atomicity: all or nothing
    public void deletarGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado com ID: " + grupoId));

        List<Usuario> usuariosAssociados = grupo.getUsuarios();
        for (Usuario usuario : List.copyOf(usuariosAssociados)) {
            usuario.getGrupos().remove(grupo);
            usuarioRepository.save(usuario);
        }
        grupoRepository.delete(grupo);
    }

    public GrupoResponseDTO toDTO(Grupo grupo) {
        GrupoResponseDTO dto = new GrupoResponseDTO();
        dto.setId(grupo.getId());
        dto.setNome(grupo.getNome());

        dto.setUsuarios(grupo.getUsuarios().stream().map(u -> {
            UsuarioSimplesDTO user = new UsuarioSimplesDTO();
            user.setId(u.getId());
            user.setNome(u.getNome());
            user.setEmail(u.getEmail());
            user.setFuncao(u.getFuncao());
            return user;
        }).toList());

        dto.setTarefas(grupo.getTarefas().stream().map(t -> {
            TarefaSimplesDTO tarefa = new TarefaSimplesDTO();
            tarefa.setId(t.getId());
            tarefa.setTitulo(t.getTitulo());
            tarefa.setStatus(t.getStatus().name());
            return tarefa;
        }).toList());

        return dto;
    }

}