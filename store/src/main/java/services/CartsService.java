package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Carts;
import model.CustomerProducts;
import model.Customers;
import model.Products;

public class CartsService {
	
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	//persistance unit name required for entity management
    EntityManagerFactory factory;
    //factory for creating entity managers
    static Logger logger = LoggerFactory.getLogger(CartsService.class);
    //logger set to this class for logging CartsService methods
    
	public int addCart(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate a factory to get an entity manager from
	    EntityManager em = factory.createEntityManager();
	    //create an entity manager
	    em.getTransaction().begin();
	    //begin the transation
	    Carts newCart = new Carts();
	    //create a new carts object
	    Customers newCustomer=new Customers();
	    //create a new customers object
	    newCustomer.setCustomer_id(id);
	    //set the customer id
	    newCart.setCustomer(newCustomer);
	    //set the customer to the cart
	    em.persist(newCart);
	    //persist the cart in the database
	    em.getTransaction().commit();
	    //commit transation
	    em.close();
	    //close entity manager
	    logger.info("cart "+newCart.getCart_id()+" created for customerid: "+id);
	    //log that a new cart was created
	return newCart.getCart_id();
	}

	@SuppressWarnings("unchecked")
	public List<Carts> listCarts() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate a new factory to get an entity manger from
		EntityManager em = factory.createEntityManager();
		//create a new entity manager
		Query q = em.createQuery("select t from Carts t");
		//create  query to get the carts from the database
		List<Carts> todoList = q.getResultList();
		//get a list of carts from the query
		em.close();
		//close the entity manager
		logger.info("carts listed");
		//log that carts were listed
		return todoList;
	}
	
	@SuppressWarnings("unchecked")
	public String addCartItem(int cart_id, int product_id, int quantity) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate a factory to get an entity manager from
	    EntityManager em = factory.createEntityManager();
	    //create an entity manager
	    em.getTransaction().begin();
	    //begin the transaction
	    Query q = em.createQuery("select t from Carts t where t.cart_id = ?1").setParameter(1, cart_id);
	    //query to get cart with specified id from the database
	    Carts myCart;
	    //create a carts object 
	    List<Carts> myList =  q.getResultList();
	    //get a list of carts from the query
	    if (myList.size()==1){
	    	//list size should be 1
	    	myCart = myList.get(0);
	    	//get the first cart from the query
	    	}
	    else{
	    	//no results
	    	return null;
	    	//error condition
	    	}
	    CustomerProducts myProduct = new CustomerProducts();
	    //create a CustomerProducts object to hold customer's purchase
	    Query q2 = em.createQuery("select t from Products t where t.product_id=?1",Products.class).setParameter(1, product_id);
	    //create a query to get the product information from the database
	    Products selectedProduct = (Products) q2.getResultList().get(0);
	    //get the result from the query
	    myProduct.setProductName(selectedProduct.getProductName());
	    //set name of product for customer inventory
	    myProduct.setPrice(selectedProduct.getPrice());
	    //set price of product for customer inventory
	    myProduct.setProduct_id(product_id);
	    //set product id for customer inventory
	    myProduct.setQuantity(quantity);
	    //set quantity for customer inventory
	    int newInvQuantity = selectedProduct.getQuantity()-quantity;
	    //calculate new inventory quantity
	    if(newInvQuantity < 0) {
	    	return "Quantity would set inventory negative. Select a lower value.";
	    }
	    Query q3 = em.createQuery("UPDATE Products t SET t.quantity = ?1 where t.product_id=?2").setParameter(1, newInvQuantity).setParameter(2, product_id);
	    //query to update inventory quantity
	    int rowcount = q3.executeUpdate();
	    //execute the update
	    logger.info("product: "+product_id+"updated to inventory quantity: "+newInvQuantity+" rowcount from query:"+rowcount);
	    //log the change in inventory
	    List<CustomerProducts> oldCart = myCart.getCartItems();
	    //get a list of old cart items to modify qiantity is item is already in cart
	    boolean contained=false;
	    //check if cart already contains the item
	    for(CustomerProducts thisProduct : oldCart){
		   if(thisProduct.getProduct_id()==product_id){
			   //find a match for product id already in cart matching item to be added
			   int newQuantity = quantity+thisProduct.getQuantity();
			   //calculate the new quantity
			   thisProduct.setQuantity(newQuantity);
			   //sets the updated quantity
			   contained=true;
			   //sets flag to true to represent item already in customer's cart
		   }
	   }   
	   if(!contained){
		   //product is not already in cart
		   logger.info("product: "+product_id+" not already in cart: "+cart_id+" with quantity:"+myProduct.getQuantity());
		   //log info for product added to cart
		   oldCart.add(myProduct);
		   //add new product to shopping cart
	   }
	   myCart.setCartItems(oldCart);
	   //set cart items
	   em.persist(myCart);
	   //persist modified cart in database
	   em.getTransaction().commit();
	   //commit transaction
	   em.close();
	   //close entity manager
	   logger.info("item: "+product_id+" added to cart: "+cart_id+" with quantity: "+quantity);
	   //log item added to cart
	   return "Cart item added successfully:<br>"+myCart.toString();
	   //return data for new cart
	}
	
	@SuppressWarnings("unchecked")
	public List<CustomerProducts> listCartContents(int cartid){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate a factory to get the entity manager from
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		Query q = em.createQuery("select t from Carts t where t.cart_id = ?1").setParameter(1,cartid);
		//query to get cart with specified id
		List<Carts> todoList = q.getResultList();
		//set a list from the query results
		List<CustomerProducts> productsList;
		//create a list of products for user inventory
		if(todoList.size()==1){
			//should have 1 item returned
			productsList = todoList.get(0).getCartItems();
			//get products list from query results
			}
		else{
			//list size is not 1
			return null;
			//error condition
			}
		em.close();
		//close the entity manager
		logger.info("cart contents listed for cartid: "+cartid);
		//log that cart contents were listed
		return productsList;
		//return the linked list of products in the specified cart
	}
	
	@SuppressWarnings("unchecked")
	public String deleteCartItem(int cart_id, int product_id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate the factory to get an entity manager
	    EntityManager em = factory.createEntityManager();
	    //create an entity manager
	    em.getTransaction().begin();
	    //begin the database transaction
	    Query q = em.createQuery("select t from Carts t where t.cart_id = ?1").setParameter(1, cart_id);
	    //qurty to find the selected cart in the database
	    Carts myCart;
	    //create a carts object
	    List<Carts> myList =  q.getResultList();
	    //create a list of carts from the query result
	    if (myList.size()==1){
	    	//query returned 1 item
	    	myCart = myList.get(0);
	    	//set cart to retrieved cart from database
	    	}
	    else{
	    	//query did not return 1 result
	    	return null;
	    	//error condition
	    	}
	    CustomerProducts myProduct=null;
	    //create a customersproduct object 
	    List<CustomerProducts> oldCart = myCart.getCartItems();
	    //get a list of items from the cart selected in the database
	    for(CustomerProducts myProductList : oldCart){
	    	//iterate through cart items to find product of interest
		   if(myProductList.getProduct_id()==product_id){
			   //product id matches that to be deleted
			   myProduct = myProductList;
			   //set product to list
			   }
	    	}
	  oldCart.remove(myProduct);
	  //remove item from the cart
	  int quantity = myProduct.getQuantity();
	  Query q2 = em.createQuery("SELECT t FROM Products t where t.product_id=?1",Products.class).setParameter(1,product_id);
	  List<Products> prodListForQuantity = q2.getResultList();
	  Products prodForQuantity = prodListForQuantity.get(0);
	  int newInvQuantity = prodForQuantity.getQuantity()+quantity;
	  Query q3 = em.createQuery("UPDATE Products t SET t.quantity = ?1 where t.product_id=?2").setParameter(1,newInvQuantity).setParameter(2,product_id);
	  @SuppressWarnings("unused")
	  int rowcount = q3.executeUpdate();
	  myCart.setCartItems(oldCart);
	  //set cart items to modified cart contents
	  em.persist(myCart);
	  //persist the change in the database
	  em.getTransaction().commit();
	  //commit the transaction
	  em.close();
	  //close the entity manger
	  logger.info("item with itemid:"+product_id+" deleted from cart with cartid: "+cart_id);
	  //log that the item was deleted
	  return myCart.toString();
	  //return a string representation of the modified cart
	}
	
	public Object UpdateCart(int cartid, int customerid) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			//instantiate a factory to get an entity manager
			EntityManager em = factory.createEntityManager();
			//create an entity manager
			List<Carts> test = em.createQuery("select t from Carts t where t.cart_id = ?1", Carts.class).setParameter(1, cartid).getResultList();
			//get the cart to be updated from the database
			if(test.size()!=1){
				//error to get non 1 result
				em.close();
				//close entity manager
				logger.error("update cart query failed");
				//log transaction failure
				return null;
				//error condition
			}
			else{
				//list size is 1 as expected
				em.getTransaction().begin();
				//begin transaction
				Carts newCart = test.get(0);
				//get cart from result list
				newCart.setCart_id(cartid);
				//set cart id
				newCart.setCustomer(new Customers());
				//set customer
				em.persist(newCart);
				//persist the change to the database
				em.getTransaction().commit();
				//commit the transaction
				em.close();
				//close the entity manager
				logger.info("cart with cartid: "+cartid+" updated");
				//log cart info change
				return newCart.toString();
				//return string representation of updated cart
			}
		}
	
	public String UpdateCartItem(int cartid, int productid, int quantity){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate factory to get an entity manager
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		List<Carts> test = em.createQuery("select t from Carts t where t.cart_id = ?1 ", Carts.class).setParameter(1, cartid).getResultList();
		//get list of cart from query on cart id
		if(test.size()!=1){
			//error size should be 1
			em.close();
			//close the entity manager
			logger.error("error updating cart item: query response size not 1");
			//log the error
			return null;
			//error condition
		}
		else
		{
			//list size is 1
			em.getTransaction().begin();
			//begin transaction
			Carts newItem = test.get(0);
			//new cart object from list
			List<CustomerProducts> oldList = newItem.getCartItems();
			//get cart items in existing list
			for(CustomerProducts item : oldList){
				//iterate through cart items
				if(item.getProduct_id()==productid){
					//product matching specified id in list
					int oldQuantity=item.getQuantity();
					//get quantity before update
					int difference = oldQuantity-quantity;
					//calculate difference in quantity from old value
					Query q1=em.createQuery("select t from Products t where t.product_id = ?1").setParameter(1, productid);
					//get product from database
					@SuppressWarnings("unchecked")
					List<Products> plist = q1.getResultList();
					//get results of query
					Products itemofinterest = plist.get(0);
					//get product entity
					int invQuant = itemofinterest.getQuantity();
					//get quantity from inventory
					int newInvQuant = invQuant+difference;
					//calculate new quantity for inventory
					if(newInvQuant < 0) {
						return "Quantity would set inventory negative. Select a lower value.";
					}
					Query q2 = em.createQuery("UPDATE Products t SET t.quantity=?1 where t.product_id=?2").setParameter(1, newInvQuant).setParameter(2,productid);
					//update query to set new inventory value
					@SuppressWarnings("unused")
					int rowcount = q2.executeUpdate();
					//run the update query
					item.setQuantity(quantity);
					//update quantity of item
				}
			}
			newItem.setCartItems(oldList);
			//set cart items now that list has been modified
			em.persist(newItem);
			//persist the new entity
			em.getTransaction().commit();
			//commit the transaction
			em.close();
			//close the entity manager
			logger.info("cart item with productid: "+productid+" updated to quantity: "+quantity+" for cartid: "+cartid);
			//log the updated item
			return "Cart Item Updated:<br>"+newItem.toString();
			//return a string representation of the new cart
		}
	}

	public Object DeleteCart(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate the factory to get an entity manager
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		try {
		Carts result =em.createQuery("select t from Carts t where t.cart_id = ?1", Carts.class).setParameter(1, id).getSingleResult();
		//carts entity tohold cart for selected cartid
		em.getTransaction().begin();
		//begin transaction
		em.remove(result);
		//remove the item from the cart
		em.getTransaction().commit();
		//commit the transaction
		em.close();
		//close the entity manager
		logger.info("cart with id: "+id+" deleted");
		//log the deleted cart
		}catch(Exception e) {
			//error condition
			return "There is no cart with id:"+id+"so cannot delete.<br>Please supply a valid id.<br>";
			//return error message for invalid id
		}
		
		return "deleted"+id;
		//return the id of the deleted cart
		}
}
