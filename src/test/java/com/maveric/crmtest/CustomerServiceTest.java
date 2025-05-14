package com.maveric.crmtest;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.services.CustomerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = com.maveric.crm.CRMApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;


    @Test
    @Order(1)
    void testCreateCustomers() {
        customerService.acceptCustomer(new Customer("Prakash", "S", "prakash@gmail.com", "Male", 23));
        customerService.acceptCustomer(new Customer("Kishan", "N", "kishan@gmail.com", "Male", 18));
        customerService.acceptCustomer(new Customer("Prethe", "T", "prethe@gmail.com", "Female", 19));
    }


    @Test
    @Order(2)
    void testGetCustomerById_Positive() throws CustomerDetailsNotFoundException {
        Customer customer = customerService.getCustomerById(1);
        assertEquals("Prakash", customer.getFirstName());
    }


    @Test
    @Order(3)
    void testGetCustomerById_Negative() {
        Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerById(99));
    }

    @Test
    @Order(4)
    void testGetCustomersByFirstName_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> list = customerService.getCustomersByFirstName("Prakash");
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(5)
    void testGetCustomersByFirstName_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomersByFirstName("xyz"));
    }

    @Test
    @Order(6)
    void testGetCustomerByFirstNameAndEmailId_Positive() throws CustomerDetailsNotFoundException {
        Customer customer = customerService.getCustomerByFirstNameAndEmailId("Prakash", "prakash@gmail.com");
        assertNotNull(customer);
    }

    @Test
    @Order(7)
    void testGetCustomerByFirstNameAndEmailId_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerByFirstNameAndEmailId("Invalid", "wrong@example.com"));
    }

    @Test
    @Order(8)
    void testGetAllCustomersByAgeGreaterThanEqual_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> list = customerService.getAllCustomersByAgeGreaterThanEqual(18);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(9)
    void testGetAllCustomersByAgeGreaterThanEqual_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeGreaterThanEqual(99));
    }

    @Test
    @Order(10)
    void testGetAllCustomersByAgeLesserThan_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerService.getAllCustomersByAgeLesserThan(30);
        assertFalse(customers.isEmpty());
    }

    @Test
    @Order(11)
    void testGetAllCustomersByAgeLesserThan_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeLesserThan(10));
    }

    @Test
    @Order(12)
    void testGetAllCustomersByAgeLesserThanEqual_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerService.getAllCustomersByAgeLesserThanEqual(25);
        assertFalse(customers.isEmpty());
    }

    @Test
    @Order(13)
    void testGetAllCustomersByAgeLesserThanEqual_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeLesserThanEqual(15));
    }

    @Test
    @Order(14)
    void testUpdateCustomerDetails_Positive() throws CustomerDetailsNotFoundException {
        String response = customerService.updateCustomerDetails( new Customer(3,"Prethe","T","prethet@gmail.com","Female",19));
        assertEquals("Successfully Updated", response);

        Customer customer = customerService.getCustomerById(3);
        assertEquals("prethet@gmail.com", customer.getEmailId());
    }

    @Test
    @Order(15)
    void testUpdateCustomerDetails_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.updateCustomerDetails(new Customer(999,"Prethe","T","prethet@gmail.com","Female",19)));
    }

    @Test
    @Order(16)
    void testRemoveCustomer_Positive() throws CustomerDetailsNotFoundException {
        assertEquals("Successfully Deleted", customerService.removeCustomer(1));
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerById(1));;
    }

    @Test
    @Order(17)
    void testRemoveCustomer_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.removeCustomer(999));
    }
}
