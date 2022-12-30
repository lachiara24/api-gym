package com.alienexplorer.app.rest.Repository;

import com.alienexplorer.app.rest.Model.Pago;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByClienteId(Long id);
    List<Pago> findAllByClienteDni(String dni);

    @Transactional
    void deleteByClienteId(Long id);
}
