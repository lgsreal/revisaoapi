package br.com.fiap.revisaoapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
//    @GetMapping("/hello")
//    public String hello() {
//        return "hello"; // o Spring vai renderizar a página templates/hello.html
//    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request) {
        request.setAttribute("nome", "Fulano de Tal");
        return "hello"; // o Spring vai renderizar a página templates/hello.html
    }

    @GetMapping("/hello-model")
    public String hello(Model model) {
        model.addAttribute("nome", "Fulano de Tal");
        return "hello";
    }

    @GetMapping("/hello-mv")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("nome", "Fulano de Tal");
        return mv;
    }

}
