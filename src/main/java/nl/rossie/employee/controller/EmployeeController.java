package nl.rossie.employee.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import nl.rossie.employee.entity.Employee;
import nl.rossie.employee.service.IEmployeeService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("user")
public class EmployeeController {
	
	@Autowired
	private IEmployeeService employeeService;
	
	@GetMapping("employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@GetMapping("employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> list = employeeService.getAllEmployees();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	@GetMapping("employees/lastname/{value}")
	public ResponseEntity<List<Employee>> getAllEmployeesWithLastnameContaining(@PathVariable("value") String value) {
		List<Employee> list = employeeService.findUsersByLastnameContains(value);
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

	
	@PostMapping("employee")
	public ResponseEntity<Void> addEmployee(@RequestBody Employee employee, UriComponentsBuilder builder) {
		Employee emp = new Employee();
		emp.setId(employee.getId());
		emp.setFirstname(employee.getFirstname());
		emp.setLastname(employee.getLastname());
		boolean flag = employeeService.addEmployee(employee);

        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/employee/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("employee")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		System.err.println("Incomming update request: " + employee.getFirstname());
		employeeService.updateEmployee(employee);
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	@DeleteMapping("employee/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") long id) {
		System.err.println("INCOMMING: Employee  ["+ id + "] to be deleted");
		employeeService.deleteEmployee(id);
		System.err.println("INCOMMING: Employee  ["+ id + "] to be deleted Finished");
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
} 