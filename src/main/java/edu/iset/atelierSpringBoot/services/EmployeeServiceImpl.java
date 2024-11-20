package edu.iset.atelierSpringBoot;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

 @Autowired
 private IEmployeeRepository employeeRepo;

 @Override
 public List<Employee> getAllEmployees() {
     return employeeRepo.findAll();
 }

 @Override
 public Employee addEmployee(Employee employee) {
     return employeeRepo.save(employee);
 }

 @Override
 public Employee findEmployeeById(Long id) {
     Optional<Employee> employee = employeeRepo.findById(id);
     return employee.orElse(null);
 }

 @Override
 public Employee updateEmployee(Employee employee) {
     if (employeeRepo.existsById(employee.getId())) {
         return employeeRepo.save(employee);
     } else {
         return null;
     }
 }

 @Override
 public void deleteEmployee(Long id) {
     if (employeeRepo.existsById(id)) {
         employeeRepo.deleteById(id);
     } else {
         System.out.println("Error in delete function");
     }
 }
}
