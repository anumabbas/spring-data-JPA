package com.spring.data.jpa.inMemoryTesting.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.spring.data.jpa.inMemoryTesting.model.Employee;

/**
 * Repository class to manage Employees data in database
 */

@Repository
public interface EmployeeRepository  extends CrudRepository<Employee, Long> {
		
	List<Employee> findByName(String employeeName);
	List<Employee> findByAge(Integer age);
	
}
