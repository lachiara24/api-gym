package com.alienexplorer.app.rest.Controller;

import com.alienexplorer.app.rest.Model.Cliente;
import com.alienexplorer.app.rest.Model.Pago;
import com.alienexplorer.app.rest.Repository.ClienteRepository;
import com.alienexplorer.app.rest.Repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class PagoController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @GetMapping("/clientes/{clienteId}/pagos")
    public List<Pago> getAllPagosByClienteId(@PathVariable Long clienteId) {
        return pagoRepository.findByClienteId(clienteId);
    }

    @GetMapping("/pagos/{id}")
    public Pago getPagosById(@PathVariable Long id){
        return pagoRepository.findById(id).get();
    }


    @PostMapping("/clientes/{clienteId}/pagos")
    public String savePago(@RequestBody Pago pago, @PathVariable Long clienteId){
        Cliente cliente = clienteRepository.findById(clienteId).get();
        pago.setCliente(cliente);
        pagoRepository.save(pago);
        return "Pago guardado";
    }

    @PutMapping("/pagos/{id}")
    public String updatePago(@RequestBody Pago pago, @PathVariable long id){
        Pago updatedPago = pagoRepository.findById(id).get();
        updatedPago.setFechaPago(pago.getFechaPago());
        updatedPago.setFechaVenc(pago.getFechaVenc());
        pagoRepository.save(updatedPago);
        return "Pago actualizado";
    }

    @DeleteMapping("pagos/{id}")
    public String deletePago(@PathVariable long id){
        Pago pago = pagoRepository.findById(id).get();
        pagoRepository.delete(pago);
        return "Pago eliminado";
    }

}
