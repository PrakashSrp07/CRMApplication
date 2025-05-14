package com.maveric.crm.controllers;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/v1/customer",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> acceptCustomer(@RequestBody Customer customer)
    {
        return new ResponseEntity<>(customerService.acceptCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/v1/customer/allCustomers")
    public ResponseEntity<List<Customer>> getCustomers() throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }


    @GetMapping("/v1/customer/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomerById(id),HttpStatus.OK);
    }

    @GetMapping("/v1/customer/firstName/{firstName}/emailId/{emailId}")
    public ResponseEntity<Customer> getCustomersByFirstNameAndEmailId(@PathVariable String firstName,@PathVariable String emailId) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomerByFirstNameAndEmailId(firstName, emailId),HttpStatus.OK);
    }

    @GetMapping("v1/customer/emailId/{emailId}")
    public ResponseEntity<Customer> getCustomerByEmailId(@PathVariable String emailId) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomerByEmailId(emailId),HttpStatus.OK);
    }

    @GetMapping("v1/customer/firstName/{firstName}")
    public ResponseEntity<List<Customer>> getCustomersByFirstName(@PathVariable String firstName) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomersByFirstName(firstName),HttpStatus.OK);
    }

    @GetMapping("v1/customer/lastName/{lastName}")
    public ResponseEntity<List<Customer>> getCustomersByLastName(@PathVariable String lastName) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomersByLastName(lastName),HttpStatus.OK);
    }

    @GetMapping("v1/customer/age/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAge(@PathVariable int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAge(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/agegreaterthanequal/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeGreaterThanEqual(@PathVariable int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeGreaterThanEqual(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/agegreaterthan/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeGreaterThan(@PathVariable int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeGreaterThan(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/agelessthanequal/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeLessThanEqual(@PathVariable int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeLesserThanEqual(age),HttpStatus.OK);
    }
    @GetMapping("v1/customer/agelessthan/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeLessThan(@PathVariable int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeLesserThan(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/gender/{gender}")
    public ResponseEntity<List<Customer>> getCustomersByGender(@PathVariable String gender) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByGender(gender),HttpStatus.OK);
    }

    @PutMapping("v1/customer/update")
    public ResponseEntity<String> updateCustomerDetails(@RequestBody Customer customer) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.updateCustomerDetails(customer),HttpStatus.OK);
    }

    @DeleteMapping("v1/customer/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.removeCustomer(id),HttpStatus.OK);
    }

}
