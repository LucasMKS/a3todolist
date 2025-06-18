package com.a3.todo.service;

import com.a3.todo.dto.AuthResponse;
import com.a3.todo.dto.AuthLoginRequest;
import com.a3.todo.dto.AuthRegisterRequest;
import com.a3.todo.entity.Usuario;
import com.a3.todo.repository.UsuarioRepository;
import com.a3.todo.config.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(AuthRegisterRequest request) {
        boolean userExists = usuarioRepository.findByEmail(request.getEmail()).isPresent();
        if (userExists) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setFuncao(request.getFuncao() != null ? request.getFuncao() : "USER");

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthLoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token);
    }
}