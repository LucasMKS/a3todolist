package com.a3.todo.service;

import com.a3.todo.dto.GrupoResponseDTO;
import com.a3.todo.dto.TarefaSimplesDTO;
import com.a3.todo.dto.UsuarioSimplesDTO;
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
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Grupo criarGrupo(String nome) {
    Grupo grupo = new Grupo();
    grupo.setNome(nome);

    Grupo grupoSalvo = grupoRepository.save(grupo);

    // Vincula o usuário autenticado ao grupo
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));
    usuario.setGrupo(grupoSalvo);
    usuarioRepository.save(usuario);

    return grupoSalvo;
}

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public Optional<Grupo> buscarPorId(Long id) {
        return grupoRepository.findById(id);
    }

    public void deletarGrupo(Long id) {
        grupoRepository.deleteById(id);
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