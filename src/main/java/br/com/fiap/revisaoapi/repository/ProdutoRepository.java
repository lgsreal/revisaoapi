package br.com.fiap.revisaoapi.repository;

import br.com.fiap.revisaoapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
