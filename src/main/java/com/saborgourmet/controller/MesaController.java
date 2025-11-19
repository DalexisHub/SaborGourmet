package com.saborgourmet.controller;

import com.saborgourmet.model.Cliente;
import com.saborgourmet.model.Mesa;
import com.saborgourmet.repository.ClienteRepository;
import com.saborgourmet.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // 🧩 Mostrar listado de mesas
    @GetMapping
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaRepository.findAll());
        return "mesas/mesa-list";
    }

    // 🧩 Formulario de nueva mesa
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesas/mesa-form";
    }

    // 🧩 Guardar o actualizar una mesa
    @PostMapping("/guardar")
    public String guardarMesa(@ModelAttribute Mesa mesa) {
        mesaRepository.save(mesa);
        return "redirect:/mesas";
    }

    // 🧩 Editar mesa
    @GetMapping("/editar/{id}")
    public String editarMesa(@PathVariable Long id, Model model) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa == null) return "redirect:/mesas";
        model.addAttribute("mesa", mesa);
        return "mesas/mesa-form";
    }

    // 🧩 Eliminar mesa
    @GetMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable Long id) {
        mesaRepository.deleteById(id);
        return "redirect:/mesas";
    }

    // 🧩 Asignar mesa a cliente
    @GetMapping("/asignar/{id}")
    public String asignarMesa(@PathVariable Long id, Model model) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa == null) return "redirect:/mesas";

        model.addAttribute("mesa", mesa);
        model.addAttribute("clientes", clienteRepository.findAll());
        return "mesas/mesa-asignar"; // plantilla de asignación
    }

    @PostMapping("/asignar")
    public String guardarAsignacion(@RequestParam Long idMesa, @RequestParam Long idCliente) {
        Mesa mesa = mesaRepository.findById(idMesa).orElse(null);
        Cliente cliente = clienteRepository.findById(idCliente).orElse(null);

        if (mesa != null && cliente != null) {
            mesa.setCliente(cliente);
            mesa.setEstado("ocupada");
            mesaRepository.save(mesa);
        }

        return "redirect:/mesas";
    }

    // 🧩 Liberar mesa
    @GetMapping("/liberar/{id}")
    public String liberarMesa(@PathVariable Long id) {
        Mesa mesa = mesaRepository.findById(id).orElse(null);
        if (mesa != null) {
            mesa.setCliente(null);
            mesa.setEstado("disponible");
            mesaRepository.save(mesa);
        }
        return "redirect:/mesas";
    }
}
