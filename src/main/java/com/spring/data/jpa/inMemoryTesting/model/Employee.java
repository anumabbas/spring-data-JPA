package com.spring.data.jpa.inMemoryTesting.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Employee Entity
 */

@Entity
@Table(name="employees")
@Data
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  private Integer age;

  public Employee(){

  }
  public Employee(String name, Integer age) {
    super();
    this.name = name;
    this.age = age;
  }
}
