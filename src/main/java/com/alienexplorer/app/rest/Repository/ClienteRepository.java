package com.alienexplorer.app.rest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alienexplorer.app.rest.Model.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
