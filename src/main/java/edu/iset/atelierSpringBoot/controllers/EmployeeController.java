package edu.iset.atelierSpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.repositories.IEmployeeRepository;
import edu.iset.atelierSpringBoot.services.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Employee") 
@CrossOrigin(origins = "http://localhost:5173")  // Enable CORS for frontend
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private IEmployeeRepository employeeRepository; 
 
    // Get all employees
    @GetMapping(path = "/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    
    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody Employee employee) {
        return employeeService.registerEmployee(employee);
    }
    
    // Get employee by ID
    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    // Create new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    // Update employee details
    @PutMapping("/edit/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        return employeeService.updateEmployee(employee);
    }

    // Delete employee
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee with ID " + id + " was deleted";
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<Employee> getProfile(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            
            // Log the password to check if it's being retrieved
            System.out.println("Employee Profile: " + existingEmployee);

            return ResponseEntity.ok(existingEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Employee> updateProfile(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();

            // Update allowed fields
            existingEmployee.setNom(updatedEmployee.getNom());
            existingEmployee.setPrenom(updatedEmployee.getPrenom());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setAge(updatedEmployee.getAge());
            existingEmployee.setNumPhone(updatedEmployee.getNumPhone());

            // Allow password change
            if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
                existingEmployee.setPassword(updatedEmployee.getPassword());
            }

            // Save and return response
            employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(existingEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PutMapping("/validate/{id}")
    public ResponseEntity<Employee> validateEmployee(@PathVariable Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setEtat(true); // Mark the employee as validated
            employeeRepository.save(employee);
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
}