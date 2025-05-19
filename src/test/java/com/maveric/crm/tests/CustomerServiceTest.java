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
    void setup() {
        Customer customer1 = new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23);
        Customer customer2 = new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18);
        Customer customer3 = new Customer(3, "Prethe", "T", "prethe@gmail.com", "Female", 19);
        //save
        Mockito.when(customerRepository.save(new Customer("Prakash", "S", "prakash@gmail.com", "Male", 23))).thenReturn(customer1);
        Mockito.when(customerRepository.save(new Customer("Kishan", "N", "kishan@gmail.com", "Male", 18))).thenReturn(customer2);
        Mockito.when(customerRepository.save(new Customer("Prethe", "T", "prethe@gmail.com", "Female", 19))).thenReturn(customer3);

        //findById
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));
        Mockito.when(customerRepository.findById(2)).thenReturn(Optional.of(customer2));
        Mockito.when(customerRepository.findById(3)).thenReturn(Optional.of(customer3));
        Mockito.when(customerRepository.findById(99)).thenReturn(Optional.empty());

        //findAll
        List<Customer> allCustomers = List.of(customer1, customer2, customer3);
        Mockito.when(customerRepository.findAll()).thenReturn(allCustomers);

        //findByFirstNameIgnoreCase
        Mockito.when(customerRepository.findByFirstNameIgnoreCase("Prakash")).thenReturn(List.of(customer1));
        Mockito.when(customerRepository.findByFirstNameIgnoreCase("xyz")).thenReturn(Collections.emptyList());

        //findByLastNameIgnoreCase
        Mockito.when(customerRepository.findByLastNameIgnoreCase("S")).thenReturn(List.of(customer1));
        Mockito.when(customerRepository.findByLastNameIgnoreCase("A")).thenReturn(Collections.emptyList());

        //findByFirstNameAndEmailIdIgnoreCase
        Mockito.when(customerRepository.findByFirstNameAndEmailIdIgnoreCase("Prakash", "prakash@gmail.com")).thenReturn(customer1);
        Mockito.when(customerRepository.findByFirstNameAndEmailIdIgnoreCase("Invalid", "wrong@example.com")).thenReturn(null);

        //findByEmailIdIgnoreCase
        Mockito.when(customerRepository.findByEmailIdIgnoreCase("prakash@gmail.com")).thenReturn(customer1);
        Mockito.when(customerRepository.findByEmailIdIgnoreCase("wrong@example.com")).thenReturn(null);

        //findByAge
        Mockito.when(customerRepository.findByAge(18)).thenReturn(List.of(customer2));
        Mockito.when(customerRepository.findByAge(99)).thenReturn((Collections.emptyList()));

        //findByAgeGreaterThanEqual
        Mockito.when(customerRepository.findByAgeGreaterThanEqual(18)).thenReturn(allCustomers);
        Mockito.when(customerRepository.findByAgeGreaterThanEqual(99)).thenReturn(Collections.emptyList());

        //findByAgeGreaterThan
        Mockito.when(customerRepository.findByAgeGreaterThan(18)).thenReturn(List.of(customer1, customer3));
        Mockito.when(customerRepository.findByAgeGreaterThan(99)).thenReturn(Collections.emptyList());

        //findByAgeLessThanEqual
        Mockito.when(customerRepository.findByAgeLessThanEqual(25)).thenReturn(allCustomers);
        Mockito.when(customerRepository.findByAgeLessThanEqual(15)).thenReturn(Collections.emptyList());

        //findByAgeLessThan
        Mockito.when(customerRepository.findByAgeLessThan(19)).thenReturn(List.of(customer2));
        Mockito.when(customerRepository.findByAgeLessThan(10)).thenReturn(Collections.emptyList());

        //findByGenderIgnoreCase
        Mockito.when(customerRepository.findByGenderIgnoreCase("Male")).thenReturn(List.of(customer1, customer2));
        Mockito.when(customerRepository.findByGenderIgnoreCase("Female")).thenReturn(List.of(customer3));
        Mockito.when(customerRepository.findByGenderIgnoreCase("abc")).thenReturn(Collections.emptyList());

        //update
        Customer updatedCustomer = new Customer(3, "Prethe", "T", "prethet@gmail.com", "Female", 19);
        Mockito.when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
        Mockito.when(customerRepository.save(new Customer(999, "Prethe", "T", "prethet@gmail.com", "Female", 19))).thenReturn(null);

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
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(Mockito.any(Customer.class));
    }

    @Test
    @Order(2)
    void testGetCustomerById_Positive() throws CustomerDetailsNotFoundException {
        Customer expected = new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23);
        assertEquals(expected, customerService.getCustomerById(1));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(1);
    }

    @Test
    @Order(3)
    void testGetCustomerById_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerById(99));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(99);
    }

    @Test
    @Order(4)
    void testGetCustomersByFirstName_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23));
        assertEquals(expected, customerService.getCustomersByFirstName("Prakash"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstNameIgnoreCase("Prakash");
    }

    @Test
    @Order(5)
    void testGetCustomersByFirstName_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomersByFirstName("xyz"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstNameIgnoreCase("xyz");
    }

    @Test
    @Order(6)
    void testGetCustomersByLastName_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23));
        assertEquals(expected, customerService.getCustomersByLastName("S"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByLastNameIgnoreCase("S");
    }

    @Test
    @Order(7)
    void testGetCustomersByLastName_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomersByLastName("A"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByLastNameIgnoreCase("A");
    }

    @Test
    @Order(8)
    void testGetCustomerByFirstNameAndEmailId_Positive() throws CustomerDetailsNotFoundException {
        Customer expected = new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23);
        assertEquals(expected, customerService.getCustomerByFirstNameAndEmailId("Prakash", "prakash@gmail.com"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstNameAndEmailIdIgnoreCase("Prakash", "prakash@gmail.com");
    }

    @Test
    @Order(9)
    void testGetCustomerByFirstNameAndEmailId_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerByFirstNameAndEmailId("Invalid", "wrong@example.com"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstNameAndEmailIdIgnoreCase("Invalid", "wrong@example.com");
    }

    @Test
    @Order(10)
    void testGetCustomerByEmailId_Positive() throws CustomerDetailsNotFoundException {
        Customer expected = new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23);
        assertEquals(expected, customerService.getCustomerByEmailId("prakash@gmail.com"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByEmailIdIgnoreCase("prakash@gmail.com");
    }

    @Test
    @Order(11)
    void testGetCustomerByEmailId_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getCustomerByEmailId("wrong@example.com"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByEmailIdIgnoreCase("wrong@example.com");
    }

    @Test
    @Order(12)
    void testGetAllCustomersByAge_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18));
        assertEquals(expected, customerService.getAllCustomersByAge(18));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAge(18);
    }

    @Test
    @Order(13)
    void testGetAllCustomersByAge_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAge(99));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAge(99);
    }

    @Test
    @Order(14)
    void testGetAllCustomersByAgeGreaterThanEqual_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(
                new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23),
                new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18),
                new Customer(3, "Prethe", "T", "prethe@gmail.com", "Female", 19)
        );
        assertEquals(expected, customerService.getAllCustomersByAgeGreaterThanEqual(18));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeGreaterThanEqual(18);
    }

    @Test
    @Order(15)
    void testGetAllCustomersByAgeGreaterThanEqual_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeGreaterThanEqual(99));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeGreaterThanEqual(99);
    }

    @Test
    @Order(16)
    void testGetAllCustomersByAgeGreaterThan_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(
                new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23),
                new Customer(3, "Prethe", "T", "prethe@gmail.com", "Female", 19)
        );
        assertEquals(expected, customerService.getAllCustomersByAgeGreaterThan(18));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeGreaterThan(18);
    }

    @Test
    @Order(17)
    void testGetAllCustomersByAgeGreaterThan_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeGreaterThan(99));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeGreaterThan(99);
    }

    @Test
    @Order(18)
    void testGetAllCustomersByAgeLesserThan_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18));
        assertEquals(expected, customerService.getAllCustomersByAgeLesserThan(19));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeLessThan(19);
    }

    @Test
    @Order(19)
    void testGetAllCustomersByAgeLesserThan_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeLesserThan(10));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeLessThan(10);
    }

    @Test
    @Order(20)
    void testGetAllCustomersByAgeLesserThanEqual_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(
                new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23),
                new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18),
                new Customer(3, "Prethe", "T", "prethe@gmail.com", "Female", 19)
        );
        assertEquals(expected, customerService.getAllCustomersByAgeLesserThanEqual(25));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeLessThanEqual(25);
    }

    @Test
    @Order(21)
    void testGetAllCustomersByAgeLesserThanEqual_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByAgeLesserThanEqual(15));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAgeLessThanEqual(15);
    }

    @Test
    @Order(22)
    void testGetAllCustomersByGender_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> maleExpected = List.of(
                new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23),
                new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18)
        );
        List<Customer> femaleExpected = List.of(
                new Customer(3, "Prethe", "T", "prethe@gmail.com", "Female", 19)
        );

        assertEquals(maleExpected, customerService.getAllCustomersByGender("Male"));
        assertEquals(femaleExpected, customerService.getAllCustomersByGender("Female"));

        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByGenderIgnoreCase("Male");
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByGenderIgnoreCase("Female");
    }

    @Test
    @Order(23)
    void testGetAllCustomersByGender_Negative() {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.getAllCustomersByGender("abc"));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByGenderIgnoreCase("abc");
    }

    @Test
    @Order(24)
    void testUpdateCustomerDetails_Positive() throws CustomerDetailsNotFoundException {
        Customer input = new Customer(3, "Prethe", "T", "prethet@gmail.com", "Female", 19);
        assertEquals("Successfully Updated", customerService.updateCustomerDetails(input));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(input);
    }

    @Test
    @Order(25)
    void testUpdateCustomerDetails_Negative(){
        Customer invalid = new Customer(999, "Prethe", "T", "prethet@gmail.com", "Female", 19);
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.updateCustomerDetails(invalid));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(invalid.getId());
    }

    @Test
    @Order(26)
    void testDeleteCustomer_Positive()throws CustomerDetailsNotFoundException {
        customerService.removeCustomer(1);
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).deleteById(1);
    }

    @Test
    @Order(27)
    void testDeleteCustomer_Negative()throws CustomerDetailsNotFoundException {
        assertThrows(CustomerDetailsNotFoundException.class, () -> customerService.removeCustomer(999));
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(999);
    }

    @Test
    @Order(28)
    void testGetAllCustomers_Positive() throws CustomerDetailsNotFoundException {
        List<Customer> expected = List.of(
                new Customer(1, "Prakash", "S", "prakash@gmail.com", "Male", 23),
                new Customer(2, "Kishan", "N", "kishan@gmail.com", "Male", 18),
                new Customer(3, "Prethe", "T", "prethe@gmail.com", "Female", 19)
        );
        assertEquals(expected, customerService.getAllCustomers());
        Mockito.verify(customerRepository, Mockito.atLeastOnce()).findAll();
    }


}
