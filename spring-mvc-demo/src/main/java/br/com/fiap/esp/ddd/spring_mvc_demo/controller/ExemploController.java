package br.com.fiap.esp.ddd.spring_mvc_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExemploController {

    @GetMapping("/exemplo")
    public String exemplo() {
        return "exemplo";
    }
}
