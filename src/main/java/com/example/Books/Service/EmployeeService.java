package com.example.Books.Service;

import com.example.Books.Model.Employee;
import com.example.Books.Model.Store;
import com.example.Books.Repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    public List<Employee> getAllEmployees(){
        /*
        returns all the employees in the repository
         */
        return employeeRepository.findAll().stream().collect(Collectors.toList());
    }

    public Employee addEmployeeToRepository(@Valid Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }

    public Store getStoreIDByEmployeeID(Long id){
        /*
        gets an employee by id
         */
        return employeeRepository.findById(id).get().getStore();
    }

    public Employee updateEmployeeInRepository(Long id, Employee updatedEmployee)
    {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            employee.setSalary(updatedEmployee.getSalary());
            employee.setFullTime(updatedEmployee.isFullTime());
            employee.setStore(updatedEmployee.getStore());
            return employeeRepository.save(employee);
                }).orElseGet(()->{
                    updatedEmployee.setId(id);
                    return employeeRepository.save(updatedEmployee);
        });
    }

    public void deleteEmployeeInRepository(Long id){
        employeeRepository.deleteById(id);
    }


    public Employee getEmployeeByID(Long id){
        return this.employeeRepository.findById(id).get();
    }


}
