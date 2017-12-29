package nl.rossie.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import nl.rossie.upload.service.StorageService;


@SpringBootApplication
@ComponentScan({"nl.rossie.employee", "nl.rossie.upload"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application implements CommandLineRunner{  
	
	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
    }  

	@Override
	public void run(String... arg) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
	
     
}            