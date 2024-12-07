package edu.iset.atelierSpringBoot.services;

import java.util.List;

import edu.iset.atelierSpringBoot.entites.Employee;

public interface EmployeeService {
 List<Employee> getAllEmployees();
 Employee findEmployeeById(Long id);
 Employee addEmployee(Employee employee);
 Employee updateEmployee(Employee employee);
 boolean deleteEmployee(Long id);
Employee registerEmployee(Employee employee);
Employee getProfile(Long id);
Employee validateEmployee(Long id);
Employee findEmployeeByEmail(String email);
}
