package com.bantads.auth.services;

import com.bantads.auth.entities.Autenticacao;
import com.bantads.auth.entities.Usuario;
import com.bantads.auth.helpers.JwtTokenHelper;
import com.bantads.auth.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHelper jwtTokenHelper;

    public ResponseEntity<String> criarOuAtualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(usuario.getUsuario());

        if (usuarioExistente.isPresent()) {
            Usuario usuarioAtualizado = usuarioExistente.get();
            usuarioAtualizado.setUsuario(usuario.getUsuario());
            usuarioAtualizado.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioAtualizado.setPerfil(usuario.getPerfil());
            usuarioAtualizado.setGerenteId(usuario.getGerenteId());
            usuarioAtualizado.setClienteId(usuario.getClienteId());
            usuarioAtualizado.setContaId(usuario.getContaId());
            usuarioRepository.save(usuarioAtualizado);
            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } else {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
        }
    }


    public ResponseEntity autenticar(Autenticacao autenticacao) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuario(autenticacao.getUsuario());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (!passwordEncoder.matches(autenticacao.getSenha(), usuario.getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
            }
            String token = jwtTokenHelper.createToken(usuario);
            return ResponseEntity.ok().body(token);
        }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
    }
}
