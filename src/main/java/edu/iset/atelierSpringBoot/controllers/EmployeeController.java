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
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.registerEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.findEmployeeById(id));
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Create new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // Update employee details
    @PutMapping("/edit/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> existingEmployee = Optional.ofNullable(employeeService.findEmployeeById(id));
        if (existingEmployee.isPresent()) {
            employee.setId(id);
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Delete employee
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (employeeService.deleteEmployee(id)) {
            return ResponseEntity.ok("Employee with ID " + id + " was deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
        }
    }

    // Get employee profile
    @GetMapping("/profile/{id}")
    public ResponseEntity<Employee> getProfile(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Update employee profile
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

            // Allow password change (ensure password is hashed before saving)
            if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
                existingEmployee.setPassword(updatedEmployee.getPassword());
            }

            // Save and return response
            employeeRepository.save(existingEmployee);
            return ResponseEntity.ok(existingEmployee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Validate employee (mark as validated)
    @PutMapping("/validate/{id}")
    public ResponseEntity<Employee> validateEmployee(@PathVariable Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            // Assuming you're adding some business logic here (e.g., setting an 'isValidated' flag)
            // employee.setEtat(true); // Mark the employee as validated
            employeeRepository.save(employee);
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
