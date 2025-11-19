package com.saborgourmet.repository;

import com.saborgourmet.model.PlatoInsumo;
import com.saborgourmet.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlatoInsumoRepository extends JpaRepository<PlatoInsumo, Long> {

    // Buscar por id del plato (para tu primer controller)
    List<PlatoInsumo> findByPlato_IdPlato(Long idPlato);

    // Buscar pasando el objeto plato (para tu segundo controller)
    List<PlatoInsumo> findByPlato(Plato plato);
}
