package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author nschaffner
 *
 */
@Entity
@Table(name="Customers")
public class Customers {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * primary key
	 */
    private int customer_id;
	/**
	 * customer first name
	 */
	private String firstname;
	/**
	 * customer last name
	 */
	private String lastname;
	/**
	 * customer email address
	 */
	private String email;
	/**
	 * customer gender
	 */
	private String gender;
	/**
	 * customer username
	 */
	private String username;
	
	/**
	 * getter for customer id
	 * @return customer id
	 */
	public int getCustomer_id() {
		return customer_id;
	}
	/**
	 * setter for customer id
	 * @param customer_id
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * getter for first name
	 * @return first name
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * setter for first name
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * getter for last name
	 * @return last name
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * setter for last name
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * getter for email
	 * @return email address
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * setter for email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * getter for customer gender
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * setter for gender
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * getter for username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * setter for username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
