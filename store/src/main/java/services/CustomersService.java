package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Customers;

public class CustomersService {
	
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	//persistence unit name for connecting to the database
    EntityManagerFactory factory;
    //factory for creating entity manager
    static Logger logger = LoggerFactory.getLogger(CustomersService.class);
    //logger set to class for CustomersService method logging
    
    
	public String addCustomer(String firstname, String lastname, String email, String gender, String username) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//factory to create entity manager
	    EntityManager em = factory.createEntityManager();
	    //create entity manager
	    em.getTransaction().begin();
	    //begin transaction
	    Customers newCustomer = new Customers();
	    //create new Customers entity
	    newCustomer.setFirstname(firstname);
	    //set first name of customer
	    newCustomer.setLastname(lastname);
	    //set last name of customer
	    newCustomer.setEmail(email);
	    //set email of customer
	    newCustomer.setGender(gender);
	    //set gender of customer
	    newCustomer.setUsername(username);
	    //set customer's username
	    em.persist(newCustomer);
	    //persist new customer to the database
	    em.getTransaction().commit();
	    //commit transaction
	    em.close();
	    //clsoe entity manager
	    logger.info("customer "+username+" added");
	    //log customer creation
	return "<html><body>User "+username+" added sucessfully!<br><a href='/store/testlogin1.jsp'>Login</a></body></html>";
	//return success message with link to login
	}

	@SuppressWarnings("unchecked")
	public List<Customers> listCustomers() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate factory for entity manager creation
		EntityManager em = factory.createEntityManager();
		//create entity manager
		Query q = em.createQuery("select t from Customers t", Customers.class);
		//query to select customers from Customers table
		List<Customers> todoList = q.getResultList();
		//create a list of customer from query result
		em.close();
		//close entity manager
		logger.info("customers listed");
		//log that customer have been listed
		return todoList;
		//return list of customer entities
	}

	public Object UpdateCustomer(int id, String first, String last) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate factory to get an entity manager
		EntityManager em = factory.createEntityManager();
		//create entity manager
		List<Customers> test = em.createQuery("select t from Customers t where t.customer_id = ?1", Customers.class).setParameter(1, id).getResultList();
		//get a list of Customers entities from the database for the specified customer id
		if(test.size()!=1){
			//error expected list size is 1
			em.close();
			//close entity manager
			logger.error("error updating customer with customer id:"+id);
			//log the error
			return null;
			//error condition
		}
		else
		{
			//list contains 1 Customers entity
			em.getTransaction().begin();
			//begin transaction
			Customers newCustomer = test.get(0);
			//get Customer entity from list
			newCustomer.setFirstname(first);
			//set updated first name
			newCustomer.setLastname(last);
			//set updated last name
			em.persist(newCustomer);
			//persist the updated customer entity
			em.getTransaction().commit();
			//commit the transaction
			em.close();
			//close the entity manager
			logger.info("customer id: "+id+" updated firstname: "+first+" lastname: "+last);
			//log the updated customer data
			return newCustomer.toString();
			//return string representation of updated customer
		}
	}

	public Object DeleteCustomer(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate factory for creating an entity manager
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		try {
		Customers result =em.createQuery("select t from Customers t where t.customer_id = ?1", Customers.class).setParameter(1, id).getSingleResult(); 
		//create a Customers entity to find the customer in the database corresponding to the supplied customer id
		em.getTransaction().begin();
		//begin transaction
		em.remove(result);
		//remove the customer from the database
		em.getTransaction().commit();
		//commit the transaction
		em.close();
		//close the entity manager
		logger.info("customer with id: "+id+" deleted");
		//log that the customer of the specified id has been deleted
		}catch(Exception e) {
			//catch error condition for bad userid
			return "Could not find customer with id:"+id+"<br>Please supply a valid user id<br><a href='/store/deleteCustomer.jsp'>Delete Customer</a>";
			//return an error message
		}
		
		return "deleted"+id;
		//return the id of the deleted customer
	 }
}



