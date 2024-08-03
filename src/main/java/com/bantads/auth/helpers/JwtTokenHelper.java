package com.bantads.auth.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import com.bantads.auth.entities.Usuario;

import java.util.Date;

@Component
public class JwtTokenHelper {
    private String secretKey = "Zc>Pz0>qx>3PYjcTlPqUiR*l!!u7slL!2";
    private long validityInMilliseconds = 36000000;

    public String createToken(Usuario usuario) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("userId", usuario.getId())
                .claim("perfil", usuario.getPerfil())
                .claim("gerenteId", usuario.getGerenteId())
                .claim("contaId", usuario.getContaId())
                .claim("clienteId", usuario.getClienteId())
                    .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}
