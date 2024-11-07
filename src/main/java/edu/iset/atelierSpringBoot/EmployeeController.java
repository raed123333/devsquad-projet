package edu.iset.atelierSpringBoot;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/Employee") // localhost:8089/Employee
public class EmployeeController {

 @Autowired
 EmployeeService employeeService;

 @GetMapping(path = "/getAllEmployees")
 public List<Employee> getAllEmployees() {
     return employeeService.getAllEmployees();
 }

 @GetMapping("/{id}")
 public Employee findEmployeeById(@PathVariable Long id) {
     return employeeService.findEmployeeById(id);
 }

 @PostMapping
 public Employee createEmployee(@RequestBody Employee employee) {
     return employeeService.addEmployee(employee);
 }

 @PutMapping("/edit/{id}")
 public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
     employee.setId(id);
     return employeeService.updateEmployee(employee);
 }

 @DeleteMapping("/delete/{id}")
 public String deleteEmployee(@PathVariable Long id) {
     employeeService.deleteEmployee(id);
     return "Employee with ID " + id + " was deleted";
 }

 @PostMapping("/upload")
 public Employee uploadEmployeeImage(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
     try {
         // Set the path where files will be stored
         String folder = "uploads/";
         byte[] bytes = file.getBytes();
         Path path = Paths.get(folder + file.getOriginalFilename());
         Files.write(path, bytes);

         // Update the image path for the employee
         Employee employee = employeeService.findEmployeeById(id);
         if (employee != null) {
             employee.setImage(path.toString());
             return employeeService.updateEmployee(employee);
         } else {
             throw new RuntimeException("Employee not found");
         }
     } catch (IOException e) {
         throw new RuntimeException("File upload error: " + e.getMessage());
     }
 }
}
