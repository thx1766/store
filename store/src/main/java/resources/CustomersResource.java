package resources;


import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.Customers;
import services.CustomersService;
/**
 * 
 * @author nschaffner
 *
 */

@Path("/customer")
public class CustomersResource {
	/**
	 *service for interacting with database 
	 */
	CustomersService service = new CustomersService();
	
	/**
	 * add a customer
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param gender
	 * @param username
	 * @return
	 */
	public String addCustomerNonRest(String firstname, String lastname, String email, String gender, String username) {
		return service.addCustomer(firstname,lastname,email,gender,username);
	}
	
	
	/**
	 * list customers
	 * @return
	 */
	public List<Customers> listCustomersNonRest(){
		return service.listCustomers();
	}
	
	
	/**
	 * update a customer
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	public String updateCustomerNonRest(int id, String firstname, String lastname) {
		return service.UpdateCustomer(id,firstname,lastname);
	}
	
	/**
	 * delete a customer
	 * @param id
	 * @return
	 */
	public String deleteCustomerNonRest(int id) {
		return service.DeleteCustomer(id);
	}
}