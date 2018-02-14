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


@Path("/carts")
public class CartsResource {
	CartsService service = new CartsService();
	//carts service instantiated to access service methods
	@POST
	@Path("/add")
	public String addCart(
			@FormParam("customerID") int id
			//parameter customer ID from form
			){
		int cartid = service.addCart(id);
		//get cartid of cart created by service for user id specified
		return "<html><body>Cart added:"+cartid+"<br><a href='/store/listProducts.jsp'>List Products</a></body></html>";
		//return html envelope with created cart and link to prodicts display page
	}
	
	@GET
	@Path("/list")
	public String listCarts(){
		List<Carts> mylist = service.listCarts();
		//get a list of carts from the carts service
		String response="";
		//String to hold return value
		for(Carts table: mylist){
			//iterate through list of carts
			response+="CartID: "+table.getCart_id()+" CustomerID: "+table.getCustomer().getCustomer_id()+"<BR>\n";
			//append cart id and customer id to response for each cart
		}
		response+="<BR><a href='/store/deleteCart.jsp'>Delete carts</a>";
		response+="<BR><a href='/store/listProductsAdmin.jsp'>Admin List Products</a>";
		response+="<BR><a href='/store/listCartItemsAdmin.jsp'>Admin List Cart Items</a>";
		return response;
		//send constructed response back
	}
	
	@POST
	@Path("/listItems")
	public String listCartItems(
			@FormParam("cartID") int cartId
			//parameter cart id from form
			){
		//
		//
		//access database to lookup userid from cartid
		int userid=-1;
		String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
		//persistance unit name required for entity management
	    EntityManagerFactory factory;
	    //factory for creating entity managers
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate a factory to get an entity manager from
	    EntityManager em = factory.createEntityManager();
	    //create an entity manager
	    em.getTransaction().begin();
	    Query q = em.createQuery("select t from Carts t where t.cart_id=?1",Carts.class).setParameter(1,cartId);
	    @SuppressWarnings("unchecked")
		List<Carts> list = q.getResultList();
	    userid = list.get(0).getCustomer().getCustomer_id();
		//
		//
		List<CustomerProducts> mylist = service.listCartContents(cartId);
		//get a list of items from cart with specified id
		String response="";
		//String to hold response
		if(mylist==null) {
			response+= "<html><body><BR>Empty list<BR><a href='/store/listProducts.jsp'>View Products<a></body></html>";
			//if there are no items in the cart display empty list and a link to the products page
		}
		else{
			response+="<html><body><table><tr>";
			//open the html envelope
		for(CustomerProducts table: mylist){
			//for each item in the cart
			response+=
					"<td>"
					+table.getProductName()
					//list product name
					+"<BR>price: $"+table.getPrice()
					//list price of item
					+"<BR>item subtotal: $"+table.getPrice()*table.getQuantity()+"<BR>\n"
					//list item subtotal
					+"<form enctype='application/x-www-form-urlencoded' method='POST' action='/store/webapi/carts/deleteCartItem\'>"
					+"<input type='hidden' name = 'cartID' value='"+cartId+"'>"
					+"<input type='hidden' name = 'productID' value='"+table.getProduct_id()+"'>"
					+"<button type='submit' >Delete From Cart</button>" 
					+"</form>"
					//form for deleting a product from the cart
					+"<form enctype=\"application/x-www-form-urlencoded\" method=\"POST\" action=\"/store/webapi/carts/updateCartItem\">"
					+"<input type='hidden' name = 'cartID' value='"+cartId+"'>"
					+"<input type='hidden' name = 'productID' value='"+table.getProduct_id()+"'>"
					+"Quantity:<input type='text' size=4 name = 'quantity' value='"+table.getQuantity()+"'>"
					+"<button type='submit' >Update Quantity</button>"
					+"</form></td>";
					//form for updating quantity of a product
			}
		response+="</tr></table><BR>"
			+"<form enctype=\"application/x-www-form-urlencoded\" method=\"POST\" action='/store/webapi/products/list'>"
				+"<input type='hidden' name='userid' value='"+userid+"'>"
				+"<button type='submit' >List Products</button>"
				+"</form>"
				+"<form action='/store/customers.jsp'><input type='submit' value='Customer Menu' /></form>"
				+"</body></html>";
		//add a link to product display page and close the html envelope
		}
		return response;
		//return the constructed response
	}
	@POST
	@Path("/addCartItem")
	public String addCartItem(
			@FormParam("cartID") int cart_id,
			//cart id parameter from form
			@FormParam("productID") int product_id,
			//product id from form
			@FormParam("quantity") int quantity
			//quantity from form
			){
		if(quantity <= 0 ) {
			//error condition invalid quantity
			return "Quantity must be greater than 0<br><a href='/store/listCartItems.jsp'>Return to List</a>";
			//return error message with link to list cart items
		}
		
		String retval="";
		//String to hold return value
		retval+=service.addCartItem(cart_id,product_id,quantity);
		//call the service and save the returned data to the return value
		return "<html><body>"+retval+"<br><form enctype='application/x-www-form-urlencoded' method='POST' action='/store/webapi/carts/listItems'><input type='hidden' name = 'cartID' id='cartID' value="+cart_id+"><button type='submit' >View Cart</button></form></body></html>";
		//build html envelope and add returned value from service with form to view cart
	}
	
	@POST
	@Path("/deleteCartItem")
	public String deleteCartItem(
			@FormParam("cartID") int cart_id,
			//cart id from the form
			@FormParam("productID") int product_id
			//product id from the form
			){
		String retval="";
		//String to hold return value
		retval+="<html><body>"
				//html envelope
				+"Item deleted:<br>"
				//newline
				+service.deleteCartItem(cart_id, product_id)
				//result of delete operation from service
				+"<br>"
				//newline
				+"<form enctype='application/x-www-form-urlencoded' method='POST' action='/store/webapi/carts/listItems'><input type='hidden' name = 'cartID' id='cartID' value="+cart_id+"><button type='submit' >View Cart</button></form>"
				//form to view cart
				+"</body></html>";
				//close html envelope
		return retval;
		//return constructed result page
				
	}
	
	@POST
	@Path("/updateCartItem")
	public String updateCartItem(
			@FormParam("cartID") int cartid,
			//cart id from form
			@FormParam("productID") int productid,
			//product id from form
			@FormParam("quantity") int quantity
			//quantity from form
			){
		
		if(quantity <= 0) {
			//invalid quantity
			return "Quantity must be greater then 0<br><a href='/store/listCartItems.jsp'>Return to Cart</a>";
			//return error message
		}
		String retval="";
		//string to hold return value
		retval+="<html><body>";
		//open html envelope
		retval+=service.UpdateCartItem(cartid, productid, quantity);
		//result of service call to update cart item
		retval+="<br>";
		//newline
		retval+="<form enctype='application/x-www-form-urlencoded' method='POST' action='/store/webapi/carts/listItems'><input type='hidden' name = 'cartID' id='cartID' value="+cartid+"><button type='submit' >View Cart</button></form>";
		//form to view cart
		retval+="</body></html>";
		//close html envelope
		return retval;
		//return constructed response
		
	}
	
	@POST
	@Path("/update")
	public Response updateCart(
			@FormParam("cartID") int cartid,
			//cart id from form
			@FormParam("customerID") int customerid
			//customer id from cart
			){
		return Response.status(201).entity(service.UpdateCart(cartid, customerid)).build();
		//return an html Response object with the contents of the result of the service call
	}
	
	@POST
	@Path("/delete")
	public String deleteCart(
			@FormParam("cartID") int cartid
			//cart id from form
			){
		String retval="<html><body>";
			retval+=service.DeleteCart(cartid);
			retval+="<BR><a href='/store/listCarts.jsp'>List Carts</a>";
			retval+="</body></html>";
		return retval;
		//return an html Response object with the contents of the result of the delete cart service call
	}
}
