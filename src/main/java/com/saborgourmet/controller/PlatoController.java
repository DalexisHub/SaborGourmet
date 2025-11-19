package com.saborgourmet.controller;

import com.saborgourmet.model.Plato;
import com.saborgourmet.model.Insumo;
import com.saborgourmet.model.PlatoInsumo;
import com.saborgourmet.repository.PlatoRepository;
import com.saborgourmet.repository.InsumoRepository;
import com.saborgourmet.repository.PlatoInsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/platos")
public class PlatoController {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private PlatoInsumoRepository platoInsumoRepository;

    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("platos", platoRepository.findAll());
        return "platos/plato-list";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("plato", new Plato());
        return "platos/plato-form";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Plato plato = platoRepository.findById(id).orElse(null);
        model.addAttribute("plato", plato);
        return "platos/plato-form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("plato") Plato plato) {
        platoRepository.save(plato);
        return "redirect:/platos";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        platoRepository.deleteById(id);
        return "redirect:/platos";
    }

    // DESACTIVAR
    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        Plato p = platoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setEstado(false);
            platoRepository.save(p);
        }
        return "redirect:/platos";
    }

    // ACTIVAR
    @GetMapping("/activar/{id}")
    public String activar(@PathVariable Long id) {
        Plato p = platoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setEstado(true);
            platoRepository.save(p);
        }
        return "redirect:/platos";
    }

    // LISTAR INSUMOS DEL PLATO
    @GetMapping("/{id}/insumos")
    public String listarInsumos(@PathVariable Long id, Model model) {
        Plato plato = platoRepository.findById(id).orElse(null);
        List<PlatoInsumo> lista = platoInsumoRepository.findByPlato(plato);

        model.addAttribute("plato", plato);
        model.addAttribute("platoInsumos", lista);
        return "platoInsumo/plato-insumo-list";
    }
}
