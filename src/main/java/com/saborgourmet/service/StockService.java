package com.saborgourmet.service;

import com.saborgourmet.model.*;
import com.saborgourmet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private PlatoInsumoRepository platoInsumoRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    // Actualiza el stock de insumos según los platos del pedido
    public void actualizarStockPorPedido(Pedido pedido) {
        if (pedido.getDetalles() == null) return;

        for (DetallePedido detalle : pedido.getDetalles()) {
            Long idPlato = detalle.getPlato().getIdPlato();
            int cantidadPlatos = detalle.getCantidad();

            // Obtener insumos usados por el plato
            List<PlatoInsumo> insumosPlato = platoInsumoRepository.findByPlato_IdPlato(idPlato);

            for (PlatoInsumo pi : insumosPlato) {
                Insumo insumo = pi.getInsumo();
                double cantidadTotalUsada = pi.getCantidadUsada() * cantidadPlatos;

                // Restar stock
                double nuevoStock = insumo.getStock() - cantidadTotalUsada;
                insumo.setStock(Math.max(nuevoStock, 0));

                insumoRepository.save(insumo);
            }
        }
    }
}
