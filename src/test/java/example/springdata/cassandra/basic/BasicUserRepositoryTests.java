/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.cassandra.basic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Version;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.Session;

import nl.rossie.employee.BasicConfiguration;
import nl.rossie.employee.BasicUserRepository;
import nl.rossie.employee.entity.Employee;

//import example.springdata.cassandra.util.CassandraKeyspace;
//import example.springdata.cassandra.util.CassandraVersion;

/**
 * Integration test showing the basic usage of {@link BasicUserRepository}.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 * @author Christoph Strobl
 * @author Mark Paluch
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BasicConfiguration.class)
public class BasicUserRepositoryTests {

	public final static Version CASSANDRA_3_4 = Version.parse("3.4");

	@ClassRule public final static CassandraKeyspace CASSANDRA_KEYSPACE = CassandraKeyspace.onLocalhost();

	@Autowired BasicUserRepository repository;
	@Autowired Session session;
	Employee user;

	@Before
	public void setUp() {

		user = new Employee();
		user.setId(42L);
		user.setUsername("foobar");
		user.setFirstname("firstname");
		user.setLastname("lastname");
	}

	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra.
	 */
	@Test
	public void findSavedUserById() {

		user = repository.save(user);
		Optional<Employee> founduser = repository.findById(user.getId());
		System.out.println(founduser.get());
		assertThat(founduser.get().getId().equals(user.getId()));
	}

	/**
	 * Cassandra can be queries by using query methods annotated with {@link @Query}.
	 */
	@Test
	public void findByAnnotatedQueryMethod() {

		repository.save(user);

		assertThat(repository.findUserByIdIn(1000)).isNull();
		Employee found = repository.findUserByIdIn(42);
		assertThat(found.getId().equals(user.getId()));
	}

	/**
	 * Spring Data Cassandra supports query derivation so annotating query methods with
	 * {@link org.springframework.data.cassandra.repository.Query} is optional. Querying columns other than the primary
	 * key requires a secondary index.
	 */
	@Test
	public void findByDerivedQueryMethod() throws InterruptedException {

		session.execute("CREATE INDEX IF NOT EXISTS user_username ON users (uname);");
		/*
		  Cassandra secondary indexes are created in the background without the possibility to check
		  whether they are available or not. So we are forced to just wait. *sigh*
		 */
		Thread.sleep(1000);

		repository.save(user);

		Employee found = repository.findUserByUsername(user.getUsername());
		assertThat(found.getUsername().equalsIgnoreCase(user.getUsername()));
	}

	/**
	 * Spring Data Cassandra supports {@code LIKE} and {@code CONTAINS} query keywords to for SASI indexes.
	 */
	@Test
	public void findByDerivedQueryMethodWithSASI() throws InterruptedException {

		System.err.println("Cassandra version " + CassandraVersion.getReleaseVersion(session));
		assumeTrue(CassandraVersion.getReleaseVersion(session).isGreaterThanOrEqualTo(CASSANDRA_3_4));

		//session.execute("CREATE CUSTOM INDEX IF NOT EXISTS ON users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex';");
		
		session.execute("CREATE CUSTOM INDEX IF NOT EXISTS ON users (lname) USING 'org.apache.cassandra.index.sasi.SASIIndex' WITH OPTIONS = { 'mode': 'CONTAINS' };");
		
/*		CREATE CUSTOM INDEX  fn_contains ON cyclist_name (firstname) 
		USING 'org.apache.cassandra.index.sasi.SASIIndex'
		WITH OPTIONS = { 'mode': 'CONTAINS' };
*/		
		
		/*
		  Cassandra secondary indexes are created in the background without the possibility to check
		  whether they are available or not. So we are forced to just wait. *sigh*
		 */
		Thread.sleep(1000);

		repository.save(user);
		createMoreUsers();

		List<Employee> found = repository.findUsersByLastnameContains("name");
		for (Employee usr : found) {
			System.err.println("Gevonden: " + usr);
		}
		assertThat(found.size() > 0);
		
		
/*		List<User> found2 = repository.findUsersByLastnameContains("stname");
		for (User usr : found2) {
			System.err.println(usr);
		}
		assertThat(found.size() > 0);
*/		
	}

	private void createMoreUsers() {
		Employee user2 = new Employee(Calendar.getInstance().getTimeInMillis() );
		user2.setUsername("foob");
		user2.setFirstname("marcel");
		user2.setLastname("last christmass");
		repository.save(user2);
		
		user2.setId(Calendar.getInstance().getTimeInMillis());
		user2.setUsername("aaa");
		user2.setFirstname("bert");
		user2.setLastname("Janssen");
		repository.save(user2);		
	}
}