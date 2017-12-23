package nl.rossie.employee.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import nl.rossie.employee.entity.FemaleFirstname;

public interface FemaleFirstnameRepository extends CrudRepository<FemaleFirstname, UUID> {


	List<FemaleFirstname> findFemaleFirstnameByNameStartsWith(String name);
	
	List<FemaleFirstname> findFemaleFirstnameByNameContains(String name);
}

