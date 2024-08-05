package com.bantads.auth.controllers;


import com.bantads.auth.services.UsuarioService;
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
    private final UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity registrarUsuario(@RequestBody Usuario usuario) {
        try {
            return usuarioService.criarOuAtualizarUsuario(usuario);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/autenticar")
        public ResponseEntity autenticar(@RequestBody Autenticacao autenticacao) {
        try {
            return usuarioService.autenticar(autenticacao);
            }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
