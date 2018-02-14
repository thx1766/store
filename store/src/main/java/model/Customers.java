package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customers")
public class Customers {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
	//primary key
	private String firstname;
	//customer first name
	private String lastname;
	//customer last name
	private String email;
	//customer email
	private String gender;
	//customer gender
	private String username;
	//customer username
	
	public int getCustomer_id() {
		//getter for customer id
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		//setter for customer id
		this.customer_id = customer_id;
	}
	public String getFirstname() {
		//getter for first name
		return firstname;
	}
	public void setFirstname(String firstname) {
		//setter for first name
		this.firstname = firstname;
	}
	public String getLastname() {
		//getter for last name
		return lastname;
	}
	public void setLastname(String lastname) {
		//setter for last name
		this.lastname = lastname;
	}
	public String getEmail() {
		//getter for email
		return email;
	}
	public void setEmail(String email) {
		//setter for email
		this.email = email;
	}
	public String getGender() {
		//getter for gender
		return gender;
	}
	public void setGender(String gender) {
		//setter for gender
		this.gender = gender;
	}
	public String getUsername() {
		//getter for username
		return username;
	}
	public void setUsername(String username) {
		//setter for username
		this.username = username;
	}
	
}
