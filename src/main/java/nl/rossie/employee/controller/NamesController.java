package nl.rossie.employee.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import nl.rossie.employee.entity.FamilyName;
import nl.rossie.employee.entity.FemaleFirstname;
import nl.rossie.employee.entity.MaleFirstname;
import nl.rossie.employee.repository.FamilyNameRepository;
import nl.rossie.employee.repository.FemaleFirstnameRepository;
import nl.rossie.employee.repository.MaleFirstnameRepository;

@Controller
public class NamesController {

	@Autowired
	private MaleFirstnameRepository maleFirstnameRepository;
	
	@Autowired
	private FemaleFirstnameRepository femaleFirstnameRepository;	
	
	@Autowired
	private FamilyNameRepository familyNameRepository;	
	
	@GetMapping("male_firstnames")
	public ResponseEntity<Void> makeMockMaleFirstNames() {
		getMaleFirstnames();
		System.err.println("makeMockMaleFirstNames");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("female_firstnames")
	public ResponseEntity<Void> makeMockFemaleFirstNames() {
		getFemaleFirstnames();
		System.err.println("makeMockFemaleFirstNames");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("family_names")
	public ResponseEntity<Void> makeMockFamilyNames() {
		getFamilynames();
		System.err.println("makeMockFamilyNames");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private static <T> List<T> toList(final Iterable<T> iterable) {
	    return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}
	
	@GetMapping("all_male_firstnames")
	public ResponseEntity<List<MaleFirstname>> getAllMaleFirstNames() {
		List<MaleFirstname> list = toList(maleFirstnameRepository.findAll());
		return new ResponseEntity<List<MaleFirstname>>(list, HttpStatus.OK);
	}	
	
	@GetMapping("all_female_firstnames")
	public ResponseEntity<List<FemaleFirstname>> getAllFemaleFirstNames() {
		List<FemaleFirstname> list = toList(femaleFirstnameRepository.findAll());
		return new ResponseEntity<List<FemaleFirstname>>(list, HttpStatus.OK);
	}	
	
	@GetMapping("all_family_names")
	public ResponseEntity<List<FamilyName>> getAllFamilyNames() {
		List<FamilyName> list = toList(familyNameRepository.findAll());
		return new ResponseEntity<List<FamilyName>>(list, HttpStatus.OK);
	}	
	
	private void getMaleFirstnames() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("male_first_names.txt").getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				maleFirstnameRepository.save(new MaleFirstname(UUID.randomUUID(), line));
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private void getFemaleFirstnames() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("female_first_names.txt").getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				femaleFirstnameRepository.save(new FemaleFirstname(UUID.randomUUID(), line));
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	private void getFamilynames() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("dutch_family_names.txt").getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				familyNameRepository.save(new FamilyName(UUID.randomUUID(), line));
				//System.out.println(line);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
