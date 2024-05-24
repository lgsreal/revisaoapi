package br.com.fiap.dto;

public record LivroDto (
		int idLivro,
		String nomeLivro,
		double preco,
		int quantPaginas 
		//foi tirado o atributo anoLancamento da Dto para diferenciar da entidade
		) {

}
