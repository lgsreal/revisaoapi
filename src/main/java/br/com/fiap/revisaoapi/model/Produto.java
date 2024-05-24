package br.com.fiap.revisaoapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;
    @Size(max = 250, message = "O texto da descrição não pode extrapolar 250 caracteres")
    private String descricao;
    @NotNull(message = "O preço do produto é obrigatório")
    @Min(value = 1, message = "O preço mínimo deve ser 1")
    private Double preco;
    @Min(value = 0, message = "As dimensões devem ser no mínimo 0")
    @Max(value = 3, message = "A soma das dimensões deve ser de no máximo 3 (metros)")
    private Double dimensoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(Double dimensoes) {
        this.dimensoes = dimensoes;
    }
}
