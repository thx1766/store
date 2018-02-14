package resources;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.Customers;
import services.CustomersService;


@Path("/customer")
public class CustomersResource {
	
	CustomersService service = new CustomersService();
	//service for interacting with database
	@POST
	@Path("/add")
	public String addCustomer(
			@FormParam("firstName") String firstname,
			//first name from form
			@FormParam("lastName") String lastname,
			//last name from form
			@FormParam("email") String email,
			//email address from form
			@FormParam("gender") String gender,
			//gender from form
			@FormParam("username") String username
			//username from form
			){
		return service.addCustomer(firstname,lastname,email,gender,username);
		//return response from service call to add customer
		
	}
	
	@GET
	@Path("/list")
	public String listCustomers(){
		List<Customers> mylist = service.listCustomers();
		//list of customers from database supplied by service
		String response="";
		//string to hold response 
		for(Customers table: mylist){
			response+=table.getCustomer_id()+": "+table.getFirstname()+" "+table.getLastname()+"<BR>\n";
			//for each usre list first and last name
		}
		response+="<BR><a href='/store/deleteCustomer.jsp'>Delete Customers</a>";
		return response;
		//return constructed response
	}
	
	@POST
	@Path("/update")
	public Response updateCustomer(
			@FormParam("customerID") int id,
			//customer id from form
			@FormParam("firstName") String firstname,
			//first name from form
			@FormParam("lastName") String lastname
			//last name from form
			){
		return Response.status(201).entity(service.UpdateCustomer(id,firstname,lastname)).build();
		//return html response from service call to update customer
		
	}
	
	@POST
	@Path("/delete")
	public String deleteCustomer(
			@FormParam("customerID") int id
			//customer id from form
			){
		String retval="<html><body>";
			retval+=service.DeleteCustomer(id);
			retval+="<BR><a href='/store/listCustomers.jsp'>List Customers</a>";
			retval+="</body></html>";
		return retval;
		//return html Response containing result of delete call on service
	}

}