package services;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Customers;

public class LoginService {
	
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	//persistence unit name for the database connection
    EntityManagerFactory factory;
    //factory for creating the entity manager
    static Logger logger = LoggerFactory.getLogger(LoginService.class);
    //setup the logger for logging LoginService class methods
    
	public int login(String usernamein) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate the factory for creating an entity manager
		 EntityManager em = factory.createEntityManager();
		 //create an entity manger
		 em.getTransaction().begin();
		 //begin the transaction
		  TypedQuery<Customers> q = em.createQuery("select t from Customers t where t.username=?1",Customers.class).setParameter(1, usernamein).setMaxResults(1);
		  //find the user in the database by querying Customers table with the supplied username
		 List<Customers> myCustomers = q.getResultList();
		 //list of customers returned from the query
		 if(myCustomers.size()==0){
			 //if there are no results this is an error condition
			 return -1;
			 //return userid -1 to signal error
		 }
		 Customers myCustomer = myCustomers.get(0);
		 //get the customer from the list
		 String username = myCustomer.getUsername();
		 //get the customer's username
		 int userid = myCustomer.getCustomer_id();
		 //get the customer's customer id number
		 logger.info("user: "+userid+"(username:"+username+") logged in");
		 //log that a user logged in
		return userid;
		//return the user's userid
	}
	
	public void logout(){
		//implemented in UI not service
		logger.info("user logged out");
		//log that a user logged out
	}
	
	

}
