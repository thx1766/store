package resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.Carts;
import model.Customers;
import model.Products;
import services.ProductsService;
@Path("/products")
public class ProductsResource {
	
	ProductsService service = new ProductsService();
	
	@POST
	@Path("/add")
	public String addProduct(
			@FormParam("productName") String productName,
			//product name from form
			@FormParam("price") double price,
			//product price from form
			@FormParam("quantity") int quantity
			//product quantity from form
			){
		if(price<=0) {
			//error condition
			return "Price must be a positive value<br><a href='/store/addProduct.jsp'>Return</a>";
			//display error message and link to add products
		}
		if(quantity<=0) {
			//error condition
			return "Quantity must be a positive value<br><a href='/store/addProduct.jsp'>Return</a>";
			//display error message and link to add products
		}
		return service.addProduct(productName,price,quantity);
		//return the result of the service call
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/list")
	public String listProducts(
			@FormParam("userid") int userid
			//userid from form
			){
		List<Products> mylist = service.listProducts();
		//list of products from the service
		String response="";
		//string to hold response
		String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
		//persistence unit name for accessing the database
	    EntityManagerFactory factory;
	    //factory for creating an entity manager
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    //instantiate factory for creating an entity manager
	    EntityManager em = factory.createEntityManager();
	    //create the entity manager
	    Query q = em.createQuery("select t from Customers t where t.customer_id = ?1").setParameter(1, userid);
	    //query to get customer with specified id
	    List<Customers> customerlist = q.getResultList();
	    //list of one customer corresponding to specified id
	    Customers mycustomer = customerlist.get(0);
	    //customer extracted from list
	    Query q2 = em.createQuery("select t from Carts t where t.customer=?1").setParameter(1, mycustomer);
	    //query to get cart corresponding to customer
	    List<Carts> cartlist = q2.getResultList();
	    //list of carts from query
	    Carts mycart;
	    //cart entity
	    try {
	    mycart = cartlist.get(0);
	    //try to get the first element
	    }catch(Exception e) {
	    	//the cart does not exist, provide a link to create one
	    	return "<html><body>You must create a cart first!<br><a href='/store/addCart.jsp'>Create a cart</a></body></html>";
	    }
	    int mycartid = mycart.getCart_id();
	    //cart id from query
		for(Products table: mylist){
			//iterate through products in list
			response+="<form method='POST' action='/store/webapi/carts/addCartItem/'>"
					+"<b>"+table.getProductName()+":</b>"
					+/*"<BR>Product ID:"+*/"<input type='hidden' name='productID' size='4' value="+table.getProduct_id()+">"+"<BR>Price:"+table.getPrice()+"<BR>"
					+"Quantity: "+"<input type='text' name='quantity' size='4' value='1'>"
					+/*"CartID:"+*/"<input type='hidden' name='cartID' size='4' value='"+mycartid+"'>"
					+"<br>Inventory Quantity: "+table.getQuantity()+"<br>"
					+"<button type='submit' >Add To Cart</button></form>"
					+"<BR>";
					//form to add products to cart
		}
		response+="<br><form enctype='application/x-www-form-urlencoded' method='POST' action='/store/webapi/carts/listItems'>"
				+"<input type='hidden' name = 'cartID' id='cartID' value='"+mycartid+"'>"
				+"<button type='submit' >List Cart Items</button>"
				+"</form>"
				+"<br><form action='/store/customers.jsp'><input type='submit' value='Customer Menu' /></form>";
		return response;
		//return constructed response
	}

	@POST
	@Path("/update")
	public Response updateProduct(
			@FormParam("productID") int product_id,
			//product id from form
			@FormParam("productName") String productName,
			//product name from form
			@FormParam("price") double price,
			//price from form
			@FormParam("quantity") int quantity
			//quantity from form
			){
		return Response.status(201).entity(service.UpdateProduct(product_id,productName,price,quantity)).build();
		//return an html Response object with the result of the call to update product with service
	}
	
	@POST
	@Path("/delete")
	public Response deleteProduct(
			@FormParam("productID") int id
			//product id from form
			){
		return Response.status(201).entity(service.DeleteProduct(id)).build();
		//return an html Response with the result of the delete service call
	}
}