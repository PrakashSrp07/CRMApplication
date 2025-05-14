package com.maveric.crm.repositories;

import com.maveric.crm.pojos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByEmailIdIgnoreCase(String emailId);
    Customer findByFirstNameAndEmailIdIgnoreCase(String firstName,String emailId);
    List<Customer> findByFirstNameIgnoreCase(String firstName);
    List<Customer> findByLastNameIgnoreCase(String lastName);
    List<Customer> findByAge(int age);
    List<Customer> findByAgeGreaterThanEqual(int age);
    List<Customer> findByAgeGreaterThan(int age);
    List<Customer> findByAgeLessThanEqual(int age);
    List<Customer> findByAgeLessThan(int age);
    List<Customer> findByGenderIgnoreCase(String gender);
}
