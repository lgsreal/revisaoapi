package br.com.fiap.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.dto.LivroDto;
import br.com.fiap.model.Livro;
import br.com.fiap.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livro")
public class LivroController {

	private LivroService livroService;
	
	public LivroController(LivroService livroService) {
		this.livroService = livroService;
	}
	
	@Operation(summary = "Retorna todos os livros em páginas de 5")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum livro encontrado", content = {
                    @Content(schema = @Schema())
            })
    })
	
	@GetMapping
    public ResponseEntity<Page<LivroDto>> findAll() {
        Page<LivroDto> livrosDTO = livroService.findAll();
        if (livrosDTO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(livrosDTO);
    }
	
	@Operation(summary = "Retorna um livro específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum livro encontrado para o id informado", content = {
                    @Content(schema = @Schema())
            })
    })
	
	@GetMapping("/{idLivro}")
    public ResponseEntity<LivroDto> findById(@PathVariable int idLivro) {
		LivroDto livroDto = livroService.findById(idLivro);
        if (livroDto == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(livroDto);
    }
	
	@Operation(summary = "Grava um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
	
	@PostMapping
    public ResponseEntity<Livro> save(@Valid @RequestBody Livro livro) {
		Livro livroSalvo = livroService.save(livro);
        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }
	
	@Operation(summary = "Atualiza um livro baseado no seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
	
	@PutMapping("/{idLivro}")
    public ResponseEntity<Livro> update(@PathVariable int idLivro, @Valid @RequestBody Livro livro) {
		Livro livroSalvo = livroService.update(idLivro, livro);
        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }
	
	@Operation(summary = "Exclui um livro com base no seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso", content = {
                    @Content(schema = @Schema())
            })
    })
	
	@DeleteMapping("/{idLivro}")
    public ResponseEntity<Void> delete(@PathVariable int idLivro) {
		livroService.delete(idLivro);
        return ResponseEntity.noContent().build();
    }
	
}
