package nl.rossie.employee.client;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import nl.rossie.employee.entity.Employee;

public class RestClientUtil {
	
    public void getEmployeeByIdDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8080/user/employee/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Employee> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Employee.class, 1);
        Employee employee = responseEntity.getBody();
        System.out.println(employee);      
    }
    
	public void getAllEmployeesDemo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8080/user/employees";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Employee[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Employee[].class);
        Employee[] employees = responseEntity.getBody();
        for(Employee employee : employees) {
              System.out.println(employee);
        }
    }
	
    public void updateEmployeeDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8080/user/employee";
	    Employee objEmployee = new Employee();
	    objEmployee.setId(Long.valueOf(99));
	    objEmployee.setFirstname("MarcelUpdated");
	    objEmployee.setLastname("PootsUpdated");
        HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(objEmployee, headers);
        restTemplate.put(url, requestEntity);
    }
    
   public void addEmployeeDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8080/user/employee";
	    Employee objEmployee = new Employee();
	    objEmployee.setId(Long.valueOf(99));
	    objEmployee.setFirstname("naam");
	    objEmployee.setLastname("achternaam");
	    //objEmployee.setBirthDate(new Date());
        HttpEntity<Employee> requestEntity = new HttpEntity<Employee>(objEmployee, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);
        System.out.println(uri.getPath());    	
    }

    public void deleteEmployeeDemo() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8080/user/employee/99";
	    System.err.println(url + " (DELETE) start..");
        restTemplate.delete(url);      
        System.err.println(url + " (DELETE) gelukt..");
    }
    public static void main(String args[]) {
    	RestClientUtil util = new RestClientUtil();
        util.getEmployeeByIdDemo();
    	util.getAllEmployeesDemo();
    	util.addEmployeeDemo();
    	util.updateEmployeeDemo();
    	util.deleteEmployeeDemo();
    }    
}
