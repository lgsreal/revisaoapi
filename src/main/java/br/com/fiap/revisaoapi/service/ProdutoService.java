package br.com.fiap.revisaoapi.service;

import br.com.fiap.revisaoapi.dto.ProdutoDTO;
import br.com.fiap.revisaoapi.model.Produto;
import br.com.fiap.revisaoapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private static final Pageable paginacaoPersonalizada = PageRequest.of(0, 5, Sort.by("nome").ascending());

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<ProdutoDTO> buscarProdutos() {
        return produtoRepository.findAll(paginacaoPersonalizada).map(this::toDTO);
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public Produto salvarProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto){
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produtoAtual = produtoOptional.get();
            produtoAtual.setNome(produto.getNome());
            produtoAtual.setDescricao(produto.getDescricao());
            produtoAtual.setPreco(produto.getPreco());
            produtoAtual.setDimensoes(produto.getDimensoes());
            return produtoRepository.save(produtoAtual);
        }
        return null;
    }

    public void deletarProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        produtoOptional.ifPresent(produtoRepository::delete);
    }

    private ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getDimensoes()
        );
    }

}
