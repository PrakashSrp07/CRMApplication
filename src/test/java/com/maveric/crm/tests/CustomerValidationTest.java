package com.maveric.crm.tests;

import com.maveric.crm.pojos.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = com.maveric.crm.CRMApplication.class)
public class CustomerValidationTest {

    @Autowired
    private Validator validator;

    private Customer getValidCustomer() {
        return new Customer(1,"Prakash","S","prakash@gmail.com","Male",23);
    }

    @Test
    void testInvalidFirstName_Blank() {
        Customer customer = getValidCustomer();
        customer.setFirstName(" ");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("First name is required")));
    }

    @Test
    void testInvalidFirstName_ShortLength() {
        Customer customer = getValidCustomer();
        customer.setFirstName("J");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("First name must be between 2 and 50 characters")));
    }

    @Test
    void testInvalidFirstName_NonAlphabetic() {
        Customer customer = getValidCustomer();
        customer.setFirstName("John123");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("First name must contain only letters")));
    }

    @Test
    void testInvalidLastName() {
        Customer customer = getValidCustomer();
        customer.setLastName("Doe123");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Last name must contain only letters")));
    }

    @Test
    void testInvalidEmail() {
        Customer customer = getValidCustomer();
        customer.setEmailId("not-an-email");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Invalid email format")));
    }

    @Test
    void testInvalidGender() {
        Customer customer = getValidCustomer();
        customer.setGender("Unknown");
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Gender must be Male, Female, or Other")));
    }

    @Test
    void testInvalidAge_TooYoung() {
        Customer customer = getValidCustomer();
        customer.setAge(16);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Age must be at least 18")));
    }

    @Test
    void testInvalidAge_TooOld() {
        Customer customer = getValidCustomer();
        customer.setAge(101);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Age must not exceed 100")));
    }

    @Test
    void testValidCustomer() {
        Customer customer = getValidCustomer();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertTrue(violations.isEmpty());
    }
}
