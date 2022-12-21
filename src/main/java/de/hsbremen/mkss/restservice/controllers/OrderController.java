package de.hsbremen.mkss.restservice.controllers;

import java.util.*;

import de.hsbremen.mkss.restservice.entity.LineItem;
import de.hsbremen.mkss.restservice.repository.LineItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final LineItemRepository lineItemRepository;

    OrderController(oorderRepository repository, LineItemRepository lineItemRepository) {
        this.repository = repository;
        this.lineItemRepository = lineItemRepository;
    }

    //Retrieving all orders (including associated line items)
    @GetMapping("/orders")
    List<oorder> all() {
        return (List<oorder>) repository.findAll();
    }

    //Retrieving an order with a given id
    @GetMapping("/orders/{id}")
    oorder one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OorderNotFoundException(id));
    }

    //Retrieving all line items of an order with a given id
    @GetMapping("/order/{id}/item/{itemId}")
    public ResponseEntity<LineItem> getLineItem(@PathVariable long id, @PathVariable long itemId) throws Exception {
        oorder order = repository.findById(id).orElseThrow(Exception::new);
        Set<LineItem> items = order.getItems();
        for (LineItem item : items) {
            if (item.getId() == itemId) {
                return ResponseEntity.ok(item);
            }
        }
        throw new Exception();
    }

    //Creating a new order (with the customer’s name)
    @PostMapping("/neworder")
    public ResponseEntity<oorder> create(@RequestParam String customerName) {
        oorder order = new oorder();
        Date date = new Date();
        order.setDate(date);
        order.setCustomerName(customerName);
        order.setItems(new HashSet<>());
        repository.save(order);

        return ResponseEntity.ok(order);
    }

    //Adding a line item to an order
    @PostMapping("/order/{orderId}/item")
    public ResponseEntity<LineItem> addLineItem(@RequestParam String Product_Name, @RequestParam float Price, @RequestParam int Quantity, @PathVariable long orderId) throws Exception {
        oorder order = repository.findById(orderId).orElseThrow(Exception::new);

            LineItem item = new LineItem();
            item.setProductName(Product_Name);
            item.setPrice(Price);
            item.setQuantity(Quantity);
            item.setOrder(order);

            repository.save(order);
            lineItemRepository.save(item);
            return ResponseEntity.ok(item);

    }

    //Removing a line item from an order
    @DeleteMapping("/order/{id}/item/{itemId}")
    void removeLineItem(@PathVariable long id, @PathVariable long itemId) throws Exception{
        oorder order = repository.findById(id).orElseThrow(Exception::new);
        order.removeItem(itemId);
        repository.save(order);
    }


    //Deleting an order
    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
