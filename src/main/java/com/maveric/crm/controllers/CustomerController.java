package com.maveric.crm.controllers;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.services.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    public ResponseEntity<Customer> acceptCustomer(@Valid @RequestBody Customer customer)
    {
        return new ResponseEntity<>(customerService.acceptCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/v1/customer/allCustomers")
    public ResponseEntity<List<Customer>> getCustomers() throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
    }


    @GetMapping("/v1/customer/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable @Min(value = 1, message = "ID must be greater than 0") int id) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomerById(id),HttpStatus.OK);
    }

    @GetMapping("/v1/customer/firstName/{firstName}/emailId/{emailId}")
    public ResponseEntity<Customer> getCustomersByFirstNameAndEmailId(@PathVariable @NotBlank(message = "First name is not entered")
                                                                          @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
                                                                          @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters") String firstName, @PathVariable @Email(message = "Invalid email format") String emailId) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomerByFirstNameAndEmailId(firstName, emailId),HttpStatus.OK);
    }

    @GetMapping("v1/customer/emailId/{emailId}")
    public ResponseEntity<Customer> getCustomerByEmailId(@PathVariable @NotBlank(message = "EmailId is not entered") @Email(message = "Invalid email format")String emailId) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomerByEmailId(emailId),HttpStatus.OK);
    }

    @GetMapping("v1/customer/firstName/{firstName}")
    public ResponseEntity<List<Customer>> getCustomersByFirstName(@PathVariable @NotBlank(message = "First name is not entered")
                                                                      @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
                                                                      @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters") String firstName) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomersByFirstName(firstName),HttpStatus.OK);
    }

    @GetMapping("v1/customer/lastName/{lastName}")
    public ResponseEntity<List<Customer>> getCustomersByLastName(@PathVariable @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters") String lastName) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getCustomersByLastName(lastName),HttpStatus.OK);
    }

    @GetMapping("v1/customer/age/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAge(@PathVariable
                                                                @Min(value = 18, message = "Age must be at least 18")
                                                                @Max(value = 100, message = "Age must not exceed 100")
                                                                @Positive(message = "Age must be a positive number") int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAge(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/agegreaterthanequal/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeGreaterThanEqual(@PathVariable
                                                                                @Min(value = 18, message = "Age must be at least 18")
                                                                                @Max(value = 100, message = "Age must not exceed 100")
                                                                                @Positive(message = "Age must be a positive number") int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeGreaterThanEqual(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/agegreaterthan/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeGreaterThan(@PathVariable
                                                                           @Min(value = 18, message = "Age must be at least 18")
                                                                           @Max(value = 100, message = "Age must not exceed 100")
                                                                           @Positive(message = "Age must be a positive number")int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeGreaterThan(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/agelessthanequal/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeLessThanEqual(@PathVariable
                                                                             @Min(value = 18, message = "Age must be at least 18")
                                                                             @Max(value = 100, message = "Age must not exceed 100")
                                                                             @Positive(message = "Age must be a positive number") int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeLesserThanEqual(age),HttpStatus.OK);
    }
    @GetMapping("v1/customer/agelessthan/{age}")
    public ResponseEntity<List<Customer>> getCustomersByAgeLessThan(@PathVariable
                                                                        @Min(value = 18, message = "Age must be at least 18")
                                                                        @Max(value = 100, message = "Age must not exceed 100")
                                                                        @Positive(message = "Age must be a positive number")int age) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByAgeLesserThan(age),HttpStatus.OK);
    }

    @GetMapping("v1/customer/gender/{gender}")
    public ResponseEntity<List<Customer>> getCustomersByGender(@PathVariable @NotBlank(message = "Gender is not entered")
                                                                   @NotBlank(message = "Gender is required")
                                                                   @Pattern(regexp = "Male|Female|male|female", message = "Gender must be Male or Female")String gender) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.getAllCustomersByGender(gender),HttpStatus.OK);
    }

    @PutMapping("v1/customer/update")
    public ResponseEntity<String> updateCustomerDetails(@Valid @RequestBody Customer customer) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.updateCustomerDetails(customer),HttpStatus.OK);
    }

    @DeleteMapping("v1/customer/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable  @Min(value = 1, message = "ID must be greater than 0")int id) throws CustomerDetailsNotFoundException
    {
        return new ResponseEntity<>(customerService.removeCustomer(id),HttpStatus.OK);
    }

}
