package br.com.fiap.revisaoapi.controller;

import br.com.fiap.revisaoapi.model.Cliente;
import br.com.fiap.revisaoapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteViewController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteViewController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/listaClientes")
    public ModelAndView listaClientes(){
        // buscar uma lista de clientes
        List<Cliente> listaClientes = clienteService.findAllView();
        ModelAndView mv = new ModelAndView("clientes");
        // adicionar a lista de clientes no ModelAndView
        mv.addObject("listaClientes", listaClientes);
        return mv;
    }
}
