package br.com.fiap.revisaoapi.controller;

import br.com.fiap.revisaoapi.dto.FilmeDTO;

import br.com.fiap.revisaoapi.model.Filme;
import br.com.fiap.revisaoapi.service.FilmeService;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/filmes", produces = {"application/json"})
@Tag(name = "api-filme")
public class FilmeController {
    private final FilmeService filmeService;

    @Autowired
    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @Operation(summary = "Retorna todos os filmes em páginas de 3")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum filme encontrado", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping
    public ResponseEntity<Page<FilmeDTO>> findAll() {
        Page<FilmeDTO> filmesDTO = filmeService.findAll();
        if (filmesDTO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (FilmeDTO filmeDTO : filmesDTO){
                Long id = filmeDTO.getId();
                filmeDTO.add(linkTo(methodOn(FilmeController.class)
                        .findById(id)).withSelfRel());
            }
        }
        return ResponseEntity.ok(filmesDTO);
    }

    @Operation(summary = "Retorna um filme específico por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum filme encontrado para o id informado", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> findById(@PathVariable Long id) {
        FilmeDTO filmeDTO = filmeService.findById(id);
        if (filmeDTO == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            filmeDTO.add(linkTo(methodOn(FilmeController.class)
                    .findAll()).withRel("Lista de Filmes"));
        }
        return ResponseEntity.ok(filmeDTO);
    }

    @Operation(summary = "Grava um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
    @PostMapping
    public ResponseEntity<Filme> save(@Valid @RequestBody Filme filme) {
        Filme filmeSalvo = filmeService.save(filme);
        return new ResponseEntity<>(filmeSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um filme com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Filme> update(@PathVariable Long id, @Valid @RequestBody Filme filme) {
        Filme filmeSalvo = filmeService.update(id, filme);
        return new ResponseEntity<>(filmeSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um filme com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Filme excluído com sucesso", content = {
                    @Content(schema = @Schema())
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
