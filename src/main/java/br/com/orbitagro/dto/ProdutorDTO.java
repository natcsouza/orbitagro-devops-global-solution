package br.com.orbitagro.dto;

import jakarta.validation.constraints.*;

public class ProdutorDTO {

    public record Request(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
        String cpf,

        String telefone,
        String rua,
        String cidade,
        String estado,
        String cep
    ) {}

    public record Response(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String cidade,
        String estado
    ) {}
}
