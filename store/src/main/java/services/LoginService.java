package services;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Customers;


/**
 * 
 * @author nschaffner
 *
 */
public class LoginService {
	/**
	 *persistence unit name for the database connection 
	 */
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	/**
	 *factory for creating the entity manager 
	 */
    EntityManagerFactory factory;
    /**
     *setup the logger for logging LoginService class methods 
     */
    static Logger logger = LoggerFactory.getLogger(LoginService.class);
    
    /**
     * service method backing rest endpoint for user login
     * @param usernamein
     * @return return the user's userid
     */
	public int login(String usernamein) {
		/**
		 *instantiate the factory for creating an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 * create an entity manger
		 */
		 EntityManager em = factory.createEntityManager();
		 /**
		  *begin the transaction 
		  */
		 em.getTransaction().begin();
		 /**
		  * find the user in the database by querying Customers table with the supplied username
		  */
		  TypedQuery<Customers> q = em.createQuery("select t from Customers t where t.username=?1",Customers.class).setParameter(1, usernamein).setMaxResults(1);
		 /**
		  *list of customers returned from the query 
		  */
		 List<Customers> myCustomers = q.getResultList();
		 /**
		  * if there are no results this is an error condition
		  */
		 if(myCustomers.size()==0){
			 /**
			  * return userid -1 to signal error
			  */
			 return -1;
		 }
		 /**
		  * get the customer from the list
		  */
		 Customers myCustomer = myCustomers.get(0);
		 /**
		  * get the customer's username
		  */
		 String username = myCustomer.getUsername();
		 /**
		  * get the customer's customer id number
		  */
		 int userid = myCustomer.getCustomer_id();
		 /**
		  * log that a user logged in
		  */
		 logger.info("user: "+userid+"(username:"+username+") logged in");
		 
/////
			//String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
			//EntityManagerFactory factory;
			//factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			//EntityManager em = factory.createEntityManager();
			// em.getTransaction().begin();
			//  TypedQuery<Customers> q = em.createQuery("select t from Customers t where t.username=?1",Customers.class).setParameter(1, name).setMaxResults(1);
			// List<Customers> myCustomers = q.getResultList();
			// int userpersistedid=-1;
			// if(myCustomers.size()==0){
			//	 userpersistedid =  -1;
			// }
			// try{
			//	 Customers myCustomer = myCustomers.get(0);
			//	 String username = myCustomer.getUsername();
			//	 userpersistedid = myCustomer.getCustomer_id();
			// }catch(Exception e){
			//	 session.setAttribute( "userid", -1 );
			// }
/////
		return userid;
	}
	
	/**
	 * service method backing rest call for user logout
	 */
	public void logout(){
		//implemented in UI not service
		logger.info("user logged out");
		//log that a user logged out
	}

	public int userid(String username) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
