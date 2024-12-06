package edu.iset.atelierSpringBoot.controllers;

import java.util.List;
import java.util.Optional;

import edu.iset.atelierSpringBoot.entites.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.repositories.IEmployeeRepository;
import edu.iset.atelierSpringBoot.services.EmployeeService;

@RestController
@RequestMapping("/Employee")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private IEmployeeRepository employeeRepository;

    // Get all employees with role 'employe' and etat true
    @GetMapping("/getAllEmployeeTrue")
    public List<Employee> getAllEmployeeTrue() {
        return employeeRepository.findByRoleAndEtat(Role.EMPLOYEE, true);
    }

    // Get all employees with role 'employe' and etat false
    @GetMapping("/getAllEmployeeFalse")
    public List<Employee> getAllEmployeeFalse() {
        return employeeRepository.findByRoleAndEtat(Role.EMPLOYEE, false);
    }

    // Get all employees with role 'RH'
    @GetMapping("/getAllRh")
    public List<Employee> getAllRh() {
        return employeeRepository.findByRole(Role.RH);
    }

    @GetMapping("/getAllEMPLOYEE")
    public List<Employee> getAllEMPLOYEE() {
        return employeeRepository.findByRole(Role.EMPLOYEE);
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
    public Employee validateEmployee(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setEtat(true); // Uncomment if you want to set the status
                    return employeeRepository.save(employee);
                }).orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    @GetMapping("/test/{email}")
    public ResponseEntity<Employee> findEmployeeByEmail(@PathVariable String email) {
        Optional<Employee> employee = Optional.ofNullable(employeeService.findEmployeeByEmail(email));
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }


}
