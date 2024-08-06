package br.com.fiap.revisaoapi.service;

import br.com.fiap.revisaoapi.dto.ClienteDTO;
import br.com.fiap.revisaoapi.model.Cliente;
import br.com.fiap.revisaoapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private static final Pageable customPageable = PageRequest.of(0, 3, Sort.by("nome").ascending());
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<ClienteDTO> findAll() {
        return clienteRepository.findAll(customPageable).map(this::toDTO);
    }

    public List<Cliente> findAllView() {
        return clienteRepository.findAll();
    }

    public ClienteDTO findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(this::toDTO).orElse(null);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteUpdate = clienteOptional.get();
            clienteUpdate.setNome(cliente.getNome());
            clienteUpdate.setEmail(cliente.getEmail());
            clienteUpdate.setSenha(cliente.getSenha());
            cliente = clienteRepository.save(clienteUpdate);
            return cliente;
        }
        return null;
    }

    public void delete(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        clienteOptional.ifPresent(clienteRepository::delete);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setEmail(cliente.getEmail());
        return clienteDTO;
    }
}
