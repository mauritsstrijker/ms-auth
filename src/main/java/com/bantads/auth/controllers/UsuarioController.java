package com.bantads.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.auth.entities.Usuario;
import com.bantads.auth.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

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
}
