package com.spring.data.jpa.inMemoryTesting.dao;

import static org.junit.Assert.assertEquals;

import com.spring.data.jpa.inMemoryTesting.config.SpringDataSourceConfiguration;
import com.spring.data.jpa.inMemoryTesting.model.Employee;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringDataSourceConfiguration.class})
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository empRepo;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void findByName_whenSave_returnsOk() {
    Employee emp = new Employee("Marathon", 67);
    empRepo.save(emp);
    List<Employee> foundEmployees = empRepo.findByName("Marathon");
    assertEquals("Marathon", foundEmployees.get(0).getName());
  }

  @Test
  public void saveEmployee_whenFetchedByAge_shouldMatchCount() {

    empRepo.save(new Employee("Marathon", 67));
    empRepo.save(new Employee("Igor", 59));
    empRepo.save(new Employee("Scott", 60));
    empRepo.save(new Employee("Telsa", 60));
    List<Employee> savedEmployees = empRepo.findByAge(60);
    assertEquals("size not matched", savedEmployees.size(), 2);
  }

  @Test
  public void saveEmployee_whenFetchedByAge_shouldNotMatchCount() {

    empRepo.save(new Employee("Marathon", 67));
    empRepo.save(new Employee("Igor", 59));
    empRepo.save(new Employee("Scott", 60));
    empRepo.save(new Employee("Telsa", 60));
    List<Employee> savedEmployees = empRepo.findByAge(67);
    assertEquals("size not matched", savedEmployees.size(), 2);
  }
}
