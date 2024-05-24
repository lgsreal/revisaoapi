package br.com.fiap.revisaoapi.controller;

import br.com.fiap.revisaoapi.dto.ProdutoDTO;
import br.com.fiap.revisaoapi.model.Produto;
import br.com.fiap.revisaoapi.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/produtos", produces = {"application/json"})
@Tag(name = "api-produto")
public class ProdutoController {
    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Retorna todos os produtos em páginas de 5")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscarProdutos() {
        Page<ProdutoDTO> produtosDTO = produtoService.buscarProdutos();
        if (produtosDTO == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(produtosDTO);
        }
    }

    @Operation(summary = "Retorna um produto específico por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado para o id informado", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produtoDTO = produtoService.buscarProdutoPorId(id);
        if (produtoDTO == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(produtoDTO);
        }
    }

    @Operation(summary = "Grava um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
    @PostMapping
    public ResponseEntity<Produto> gravarProduto(@Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @Operation(summary = "Atualiza um produto com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.atualizarProduto(id, produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    @Operation(summary = "Exclui um produto com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso", content = {
                    @Content(schema = @Schema())
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
