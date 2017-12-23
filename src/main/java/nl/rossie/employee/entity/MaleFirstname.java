package nl.rossie.employee.entity;

import java.util.UUID;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value = "male_first_names")
public class MaleFirstname {

	@PrimaryKey("id") private UUID id;
	@Column("name") private String name;
	
	
	public MaleFirstname(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MaleFirstname [id=" + id + ", name=" + name + "]";
	}
	
}
