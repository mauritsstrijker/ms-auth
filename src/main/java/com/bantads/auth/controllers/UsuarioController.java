package com.bantads.auth.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.auth.entities.Autenticacao;
import com.bantads.auth.entities.Usuario;
import com.bantads.auth.helpers.JwtTokenHelper;
import com.bantads.auth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHelper jwtTokenHelper;

    @PostMapping("/registrar")
    public ResponseEntity registrarUsuario(@RequestBody Usuario usuario) {
        try {
                if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado.");
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
                usuarioRepository.save(usuario);
                return ResponseEntity.ok(HttpStatus.CREATED);
            } catch (Exception e){
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody Autenticacao autenticacao) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuario(autenticacao.getUsuario());
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                if (!passwordEncoder.matches(autenticacao.getSenha(), usuario.getSenha())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
                }
                String token = jwtTokenHelper.createToken(usuario);
                return ResponseEntity.ok().body(token);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
