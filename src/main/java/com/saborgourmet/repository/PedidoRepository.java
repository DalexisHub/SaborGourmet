package com.saborgourmet.repository;

import com.saborgourmet.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(String estado); // para listar pendientes, en preparación, etc.
}
