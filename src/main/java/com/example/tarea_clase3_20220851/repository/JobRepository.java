package com.example.tarea_clase3_20220851.repository;

import com.example.tarea_clase3_20220851.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  JobRepository extends JpaRepository<Job, String> {
    List<Job> findAllByOrderByJobTitleAsc();
}

