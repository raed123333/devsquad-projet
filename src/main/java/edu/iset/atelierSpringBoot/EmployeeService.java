package edu.iset.atelierSpringBoot;

import java.util.List;

public interface EmployeeService {
 List<Employee> getAllEmployees();
 Employee findEmployeeById(Long id);
 Employee addEmployee(Employee employee);
 Employee updateEmployee(Employee employee);
 void deleteEmployee(Long id);
}
