package com.maveric.crm.services;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer acceptCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerDetailsNotFoundException {
        List<Customer> allCustomers=customerRepository.findAll();
        if(allCustomers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found");
        return allCustomers;
    }

    @Override
    public Customer getCustomerById(int id) throws CustomerDetailsNotFoundException {
        Optional<Customer> customer=customerRepository.findById(id);
        if(customer.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for ID: "+id);
        return customer.get();
    }

    @Override
    public Customer getCustomerByFirstNameAndEmailId(String firstName, String emailId) throws CustomerDetailsNotFoundException {
       Customer customer=customerRepository.findByFirstNameAndEmailIdIgnoreCase(firstName, emailId);
       if(customer==null)
           throw new CustomerDetailsNotFoundException("No Customer Details Found For The Name: "+firstName+" and EmailId: "+emailId);
       return customer;
    }

    @Override
    public List<Customer> getCustomersByFirstName(String firstName) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByFirstNameIgnoreCase(firstName);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for FirstName: "+firstName);
        return customers;
    }

    @Override
    public List<Customer> getCustomersByLastName(String lastName) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByLastNameIgnoreCase(lastName);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for LastName: "+lastName);
        return customers;
    }

    @Override
    public Customer getCustomerByEmailId(String emailId) throws CustomerDetailsNotFoundException {
        Customer customer=customerRepository.findByEmailIdIgnoreCase(emailId);
        if(customer==null)
            throw new CustomerDetailsNotFoundException("No Customer Details Found for emailId: "+emailId);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomersByAge(int age) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByAge(age);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for Age: "+age);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByAgeGreaterThanEqual(int age) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByAgeGreaterThanEqual(age);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for Age Greater Than: "+age);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByAgeGreaterThan(int age) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByAgeGreaterThan(age);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for Age Greater Than: "+age);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByAgeLesserThanEqual(int age) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByAgeLessThanEqual(age);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for Age Lesser Than: "+age);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByAgeLesserThan(int age) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByAgeLessThan(age);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for Age Lesser Than: "+age);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomersByGender(String gender) throws CustomerDetailsNotFoundException {
        List<Customer> customers=customerRepository.findByGenderIgnoreCase(gender);
        if(customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found for Gender: "+gender);
        return customers;
    }

    @Override
    public String updateCustomerDetails(Customer customer) throws CustomerDetailsNotFoundException {
        this.getCustomerById(customer.getId());
        customerRepository.save(customer);
        return "Successfully Updated";
    }

    @Override
    public String removeCustomer(int id) throws CustomerDetailsNotFoundException {
        this.getCustomerById(id);
        customerRepository.deleteById(id);
        return "Successfully Deleted";
    }
}
