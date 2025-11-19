package com.saborgourmet.service;

import com.saborgourmet.model.Insumo;
import com.saborgourmet.model.Plato;
import com.saborgourmet.model.PlatoInsumo;
import com.saborgourmet.repository.InsumoRepository;
import com.saborgourmet.repository.PlatoInsumoRepository;
import com.saborgourmet.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private PlatoInsumoRepository platoInsumoRepository;

    // Crear o actualizar plato
    public Plato guardarPlato(Plato plato) {
        return platoRepository.save(plato);
    }

    // Eliminar plato
    public void eliminarPlato(Long idPlato) {
        platoRepository.deleteById(idPlato);
    }

    // Activar o desactivar plato
    public Plato cambiarEstadoPlato(Long idPlato, boolean estado) {
        Plato plato = platoRepository.findById(idPlato).orElseThrow(() -> new RuntimeException("Plato no encontrado"));
        plato.setEstado(estado);
        return platoRepository.save(plato);
    }

    // Asociar insumo a plato
    public PlatoInsumo asociarInsumoAPlato(Long idPlato, Long idInsumo, double cantidad) {
        Plato plato = platoRepository.findById(idPlato).orElseThrow(() -> new RuntimeException("Plato no encontrado"));
        Insumo insumo = insumoRepository.findById(idInsumo).orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        PlatoInsumo pi = new PlatoInsumo();
        pi.setPlato(plato);
        pi.setInsumo(insumo);
        pi.setCantidadUsada(cantidad);

        return platoInsumoRepository.save(pi);
    }

    // Listar insumos asociados a un plato
    public List<PlatoInsumo> listarInsumosDePlato(Long idPlato) {
        return platoInsumoRepository.findByPlato_IdPlato(idPlato);
    }

    // Actualizar stock de insumos al procesar pedido
    @Transactional
    public void procesarPedido(List<PlatoInsumo> insumosPedido, int cantidadPedido) {
        for (PlatoInsumo pi : insumosPedido) {
            Insumo insumo = pi.getInsumo();
            double nuevoStock = insumo.getStock() - (pi.getCantidadUsada() * cantidadPedido);
            if (nuevoStock < 0) {
                throw new RuntimeException("Stock insuficiente para insumo: " + insumo.getNombre());
            }
            insumo.setStock(nuevoStock);
            insumoRepository.save(insumo);
        }
    }
}
