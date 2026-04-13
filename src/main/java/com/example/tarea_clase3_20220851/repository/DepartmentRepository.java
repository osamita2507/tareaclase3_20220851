package com.example.tarea_clase3_20220851.repository;

import com.example.tarea_clase3_20220851.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findAllByOrderByDepartmentNameAsc();

}
