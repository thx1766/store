package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import services.LoginService;
import transferObjects.loginTransferObject;
/**
 * 
 * @author nschaffner
 *
 */
@Path("/loginservice")
public class LoginResource {
	
	/**
	 * login a user
	 * @param username
	 * @return
	 */
	public loginTransferObject loginmethod(String username) {
		loginTransferObject retval = new loginTransferObject();
		
		LoginService service = new LoginService();
		
		retval.setUserid(service.login(username));
		retval.setUsername(username);
		
		return retval;
		
	}

}
