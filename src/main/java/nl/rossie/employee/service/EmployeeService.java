package nl.rossie.employee.service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.rossie.employee.entity.Employee;
import nl.rossie.employee.repository.BasicUserRepository;

@Service
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	BasicUserRepository basicUserRepository;
	
	@Override
	public Employee getEmployeeById(long employeeId) {
		Employee obj = basicUserRepository.findUserByIdIn(employeeId);
		return obj;
	}	
	
	private static <T> List<T> toList(final Iterable<T> iterable) {
	    return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}
	
	@Override
	public List<Employee> getAllEmployees(){
		return  toList(basicUserRepository.findAll());
	}
	
	@Override
	public synchronized boolean addEmployee(Employee employee){
/*       if (employeeDAO.employeeExists(employee.getFirstname(), employee.getLastname())) {
    	   return false;
       } else {
    	   employeeDAO.addEmployee(employee);
    	   return true;
       }
*/
		if (employee.getId() < 1) {
			employee.setId(Calendar.getInstance().getTimeInMillis());
		}
		basicUserRepository.save(employee);
		return true;
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		basicUserRepository.save(employee);
	}
	
	@Override
	public void deleteEmployee(long employeeId) {
		basicUserRepository.delete(employeeId);
	}
	
	@Override
	public List<Employee> findUsersByLastnameContains(String value){
	    return basicUserRepository.findUsersByLastnameContains(value);
	}
}
