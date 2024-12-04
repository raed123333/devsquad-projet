package edu.iset.atelierSpringBoot.repositories;

import java.util.List;
import java.util.Optional;

import edu.iset.atelierSpringBoot.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.iset.atelierSpringBoot.entites.Employee;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findAll();
	Optional<Employee> findByEmail(String email);
	// Find employees by role and etat
	List<Employee> findByRoleAndEtat(Role role, boolean etat);

	// Find employees by role
	List<Employee> findByRole(Role role);
}
