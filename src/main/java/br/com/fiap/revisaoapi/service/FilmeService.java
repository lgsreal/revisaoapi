package br.com.fiap.revisaoapi.service;

import br.com.fiap.revisaoapi.dto.FilmeDTO;
import br.com.fiap.revisaoapi.model.Filme;
import br.com.fiap.revisaoapi.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmeService {
    private final FilmeRepository filmeRepository;
    private static final Pageable customPageable = PageRequest.of(0, 3, Sort.by("nome").ascending());
    @Autowired
    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public Page<FilmeDTO> findAll() {
        return filmeRepository.findAll(customPageable).map(this::toDTO);
    }

    public FilmeDTO findById(Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.map(this::toDTO).orElse(null);
    }

    public Filme save(Filme filme) {
        return filmeRepository.save(filme);
    }

    public Filme update(Long id, Filme filme) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);
        if (filmeOptional.isPresent()) {
            Filme filmeUpdate = filmeOptional.get();
            filmeUpdate.setNome(filme.getNome());
            filmeUpdate.setNomeDiretor(filme.getNomeDiretor());
            filmeUpdate.setDuracao(filme.getDuracao());
            filmeUpdate.setProdutora(filme.getProdutora());
            filmeUpdate.setAno(filme.getAno());
            filme = filmeRepository.save(filmeUpdate);
            return filme;
        }
        return null;
    }

    public void delete(Long id) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);
        filmeOptional.ifPresent(filmeRepository::delete);
    }

    private FilmeDTO toDTO(Filme filme) {
        FilmeDTO filmeDTO = new FilmeDTO();
        filmeDTO.setId(filme.getId());
        filmeDTO.setNome(filme.getNome());
        filmeDTO.setNomeDiretor(filme.getNomeDiretor());
        filmeDTO.setDuracao(filme.getDuracao());
        filmeDTO.setProdutora(filme.getProdutora());
        filmeDTO.setAno(filme.getAno());
        return filmeDTO;
    }
}
