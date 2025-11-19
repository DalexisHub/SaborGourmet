package com.saborgourmet.controller;

import com.saborgourmet.model.DetallePedido;
import com.saborgourmet.model.Pedido;
import com.saborgourmet.model.Cliente;
import com.saborgourmet.model.Mesa;
import com.saborgourmet.repository.ClienteRepository;
import com.saborgourmet.repository.DetallePedidoRepository;
import com.saborgourmet.repository.MesaRepository;
import com.saborgourmet.repository.PedidoRepository;
import com.saborgourmet.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MesaRepository mesaRepository;

    // Listar pedidos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "pedido/pedido-list";
    }

    // Formulario nuevo pedido
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("pedido", new Pedido()); // NECESARIO PARA EL FORMULARIO
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("mesas", mesaRepository.findAll());
        return "pedido/pedido-form";
    }

    // Guardar pedido
    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long idCliente,
                          @RequestParam Long idMesa) {

        Pedido pedido = new Pedido();

        pedido.setFechaHora(LocalDateTime.now());
        pedido.setEstado("pendiente");

        // Cliente opcional
        if (idCliente != null) {
            Cliente cliente = clienteRepository.findById(idCliente).orElse(null);
            pedido.setCliente(cliente);
        }

        // Mesa obligatoria
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        pedido.setMesa(mesa);

        pedidoRepository.save(pedido);

        return "redirect:/pedidos";
    }

    // Cambiar estado
    @GetMapping("/cambiarEstado/{id}/{estado}")
    public String cambiarEstado(@PathVariable Long id, @PathVariable String estado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(estado);
        pedidoRepository.save(pedido);

        // Si se cierra, actualizar stock
        if (estado.equalsIgnoreCase("cerrado")) {
            List<DetallePedido> detalles = detallePedidoRepository.findByPedido_IdPedido(id);
            pedidoService.actualizarStock(detalles);
        }

        return "redirect:/pedidos";
    }

    // Pedidos pendientes (cocina)
    @GetMapping("/pendientes")
    public String pendientes(Model model) {
        List<Pedido> pendientes = pedidoRepository.findByEstado("pendiente");
        model.addAttribute("pedidos", pendientes);
        return "pedido/pedido-list";
    }
}
