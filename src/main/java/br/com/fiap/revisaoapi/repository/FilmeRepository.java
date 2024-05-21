package br.com.fiap.revisaoapi.repository;
import br.com.fiap.revisaoapi.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
