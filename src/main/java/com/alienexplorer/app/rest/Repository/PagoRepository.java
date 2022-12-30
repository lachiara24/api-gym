package com.alienexplorer.app.rest.Repository;

import com.alienexplorer.app.rest.Model.Pago;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByClienteId(Long clienteId);
    Optional<Pago> findByIdAndClienteId(Long id, Long ClienteId);

    Pago findTopByClienteId(Long clienteId);
}
