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
@RequestMapping("/plato-insumos")
public class PlatoInsumoController {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private PlatoInsumoRepository platoInsumoRepository;

    @GetMapping("/{idPlato}")
    public String listarPorPlato(@PathVariable Long idPlato, Model model) {
        Plato plato = platoRepository.findById(idPlato).orElse(null);
        List<PlatoInsumo> lista = platoInsumoRepository.findByPlato_IdPlato(idPlato);

        model.addAttribute("plato", plato);
        model.addAttribute("platoInsumos", lista);
        return "platoInsumo/plato-insumo-list";
    }

    @GetMapping("/nuevo/{idPlato}")
    public String nuevo(@PathVariable Long idPlato, Model model) {
        Plato plato = platoRepository.findById(idPlato).orElse(null);

        model.addAttribute("plato", plato);
        model.addAttribute("insumo", new PlatoInsumo());
        model.addAttribute("insumos", insumoRepository.findAll());
        return "platoInsumo/plato-insumo-form";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long idPlato,
                          @RequestParam Long idInsumo,
                          @RequestParam double cantidadUsada) {

        Plato plato = platoRepository.findById(idPlato).orElse(null);
        Insumo insumo = insumoRepository.findById(idInsumo).orElse(null);

        if (plato != null && insumo != null) {
            PlatoInsumo pi = new PlatoInsumo();
            pi.setPlato(plato);
            pi.setInsumo(insumo);
            pi.setCantidadUsada(cantidadUsada);
            platoInsumoRepository.save(pi);
        }

        return "redirect:/plato-insumos/" + idPlato;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        PlatoInsumo pi = platoInsumoRepository.findById(id).orElse(null);
        if (pi != null) {
            Long idPlato = pi.getPlato().getIdPlato();
            platoInsumoRepository.delete(pi);
            return "redirect:/plato-insumos/" + idPlato;
        }
        return "redirect:/platos";
    }
}
