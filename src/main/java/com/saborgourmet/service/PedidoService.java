package com.saborgourmet.service;

import com.saborgourmet.model.DetallePedido;
import com.saborgourmet.model.Insumo;
import com.saborgourmet.model.PlatoInsumo;
import com.saborgourmet.repository.InsumoRepository;
import com.saborgourmet.repository.PlatoInsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PlatoInsumoRepository platoInsumoRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    /**
     * Actualiza el stock de insumos según los platos del pedido.
     * @param detalles lista de DetallePedido (plato + cantidad)
     */
    @Transactional
    public void actualizarStock(List<DetallePedido> detalles) {
        for (DetallePedido detalle : detalles) {
            Long idPlato = detalle.getPlato().getIdPlato();
            double cantidadPedido = detalle.getCantidad();

            // Obtener todos los insumos de ese plato
            List<PlatoInsumo> insumosPlato = platoInsumoRepository.findByPlato_IdPlato(idPlato);

            for (PlatoInsumo pi : insumosPlato) {
                Insumo insumo = pi.getInsumo();
                double nuevoStock = insumo.getStock() - (pi.getCantidadUsada() * cantidadPedido);

                if (nuevoStock < 0) {
                    throw new RuntimeException("Stock insuficiente para el insumo: " + insumo.getNombre());
                }

                insumo.setStock(nuevoStock);
                insumoRepository.save(insumo);
            }
        }
    }
}
