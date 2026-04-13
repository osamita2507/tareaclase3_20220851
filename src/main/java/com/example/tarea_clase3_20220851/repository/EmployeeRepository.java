package com.example.tarea_clase3_20220851.repository;

import com.example.tarea_clase3_20220851.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("""
        SELECT e
        FROM Employee e
        LEFT JOIN e.job j
        LEFT JOIN e.department d
        LEFT JOIN d.location l
        WHERE (:texto IS NULL OR :texto = '' OR
               LOWER(COALESCE(e.firstName,'')) LIKE LOWER(CONCAT('%', :texto, '%')) OR
               LOWER(COALESCE(e.lastName,'')) LIKE LOWER(CONCAT('%', :texto, '%')) OR
               LOWER(COALESCE(j.jobTitle,'')) LIKE LOWER(CONCAT('%', :texto, '%')) OR
               LOWER(COALESCE(d.departmentName,'')) LIKE LOWER(CONCAT('%', :texto, '%')) OR
               LOWER(COALESCE(l.city,'')) LIKE LOWER(CONCAT('%', :texto, '%')))
        ORDER BY e.employeeId
    """)

    List<Employee> buscarEmpleados(String texto);

    List<Employee> findAllByOrderByFirstNameAsc();

}

