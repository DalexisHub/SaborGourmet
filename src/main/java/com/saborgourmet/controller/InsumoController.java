package com.saborgourmet.controller;

import com.saborgourmet.model.Insumo;
import com.saborgourmet.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/insumos")
public class InsumoController {

    @Autowired
    private InsumoRepository insumoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("insumos", insumoRepository.findAll());
        return "insumos/insumo-list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("insumo", new Insumo());
        return "insumos/insumo-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("insumo") Insumo insumo) {
        insumoRepository.save(insumo);
        return "redirect:/insumos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("insumo", insumoRepository.findById(id).orElse(new Insumo()));
        return "insumos/insumo-form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        insumoRepository.deleteById(id);
        return "redirect:/insumos";
    }
}
