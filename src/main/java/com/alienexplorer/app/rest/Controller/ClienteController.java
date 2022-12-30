package com.alienexplorer.app.rest.Controller;

import com.alienexplorer.app.rest.Exception.CustomErrorType;
import com.alienexplorer.app.rest.Model.Cliente;
import com.alienexplorer.app.rest.Repository.ClienteRepository;
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
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/")
    public ResponseEntity<List<Cliente>> listAllUsers() {
        List<Cliente> users = clienteRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<Cliente>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Cliente>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> createUser(@Valid @RequestBody final Cliente user) {
        clienteRepository.save(user);
        return new ResponseEntity<Cliente>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getUserById(@PathVariable("id") final Long id) {
        Optional<Cliente> user = clienteRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Cliente>(
                    new CustomErrorType("User with id "
                            + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(user.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> updateUser(@PathVariable("id") final Long id, @Valid @RequestBody Cliente user) {
        // fetch user based on id and set it to currentUser object of type Cliente
        Optional<Cliente> currentUser = clienteRepository.findById(id);
        if (!currentUser.isPresent()) {
            return new ResponseEntity<Cliente>(
                    new CustomErrorType("Unable to update. User with id "
                            + id + " not found."), HttpStatus.NOT_FOUND);
        }
        // update currentUser object data with user object data
        currentUser.get().setNombre(user.getNombre());
        currentUser.get().setApellido(user.getApellido());
        currentUser.get().setDni(user.getDni());
        // save currentUser object
        clienteRepository.saveAndFlush(currentUser.get());
        //return ResponseEntity object
        return new ResponseEntity<Cliente>(currentUser.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deleteUser(@PathVariable("id") final Long id) {
        Optional<Cliente> user = clienteRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<Cliente>(
                    new CustomErrorType("Unable to delete. User with id "
                            + id + " not found."), HttpStatus.NOT_FOUND);
        }
        clienteRepository.deleteById(id);
        return new ResponseEntity<Cliente>(
                new CustomErrorType("Deleted User with id "
                        + id + "."), HttpStatus.NO_CONTENT);
    }

}
