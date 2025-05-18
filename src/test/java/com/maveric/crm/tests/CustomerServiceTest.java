package com.maveric.crm.tests;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.repositories.CustomerRepository;
import com.maveric.crm.services.CustomerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.maveric.crm.CRMApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest {

    @MockitoBean
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @BeforeEach
    void setup()
    {
        Customer customer1=new Customer(1,"Prakash", "S", "prakash@gmail.com", "Male", 23);
        Customer customer2=new Customer(2,"Kishan", "N", "kishan@gmail.com", "Male", 18);
        Customer customer3=new Customer(3,"Prethe", "T", "prethe@gmail.com", "Female", 19);

        //save
        Mockito.when(customerRepository.save(new Customer("Prakash", "S", "prakash@gmail.com", "Male", 23))).thenReturn(customer1);
        Mockito.when(customerRepository.save(new Customer("Kishan", "N", "kishan@gmail.com", "Male", 18))).thenReturn(customer2);
        Mockito.when(customerRepository.save(new Customer("Prethe", "T", "prethe@gmail.com", "Female", 19))).thenReturn(customer3);

        //findById
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));
        Mockito.when(customerRepository.findById(2)).thenReturn(Optional.of(customer2));
        Mockito.when(customerRepository.findById(3)).thenReturn(Optional.of(customer3));
        Mockito.when(customerRepository.findById(99)).thenReturn(Optional.ofNullable(null));

        //findAll
        List<Customer> customers=List.of(customer1,customer2,customer3);
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        //findByFirstName
        Mockito.when(customerRepository.findByFirstNameIgnoreCase("Prakash")).thenReturn(List.of(customer1));
        Mockito.when(customerRepository.findByFirstNameIgnoreCase("xyz")).thenReturn(Collections.emptyList());

        //findByLastName
        Mockito.when(customerRepository.findByLastNameIgnoreCase("S")).thenReturn(List.of(customer1));
        Mockito.when(customerRepository.findByLastNameIgnoreCase("A")).thenReturn(Collections.emptyList());

        //findByFirstNameandEmailId
        Mockito.when(customerRepository.findByFirstNameAndEmailIdIgnoreCase("Prakash","prakash@gmail.com")).thenReturn(customer1);
        Mockito.when(customerRepository.findByFirstNameAndEmailIdIgnoreCase("Invalid", "wrong@example.com")).thenReturn(null);

        //findByEmailId
        Mockito.when(customerRepository.findByEmailIdIgnoreCase("prakash@gmail.com")).thenReturn(customer1);
        Mockito.when(customerRepository.findByEmailIdIgnoreCase("wrong@example.com")).thenReturn(null);

        //findByAge
        Mockito.when(customerRepository.findByAge(18)).thenReturn(List.of(customer2));
        Mockito.when(customerRepository.findByAgeGreaterThanEqual(99)).thenReturn(Collections.emptyList());

        //findByAgeGreaterThanEqual
        Mockito.when(customerRepository.findByAgeGreaterThanEqual(18)).thenReturn(customers);
        Mockito.when(customerRepository.findByAgeGreaterThanEqual(99)).thenReturn(Collections.emptyList());

        //findByAgeGreaterThan
        Mockito.when(customerRepository.findByAgeGreaterThan(18)).thenReturn(List.of(customer1,customer3));
        Mockito.when(customerRepository.findByAgeGreaterThan(99)).thenReturn(Collections.emptyList());

        //findByAgeLesserThanEqual
        Mockito.when(customerRepository.findByAgeLessThanEqual(25)).thenReturn(customers);
        Mockito.when(customerRepository.findByAgeLessThanEqual(15)).thenReturn(Collections.emptyList());

        //findByAgeLesserThan
        Mockito.when(customerRepository.findByAgeLessThan(19)).thenReturn(List.of(customer2));
        Mockito.when(customerRepository.findByAgeLessThan(10)).thenReturn(Collections.emptyList());

        //findByGender
        Mockito.when(customerRepository.findByGenderIgnoreCase("Male")).thenReturn(List.of(customer1,customer2));
        Mockito.when(customerRepository.findByGenderIgnoreCase("Female")).thenReturn(List.of(customer3));
        Mockito.when(customerRepository.findByGenderIgnoreCase("abc")).thenReturn(Collections.emptyList());

        //update
        Customer updatedCustomer=new Customer(3,"Prethe","T","prethet@gmail.com","Female",19);
        Mockito.when(customerRepository.save( new Customer(3,"Prethe","T","prethet@gmail.com","Female",19))).thenReturn(updatedCustomer);
        Mockito.when(customerRepository.save( new Customer(999,"Prethe","T","prethet@gmail.com","Female",19))).thenReturn(null);

        //deleteById
        doNothing().when(customerRepository).deleteById(1);
        doNothing().when(customerRepository).deleteById(999);

    }
    @Test
    @Order(1)
    void testAcceptCustomers() {
        assertNotNull(customerService.acceptCustomer(new Customer("Prakash", "S", "prakash@gmail.com", "Male", 23)));
        assertNotNull(customerService.acceptCustomer(new Customer("Kishan", "N", "kishan@gmail.com", "Male", 18)));
        assertNotNull(customerService.acceptCustomer(new Customer("Prethe", "T", "prethe@gmail.com", "Female", 19)));
    }


    @Test
    @Order(2)
    void testGetCustomerById_Positive() throws CustomerDetailsNotFoundException {
        Customer expectedCustomer=new Customer(1,"Prakash", "S", "prakash@gmail.com", "Male", 23);
        assertEquals(expectedCustomer, customerService.getCustomerById(1));
    }


    @Test
    @Order(3)
    void testGetCustomerById_Negative() {
        Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerById(99));
    }

    @Test
    @Order(4)
    void testGetCustomersByFirstName_Positive() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getCustomersByFirstName("Prakash").isEmpty());
    }

    @Test
    @Order(5)
    void testGetCustomersByFirstName_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomersByFirstName("xyz"));
    }

    @Test
    @Order(6)
    void testGetCustomersByLastName_Positive() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getCustomersByLastName("S").isEmpty());
    }

    @Test
    @Order(7)
    void testGetCustomersByLastName_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomersByLastName("A"));
    }

    @Test
    @Order(8)
    void testGetCustomerByFirstNameAndEmailId_Positive() throws CustomerDetailsNotFoundException {
        Customer expectedCustomer=new Customer(1,"Prakash", "S", "prakash@gmail.com", "Male", 23);
        assertEquals(expectedCustomer,customerService.getCustomerByFirstNameAndEmailId("Prakash", "prakash@gmail.com"));
    }

    @Test
    @Order(9)
    void testGetCustomerByFirstNameAndEmailId_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerByFirstNameAndEmailId("Invalid", "wrong@example.com"));
    }

    @Test
    @Order(10)
    void testGetCustomerByEmailId_Positive() throws CustomerDetailsNotFoundException {
        Customer expectedCustomer=new Customer(1,"Prakash", "S", "prakash@gmail.com", "Male", 23);
        assertEquals(expectedCustomer, customerService.getCustomerByEmailId("prakash@gmail.com"));
    }

    @Test
    @Order(11)
    void testGetCustomerByEmailId_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerByEmailId("wrong@example.com"));
    }

    @Test
    @Order(12)
    void testGetAllCustomersByAge_Positive() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getAllCustomersByAge(18).isEmpty());
    }

    @Test
    @Order(13)
    void testGetAllCustomersByAge_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAge(99));
    }

    @Test
    @Order(14)
    void testGetAllCustomersByAgeGreaterThanEqual_Positive() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getAllCustomersByAgeGreaterThanEqual(18).isEmpty());
    }

    @Test
    @Order(15)
    void testGetAllCustomersByAgeGreaterThanEqual_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeGreaterThanEqual(99));
    }

    @Test
    @Order(16)
    void testGetAllCustomersByAgeGreaterThan_Positive() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getAllCustomersByAgeGreaterThan(18).isEmpty());
    }

    @Test
    @Order(17)
    void testGetAllCustomersByAgeGreaterThan_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeGreaterThan(99));
    }

    @Test
    @Order(18)
    void testGetAllCustomersByAgeLesserThan_Positive() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getAllCustomersByAgeLesserThan(19).isEmpty());
    }

    @Test
    @Order(19)
    void testGetAllCustomersByAgeLesserThan_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeLesserThan(10));
    }

    @Test
    @Order(20)
    void testGetAllCustomersByAgeLesserThanEqual_Positive() throws CustomerDetailsNotFoundException {
        assertFalse( customerService.getAllCustomersByAgeLesserThanEqual(25).isEmpty());
    }

    @Test
    @Order(21)
    void testGetAllCustomersByAgeLesserThanEqual_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeLesserThanEqual(15));
    }


    @Test
    @Order(22)
    void testGetAllCustomersByGender_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> malecustomers = customerService.getAllCustomersByGender("Male");
        assertFalse(malecustomers.isEmpty());
        List<Customer> femaleCustomers = customerService.getAllCustomersByGender("Female");
        assertFalse(femaleCustomers.isEmpty());
    }

    @Test
    @Order(23)
    void testGetAllCustomersByGender_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByGender("abc"));
    }

    @Test
    @Order(24)
    void testUpdateCustomerDetails_Positive() throws CustomerDetailsNotFoundException {
        assertEquals("Successfully Updated", customerService.updateCustomerDetails((new Customer(3,"Prethe","T","prethet@gmail.com","Female",19))));
    }

    @Test
    @Order(25)
    void testUpdateCustomerDetails_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.updateCustomerDetails(new Customer(999,"Prethe","T","prethet@gmail.com","Female",19)));
    }

    @Test
    @Order(26)
    void testRemoveCustomer_Positive() throws CustomerDetailsNotFoundException {
        assertEquals("Successfully Deleted", customerService.removeCustomer(1));
    }

    @Test
    @Order(27)
    void testRemoveCustomer_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.removeCustomer(999));
    }

    @Test
    @Order(28)
    void testGetAllCustomers() throws CustomerDetailsNotFoundException {
        assertFalse(customerService.getAllCustomers().isEmpty());
    }
}
