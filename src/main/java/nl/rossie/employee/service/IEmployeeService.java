package nl.rossie.employee.service;

import java.util.List;

import nl.rossie.employee.entity.Employee;

public interface IEmployeeService {
     List<Employee> getAllEmployees();
     Employee getEmployeeById(long employeeId);
     boolean addEmployee(Employee employee);
     void updateEmployee(Employee employee);
     void deleteEmployee(long employeeId);
     List<Employee> findUsersByLastnameContains(String value);
}
