package com.maveric.crm.services;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    Customer acceptCustomer(Customer customer);
    List<Customer> getAllCustomers() throws CustomerDetailsNotFoundException;
    Customer getCustomerById(int id) throws CustomerDetailsNotFoundException;
    Customer getCustomerByFirstNameAndEmailId(String firstName,String emailId) throws CustomerDetailsNotFoundException;
    Customer getCustomerByEmailId(String emailId) throws CustomerDetailsNotFoundException;
    List<Customer> getCustomersByFirstName(String firstName) throws CustomerDetailsNotFoundException;
    List<Customer> getCustomersByLastName(String lastName) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomersByAge(int age) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomersByAgeGreaterThanEqual(int age) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomersByAgeGreaterThan(int age) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomersByAgeLesserThanEqual(int age) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomersByAgeLesserThan(int age) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomersByGender(String gender) throws CustomerDetailsNotFoundException;
    String updateCustomerDetails(Customer customer)throws CustomerDetailsNotFoundException;
    String removeCustomer(int id)throws CustomerDetailsNotFoundException;

}
