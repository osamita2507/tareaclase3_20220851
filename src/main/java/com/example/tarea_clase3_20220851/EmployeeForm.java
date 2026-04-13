package com.example.tarea_clase3_20220851;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeForm {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private BigDecimal salary;
    private String jobId;
    private Integer managerId;
    private Integer departmentId;
}
