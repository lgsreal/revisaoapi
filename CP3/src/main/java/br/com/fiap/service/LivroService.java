package br.com.fiap.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.fiap.dto.LivroDto;
import br.com.fiap.model.Livro;
import br.com.fiap.repository.LivroRepository;

@Service
public class LivroService {

	private LivroRepository livroRepository;
	
	public LivroService(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}
	
    private static final Pageable personalizacao = PageRequest.of(0, 5, Sort.by("nomeLivro").ascending());

    public Page<LivroDto> findAll() {
        return livroRepository.findAll(personalizacao).map(this::toDTO);
    }
    
    public LivroDto findById(int idLivro) {
    	Optional<Livro> livro = livroRepository.findById(idLivro);
    	return livro.map(this::toDTO).orElse(null);
    }
    
    public Livro save(Livro livro) {
    	return livroRepository.save(livro);
    }
    
    public Livro update(int idLivro, Livro livro) {
    	Optional<Livro> livroOptional = livroRepository.findById(idLivro);
    	if (livroOptional.isPresent()) {
    		Livro livroUpdate = livroOptional.get();  
    		livroUpdate.setNomeLivro(livro.getNomeLivro());
    		livroUpdate.setPreco(livro.getPreco());
    		livroUpdate.setQuantPaginas(livro.getQuantPaginas());
    		livroUpdate.setAnoLancamento(livro.getAnoLancamento());
    		livro = livroRepository.save(livroUpdate);
    		return livro;
    	}
    	return null;
    }
    
    public void delete(int idLivro) {
    	Optional<Livro> livro = livroRepository.findById(idLivro);
    	livro.ifPresent(livroRepository::delete);
    }
    
    private LivroDto toDTO(Livro livro) {
        return new LivroDto(
        	livro.getIdLivro(),
            livro.getNomeLivro(),
            livro.getPreco(),
            livro.getQuantPaginas()
        );
    }

}
