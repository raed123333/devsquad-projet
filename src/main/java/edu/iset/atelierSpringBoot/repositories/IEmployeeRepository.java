package edu.iset.atelierSpringBoot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findAll();
}
