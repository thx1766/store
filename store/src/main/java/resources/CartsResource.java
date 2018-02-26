package resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.Carts;
import model.CustomerProducts;
import services.CartsService;
/**
 * 
 * @author nschaffner
 *
 */

@Path("/carts")
public class CartsResource {
	/**
	 *carts service instantiated to access service methods 
	 */
	CartsService service = new CartsService();
	
	/**
	 * add a cart
	 * @param id
	 * @return
	 */
	public int addCartNonRest(int id) {
		 
		int cartid = service.addCart(id);
		return cartid;
	}
	
	/**
	 * list carts
	 * @return
	 */
	public List<Carts> listCartsNonRest(){
		List<Carts> mylist = service.listCarts();
		return mylist;
	}
	
	/**
	 * gat cart id from user id
	 * @param userid
	 * @return
	 */
	public int getCartFromUserid(int userid) {
		return service.getCartFromUserid(userid);
	}
	/**
	 * get user id associated with cart id
	 * @param cartid
	 * @return
	 */
	public int getUseridFromCartid(int cartid) {
		return service.getUseridFromCartid(cartid);
	}
	
	/**
	 * list cart contents
	 * @param cartid
	 * @return
	 */
	public List<CustomerProducts> listCartContents(int cartid) {
		List<CustomerProducts> mylist = service.listCartContents(cartid);
		return mylist;
	}
	
	/**
	 * add cart item
	 * @param cart_id
	 * @param product_id
	 * @param quantity
	 * @return
	 */
	public String addCartItemNonRest(int cart_id, int product_id, int quantity) {
		return service.addCartItem(cart_id,product_id,quantity);
	}
	
	/**
	 * delete cart item
	 * @param cart_id
	 * @param product_id
	 * @return
	 */
	public String deleteCartItemNonRest(int cart_id, int product_id) {
		return service.deleteCartItem(cart_id, product_id);
	}
	
	/**
	 * update cart item
	 * @param cartid
	 * @param productid
	 * @param quantity
	 * @return
	 */
	public String updateCartItemNonRest(int cartid, int productid, int quantity) {
		
		return service.UpdateCartItem(cartid, productid, quantity);
	}
	

	/**
	 * update cart
	 * @param cartid
	 * @param customerid
	 * @return
	 */
	public String updateCartNonRest(int cartid, int customerid) {
		return service.UpdateCart(cartid, customerid);
	}
	
	/**
	 * delete a cart
	 * @param cartid
	 * @return
	 */
	public String deleteCartRest(int cartid) {
		return service.DeleteCart(cartid);
	}
}
