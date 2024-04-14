package com.bantads.auth.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Autenticacao {
    private String usuario;
    private String senha;
}
