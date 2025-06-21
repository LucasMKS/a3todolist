package com.a3.todo.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.a3.todo.entity.Usuario;
import com.a3.todo.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

    private final UsuarioService userService;
    private final JwtService jwtService;

    public JwtFilter(UsuarioService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/register") ||
               path.startsWith("/api/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String email = null;
        try {
            email = jwtService.extractEmail(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Carrega UserDetails usando o serviço de usuário
                UserDetails userDetails = userService.findByEmail(email)
                    .map(u -> org.springframework.security.core.userdetails.User
                            .withUsername(u.getEmail())
                            .password(u.getSenha())
                            .authorities(u.getAuthorities() != null ? u.getAuthorities() : List.of()) // Adapte para como você obtém as roles/autoridades do seu Usuario
                            .build()
                    )
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado para o email do token: " + email));

                // Valida o token com o UserDetails carregado
                if (jwtService.validateToken(token, (Usuario) userDetails)) { // Assumindo que validateToken espera um Usuario
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException e) {
                // Usuário do token não encontrado no banco de dados
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                return;
            } catch (Exception e) {
                // Outras falhas de validação ou processamento
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
