package de.hsbremen.mkss.restservice.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.hsbremen.mkss.restservice.repository.oorderRepository;
import de.hsbremen.mkss.restservice.entity.oorder;
import de.hsbremen.mkss.restservice.entity.OorderNotFoundException;

@RestController
public class OrderController {
    @GetMapping("/health")
    String health() {
        return "OK";
    }

    private final oorderRepository repository;

    OrderController(oorderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders")
    List<oorder> all() {
        return (List<oorder>) repository.findAll();
    }

    @PostMapping("/neworder")
    oorder newEmployee(@RequestBody oorder newOorder) {
        return repository.save(newOorder);
    }

    @GetMapping("/orders/{id}")
    oorder one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OorderNotFoundException(id));
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
