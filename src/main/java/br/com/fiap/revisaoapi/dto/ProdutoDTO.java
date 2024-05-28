package br.com.fiap.revisaoapi.dto;

import org.springframework.hateoas.Link;

public record ProdutoDTO(
        Long id,
        String nome,
        String descricao,
        Double dimensoes,
        Link link
) {}
