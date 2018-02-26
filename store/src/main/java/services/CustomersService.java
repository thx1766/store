package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Customers;

/**
 * 
 * @author nschaffner
 *
 */

public class CustomersService {
	/**
	 *persistence unit name for connecting to the database 
	 */
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	/**
	 *factory for creating entity manager 
	 */
    EntityManagerFactory factory;
    /**
     *logger set to class for CustomersService method logging 
     */
    static Logger logger = LoggerFactory.getLogger(CustomersService.class); 
    
    /**
     * Service method backing rest endpoint to add a customer
     * @param firstname
     * @param lastname
     * @param email
     * @param gender
     * @param username
     * @return return success message with link to login
     */
	public String addCustomer(String firstname, String lastname, String email, String gender, String username) {
		/**
		 * factory to create entity manager
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create entity manager 
		 */
	    EntityManager em = factory.createEntityManager();
	    /**
	     *begin transaction 
	     */
	    em.getTransaction().begin();
	    /**
	     *create new Customers entity 
	     */
	    Customers newCustomer = new Customers();
	    /**
	     *set first name of customer 
	     */
	    newCustomer.setFirstname(firstname);
	    /**
	     *set last name of customer 
	     */
	    newCustomer.setLastname(lastname);
	    /**
	     *set email of customer 
	     */
	    newCustomer.setEmail(email);
	    /**
	     *set gender of customer 
	     */
	    newCustomer.setGender(gender);
	    /**
	     *set customer's username 
	     */
	    newCustomer.setUsername(username);
	    /**
	     *persist new customer to the database 
	     */
	    em.persist(newCustomer);
	    /**
	     *commit transaction 
	     */
	    em.getTransaction().commit();
	    /**
	     *close entity manager 
	     */
	    em.close();
	    /**
	     *log customer creation 
	     */
	    logger.info("customer "+username+" added");
	return username+" added sucessfully!";
	}

	
	@SuppressWarnings("unchecked")
	/**
	 * service backing rest method to list customers
	 * @return return list of customer entities
	 */
	public List<Customers> listCustomers() {
		/**
		 *instantiate factory for entity manager creation 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *query to select customers from Customers table 
		 */
		Query q = em.createQuery("select t from Customers t", Customers.class);
		/**
		 *create a list of customer from query result 
		 */
		List<Customers> todoList = q.getResultList();
		/**
		 *close entity manager 
		 */
		em.close();
		/**
		 *log that customer have been listed 
		 */
		logger.info("customers listed");
		return todoList;
	}

	/**
	 * service backing rest endpoint method to update customer
	 * @param id
	 * @param first
	 * @param last
	 * @return return string representation of updated customer
	 */
	public String UpdateCustomer(int id, String first, String last) {
		/**
		 *instantiate factory to get an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *get a list of Customers entities from the database for the specified customer id 
		 */
		List<Customers> test = em.createQuery("select t from Customers t where t.customer_id = ?1", Customers.class).setParameter(1, id).getResultList();
		/**
		 * error expected list size is 1
		 */
		if(test.size()!=1){
			/**
			 *close entity manager 
			 */
			em.close();
			/**
			 *log the error 
			 */
			logger.error("error updating customer with customer id:"+id);
			/**
			 * error condition
			 */
			return null;
		}
		/**
		 *list contains 1 Customers entity 
		 */
		else
		{
			/**
			 * begin transaction
			 */
			em.getTransaction().begin();
			/**
			 *get Customer entity from list 
			 */
			Customers newCustomer = test.get(0);
			/**
			 *set updated first name 
			 */
			newCustomer.setFirstname(first);
			/**
			 *set updated last name 
			 */
			newCustomer.setLastname(last);
			/**
			 *persist the updated customer entity 
			 */
			em.persist(newCustomer);
			/**
			 *commit the transaction 
			 */
			em.getTransaction().commit();
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 *log the updated customer data 
			 */
			logger.info("customer id: "+id+" updated firstname: "+first+" lastname: "+last);
			return newCustomer.toString();
		}
	}

	/**
	 * service method backing rest endpoint to delete customer
	 * @param id
	 * @return return the id of the deleted customer
	 */
	public String DeleteCustomer(int id) {
		/**
		 *instantiate factory for creating an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		try {
			/**
			 *create a Customers entity to find the customer in the database corresponding to the supplied customer id 
			 */
			Customers result =em.createQuery("select t from Customers t where t.customer_id = ?1", Customers.class).setParameter(1, id).getSingleResult(); 
			/**
			 *begin transaction 
			 */
			em.getTransaction().begin();
			/**
			 *remove the customer from the database 
			 */
			em.remove(result);
			/**
			 *commit the transaction 
			 */
			em.getTransaction().commit();
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 *log that the customer of the specified id has been deleted 
			 */
			logger.info("customer with id: "+id+" deleted");
		}
		/**
		 * catch exception user has a cart still
		 */
		catch(RollbackException e) {
			/**
			 * return an error message
			 */
			return "You must delete this user's cart first<br><a href='/store/listCarts.jsp'>List Carts</a><br><a href='/store/deleteCart.jsp'>Delete Carts</a>";
		}
		/**
		 * catch exception user does not exist
		 */
		catch(Exception e) {
			/**
			 * return an error message
			 */
			return "Could not find customer with id:"+id+" Please supply a valid user id!";
		}
		return "deleted"+id;
	 }
}



