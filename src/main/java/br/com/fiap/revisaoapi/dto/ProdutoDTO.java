package br.com.fiap.revisaoapi.dto;

public record ProdutoDTO(
        Long id,
        String nome,
        String descricao,
        Double dimensoes
) {
}
