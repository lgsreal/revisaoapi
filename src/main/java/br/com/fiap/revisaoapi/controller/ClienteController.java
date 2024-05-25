package br.com.fiap.revisaoapi.controller;

import br.com.fiap.revisaoapi.dto.ClienteDTO;


import br.com.fiap.revisaoapi.model.Cliente;
import br.com.fiap.revisaoapi.service.ClienteService;
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
@RequestMapping(value = "/clientes", produces = {"application/json"})
@Tag(name = "api-cliente")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Retorna todos os clientes em páginas de 3")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll() {
        Page<ClienteDTO> clientesDTO = clienteService.findAll();
        if (clientesDTO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (ClienteDTO clienteDTO : clientesDTO){
                Long id = clienteDTO.getId();
                clienteDTO.add(linkTo(methodOn(ClienteController.class)
                        .findById(id)).withSelfRel());
            }
        }
        return ResponseEntity.ok(clientesDTO);
    }

    @Operation(summary = "Retorna um cliente específico por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado para o id informado", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.findById(id);
        if (clienteDTO == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            clienteDTO.add(linkTo(methodOn(ClienteController.class)
                    .findAll()).withRel("Lista de Clientes"));
        }
        return ResponseEntity.ok(clienteDTO);
    }

    @Operation(summary = "Grava um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente gravado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
    @PostMapping
    public ResponseEntity<Cliente> save(@Valid @RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.save(cliente);
        return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza um cliente com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados", content = {
                    @Content(schema = @Schema())
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        Cliente clienteSalvo = clienteService.update(id, cliente);
        return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Exclui um cliente com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso", content = {
                    @Content(schema = @Schema())
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
