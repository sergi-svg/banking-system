package dev.svg.controllers;

import dev.svg.model.Customer;
import dev.svg.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/iron-bank")
public class CustomerController implements ResourceController <Customer> {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllResources() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/customer")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Customer> getResourceByParams(@RequestParam HashMap<String, String> params) {
        Optional<Customer> customer = null;
        if (params.containsKey("idCard")) customer = customerService.getCustomerByIdCard(params.get("idCard"));
        else if (params.containsKey("email")) customer = customerService.getCustomerByEmail(params.get("email"));
        else if (params.containsKey("phone")) customer = customerService.getCustomerByPhone(params.get("phone"));

        return customer;
    }

    @DeleteMapping("/customers/{idCard}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResource(@PathVariable("idCard") String idCard) {
        customerService.deleteByIdCard(idCard);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createResource(@RequestBody @Valid Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Customer updateResource(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }


}
