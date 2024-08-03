package com.bantads.auth.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("usuario")
public class Usuario {
    @Id
    private String id;
    @Indexed
    private String usuario;
    private String senha;
    private String perfil;
    private String gerenteId;
    private String contaId;
    private String clienteId;
}
