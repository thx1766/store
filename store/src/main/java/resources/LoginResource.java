package resources;

import java.net.URI;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import services.LoginService;

@Path("/loginservice")
public class LoginResource {
	
	@Path("/login")
	@POST
	public Response loginMethod(
			@FormParam("username") String username
			//username from form
			){
		LoginService service = new LoginService();
		//create login service
		int userid = service.login(username);
		//lookup user id integer from username with service
		URI uri = UriBuilder.fromPath("/store/customers.jsp").queryParam("userid", userid).build();
		//return to the customers page and set the userid in the session
		return Response.seeOther(uri).build();
		//return the html Response object to redirect to customers page
	}
	
	@Path("/logout")
	@POST
	public void logoutmethod(){
		LoginService service = new LoginService();
		//service to enable logout
		service.logout();
		//call to log out with service
	}

}
