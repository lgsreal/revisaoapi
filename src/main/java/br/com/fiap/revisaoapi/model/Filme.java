package br.com.fiap.revisaoapi.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "tb_filme")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome")
    private String nome;
    
    @NotBlank(message = "O nome do diretor é obrigatório")
    @Column(name = "nomeDiretor")
    private String nomeDiretor;
    
    @NotBlank(message = "O nome da produtora é obrigatório")
    @Column(name = "produtora")
    private String produtora;
    
    @NotNull(message = "O tempo de duracao e obrigatorio")
    @Column(name = "duracao")
    private float duracao;
    
    @Min(value = 1, message = "O ano do filme deve ser maior que zero")
    @Column(name = "ano")
    private int ano;
    
    public Filme() {
    }

    public Filme(Long id, String nome, String nomeDiretor, String produtora, float duracao, int ano) {
        this.id = id;
        this.nome = nome;
        this.nomeDiretor = nomeDiretor;
        this.produtora = produtora;
        this.duracao = duracao;
        this.ano = ano;
    }

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

    public String getNomeDiretor() {
        return nomeDiretor;
    }

    public void setNomeDiretor(String nomeDiretor) {
        this.nomeDiretor = nomeDiretor;
    }

    public String getProdutora() {
        return produtora;
    }

    public void setProdutora(String produtora) {
        this.produtora = produtora;
    }
    
    public float getDuracao() {
        return duracao;
    }

    public void setDuracao(float duracao) {
        this.duracao = duracao;
    }
    
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
