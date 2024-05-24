package br.com.fiap.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_livro")
public class Livro {

	//atributos com validadores
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_livro")
	@SequenceGenerator(name = "seq_livro", sequenceName = "seq_livro", allocationSize = 1)
	private int idLivro;

	@NotNull(message = "O nome do livro não pode ser nulo")
	@Size(min = 1, max = 80)
	@Column(name = "nome_livro")
	private String nomeLivro;
	
	@NotNull(message = "O preço não pode ser nulo")
    @Positive(message = "O preço deve ter valor positivo")
	@Column(name = "preco")
	private double preco;
	
	@NotNull(message = "A quantidade de páginas não pode ser nula")
    @Min(value = 1, message = "O livro deve ter pelo menos 1 página")
	@Column(name = "quant_pag")
	private int quantPaginas;
	
	@NotNull(message = "O ano de lançamento não pode ser nulo")
    @Min(value = 1440, message = "O ano de lançamento deve ser posterior à invenção da imprensa")
	@Column(name = "ano_lancamento")
	private int anoLancamento;

	//métodos de acesso sem Lombok
	
	public int getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantPaginas() {
		return quantPaginas;
	}

	public void setQuantPaginas(int quantPaginas) {
		this.quantPaginas = quantPaginas;
	}

	public int getAnoLancamento() {
		return anoLancamento;
	}

	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}

}
