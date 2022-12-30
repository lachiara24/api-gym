package com.alienexplorer.app.rest.Controller;

import com.alienexplorer.app.rest.Exception.CustomErrorType;
import com.alienexplorer.app.rest.Exception.ResourceNotFoundException;
import com.alienexplorer.app.rest.Model.Cliente;
import com.alienexplorer.app.rest.Model.Pago;
import com.alienexplorer.app.rest.Repository.ClienteRepository;
import com.alienexplorer.app.rest.Repository.PagoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class PagoController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    // Listado de pagos por id de cliente
    @GetMapping("/{clienteId}/pagos")
    public ResponseEntity<List<Pago>> listAllPagosByCliente(@PathVariable("clienteId") final Long id) {
        Optional<Cliente> user = clienteRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<List<Pago>>(HttpStatus.NOT_FOUND);
        }
        List<Pago> pagos = pagoRepository.findByClienteId(id);
        if (pagos.isEmpty()) {
            return new ResponseEntity<List<Pago>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Pago>>(pagos, HttpStatus.OK);
    }

    // Obtener ultimo pago de cliente
    @GetMapping("/{clienteId}/pagos/last")
    public ResponseEntity<Pago> listLastPagoByCliente(@PathVariable("clienteId") final Long id) {
        Optional<Cliente> user = clienteRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        List<Pago> pagos = pagoRepository.findByClienteId(id);
        if (pagos.isEmpty()) {
            return new ResponseEntity<Pago>(HttpStatus.NO_CONTENT);
        }
        Pago ultimoPago = pagos.get(pagos.size()-1);
        return new ResponseEntity<Pago>(ultimoPago, HttpStatus.OK);
    }

    // Obtener pago por id
    @GetMapping("{clienteId}/pagos/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable("id") final Long id,
                                            @PathVariable("clienteId") final Long clienteId) {
        Optional<Pago> pago = pagoRepository.findByIdAndClienteId(id, clienteId);
        if (!pago.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pago>(pago.get(), HttpStatus.OK);
    }


    // Guardar pago de un cliente
    @PostMapping(value = "/{clienteId}/pagos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pago> createPago(@PathVariable("clienteId") final Long id, @RequestBody final Pago pago) {
        // busco si existe el id del cliente
        Optional<Cliente> currentUser = clienteRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        Cliente cliente = clienteRepository.findById(id).get();
        pago.setCliente(cliente);
        pagoRepository.save(pago);
        return new ResponseEntity<Pago>(pago, HttpStatus.CREATED);
    }

    // editar pago
    @PutMapping(value = "/{clienteId}/pagos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pago> updateUser(@PathVariable("clienteId") final Long clienteId,
                                              @PathVariable("id") final Long id,
                                              @RequestBody Pago pago) {
        // busco si existe el id del cliente
        Optional<Cliente> currentUser = clienteRepository.findById(clienteId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del pago
        Optional<Pago> currentPago = pagoRepository.findById(id);
        if (!currentPago.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        // actualizo pago
        currentPago.get().setFechaPago(pago.getFechaPago());
        currentPago.get().setFechaVenc(pago.getFechaVenc());
        // guardo pago actualizado
        pagoRepository.saveAndFlush(currentPago.get());
        //return ResponseEntity object
        return new ResponseEntity<Pago>(currentPago.get(), HttpStatus.OK);
    }

    // borrar pago
    @DeleteMapping("/{clienteId}/pagos/{id}")
    public ResponseEntity<Pago> deleteUser(@PathVariable("id") final Long id,
                                              @PathVariable("clienteId") final Long clienteId) {
        // busco si existe el id del cliente
        Optional<Cliente> currentUser = clienteRepository.findById(clienteId);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        // busco si existe el id del pago
        Optional<Pago> currentPago = pagoRepository.findById(id);
        if (!currentPago.isPresent()) {
            return new ResponseEntity<Pago>(HttpStatus.NOT_FOUND);
        }
        pagoRepository.deleteById(id);
        return new ResponseEntity<Pago>(HttpStatus.NO_CONTENT);
    }

}
