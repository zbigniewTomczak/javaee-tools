package org.jpa.sql.injection.tester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;

@Entity
@NamedQuery(name="User.getByName", query="SELECT u FROM User u WHERE u.name=:name")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@OrderBy
	@Column(nullable=false, unique=true)
	String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
