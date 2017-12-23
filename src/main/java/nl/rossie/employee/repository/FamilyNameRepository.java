package nl.rossie.employee.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import nl.rossie.employee.entity.FamilyName;

public interface FamilyNameRepository extends CrudRepository<FamilyName, UUID> {


	List<FamilyName> findFamilyNameByNameStartsWith(String name);
	
	List<FamilyName> findFamilyNameByNameContains(String name);
}