package edu.iset.atelierSpringBoot.services;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.iset.atelierSpringBoot.entites.Employee;
import edu.iset.atelierSpringBoot.repositories.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepo;

    //@Autowired
    //private PasswordEncoder passwordEncoder;
    @Override
    public Employee validateEmployee(Long id) {
        Optional<Employee> employeeOpt = employeeRepo.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            //employee.setEtat(true);
            return employeeRepo.save(employee);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }


    @Override
    public Employee registerEmployee(Employee employee) {
        // Hash the password provided by the user
        // String hashedPassword = passwordEncoder.encode(employee.getPassword());
        //employee.setPassword(hashedPassword);
        employee.setPassword(employee.getPassword());
        // Mark the employee as inactive by default
        //employee.setEtat(false);

        // Save the employee to the database
        return employeeRepo.save(employee);
    }

    public Employee getProfile(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

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
    public boolean deleteEmployee(Long id) {
        if (employeeRepo.existsById(id)) {
            employeeRepo.deleteById(id);
            return true; // Return true after successful deletion
        } else {
            System.err.println("Error: Employee with ID " + id + " does not exist");
            return false; // Return false if the employee doesn't exist
        }
    }


    public Employee findEmployeeByEmail(String email) {
        return employeeRepo.findByEmail(email).orElse(null);
    }

}