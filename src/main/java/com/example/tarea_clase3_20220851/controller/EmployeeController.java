package com.example.tarea_clase3_20220851.controller;

import com.example.tarea_clase3_20220851.EmployeeForm;
import com.example.tarea_clase3_20220851.entity.Department;
import com.example.tarea_clase3_20220851.entity.Employee;
import com.example.tarea_clase3_20220851.entity.Job;
import com.example.tarea_clase3_20220851.repository.DepartmentRepository;
import com.example.tarea_clase3_20220851.repository.EmployeeRepository;
import com.example.tarea_clase3_20220851.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeRepository employeeRepository,
                              JobRepository jobRepository,
                              DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/list")
    public String listar(@RequestParam(required = false) String texto, Model model) {
        model.addAttribute("empleados", employeeRepository.buscarEmpleados(texto));
        model.addAttribute("texto", texto);
        return "employee/list";
    }

    @GetMapping("/new")
    public String nuevo(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        cargarCombos(model);
        model.addAttribute("modo", "nuevo");
        model.addAttribute("actionUrl", "/employee/save");
        return "employee/form";
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute EmployeeForm form, RedirectAttributes ra) {
        Employee employee = new Employee();
        mapearFormulario(employee, form, true);
        employeeRepository.save(employee);
        ra.addFlashAttribute("msg", "Empleado creado exitosamente");
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        EmployeeForm form = new EmployeeForm();
        form.setEmployeeId(employee.getEmployeeId());
        form.setFirstName(employee.getFirstName());
        form.setLastName(employee.getLastName());
        form.setEmail(employee.getEmail());
        form.setSalary(employee.getSalary());
        form.setJobId(employee.getJob() != null ? employee.getJob().getJobId() : null);
        form.setManagerId(employee.getManager() != null ? employee.getManager().getEmployeeId() : null);
        form.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getDepartmentId() : null);

        model.addAttribute("employeeForm", form);
        cargarCombos(model);
        model.addAttribute("modo", "editar");
        model.addAttribute("actionUrl", "/employee/update");
        return "employee/form";
    }

    @PostMapping("/update")
    public String actualizar(@ModelAttribute EmployeeForm form, RedirectAttributes ra) {
        Employee employee = employeeRepository.findById(form.getEmployeeId()).orElseThrow();
        mapearFormulario(employee, form, false);
        employeeRepository.save(employee);
        ra.addFlashAttribute("msg", "Empleado actualizado exitosamente");
        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String borrar(@PathVariable Integer id, RedirectAttributes ra) {
        employeeRepository.deleteById(id);
        ra.addFlashAttribute("msg", "Empleado borrado exitosamente");
        return "redirect:/employee/list";
    }

    private void cargarCombos(Model model) {
        model.addAttribute("jobs", jobRepository.findAllByOrderByJobTitleAsc());
        model.addAttribute("managers", employeeRepository.findAllByOrderByFirstNameAsc());
        model.addAttribute("departments", departmentRepository.findAllByOrderByDepartmentNameAsc());
    }

    private void mapearFormulario(Employee employee, EmployeeForm form, boolean esNuevo) {
        employee.setFirstName(form.getFirstName());
        employee.setLastName(form.getLastName());
        employee.setEmail(form.getEmail());
        employee.setSalary(form.getSalary());

        if (esNuevo) {
            employee.setHireDate(LocalDateTime.now());
        }

        Job job = jobRepository.findById(form.getJobId()).orElse(null);
        employee.setJob(job);

        Department department = departmentRepository.findById(form.getDepartmentId()).orElse(null);
        employee.setDepartment(department);

        if (form.getManagerId() != null) {
            Employee manager = employeeRepository.findById(form.getManagerId()).orElse(null);
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }
    }
}