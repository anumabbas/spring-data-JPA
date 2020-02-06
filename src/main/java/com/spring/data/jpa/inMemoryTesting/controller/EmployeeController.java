package com.spring.data.jpa.inMemoryTesting.controller;

import com.spring.data.jpa.inMemoryTesting.dao.EmployeeRepository;
import com.spring.data.jpa.inMemoryTesting.model.Employee;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * endpoints controller class for Employee entity
 */

@Slf4j
@Controller
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepo;

  /**
   * Method to save/create employees in the database
   * @param employees List of employee beans
   * @return ResponseEntity contains success or failure status with no data
   */

  @RequestMapping(consumes = "application/json")
  public ResponseEntity saveEmployees(@RequestBody List<Employee> employees) {

    try {
      if (!employees.isEmpty()) {
        employeeRepo.saveAll(employees);
      }
    } catch (Exception ex) {
      log.error("Exception occurs during save {}", ex);
      return ResponseEntity.status(500).build();
    }
    return ResponseEntity.status(200).build();
  }

  /**
   * Method to fetch Employee data on the basis of employee id
   * @param id primary key employee id
   * @return ResponseEntity having employee data
   */

  @RequestMapping(value = "/{id}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id) {

    Optional<Employee> employee = employeeRepo.findById(id);
    return (employee.isPresent()) ? ResponseEntity.ok().body(employee.get())
        : ResponseEntity.ok().body(null);
  }

  /**
   * Method to fetch Employees data on the basis of employee's name
   * @param name employee name
   * @return ResponseEntity having employees data searched by name
   */

  @RequestMapping(value = "/name/{name}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable("name") String name) {

    List<Employee> employees = employeeRepo.findByName(name);
    return (ObjectUtils.isEmpty(employees)) ? ResponseEntity.ok().body(Collections.EMPTY_LIST)
        : ResponseEntity.ok().body(employees);
  }

}
