package nl.rossie.employee.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import nl.rossie.employee.entity.MaleFirstname;

public interface MaleFirstnameRepository extends CrudRepository<MaleFirstname, UUID> {


	List<MaleFirstname> findMaleFirstnameByNameStartsWith(String name);
	
	List<MaleFirstname> findMaleFirstnameByNameContains(String name);
}
