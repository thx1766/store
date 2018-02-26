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

/**
 * 
 * @author nschaffner
 *
 */

public class CartsService {
	/**
	 *persistance unit name required for entity management 
	 */
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	/**
	 *factory for creating entity managers
	 */
	/* link to log to a file
	 * https://examples.javacodegeeks.com/core-java/logback-file-appender-example 
	 */
    EntityManagerFactory factory;
    /**
     *logger set to this class for logging CartsService methods 
     */
    static Logger logger = LoggerFactory.getLogger(CartsService.class);
    
    /**
     * Service method backing REST endpoint to add a cart to the database
     * 
     * @param id userid to associate cart to
     * @return id of created cart
     */
	public int addCart(int id) {
		/**
		 *instantiate a factory to get an entity manager from 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
	    EntityManager em = factory.createEntityManager();
	    /**
	     * begin the transaction
	     */
	    em.getTransaction().begin();
	    /**
	     * create a new carts object
	     */
	    Carts newCart = new Carts();
	    /**
	     *create a new customers object 
	     */
	    Customers newCustomer=new Customers();
	    /**
	     *set the customer id 
	     */
	    newCustomer.setCustomer_id(id);
	    /**
	     *set the customer to the cart 
	     */
	    newCart.setCustomer(newCustomer);
	    /**
	     *persist the cart in the database 
	     */
	    em.persist(newCart);
	    /**
	     * commit the transaction
	     */
	    em.getTransaction().commit();
	    /**
	     * close the entity manager
	     */
	    em.close();
	    /**
	     *log that a new cart was created 
	     */
	    logger.info("cart "+newCart.getCart_id()+" created for customerid: "+id);
	return newCart.getCart_id();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Service method backing REST endpoint to list carts
	 * @return
	 */
	public List<Carts> listCarts() {
		/**
		 *instantiate a new factory to get an entity manger from 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create a new entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *create  query to get the carts from the database 
		 */
		Query q = em.createQuery("select t from Carts t");
		/**
		 *get a list of carts from the query 
		 */
		List<Carts> todoList = q.getResultList();
		/**
		 * close the entity manager
		 */
		em.close();
		/**
		 * log that carts were listed
		 */
		logger.info("carts listed");
		return todoList;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Service backing REST endpoint to add an item to a cart
	 * @param cart_id cart id from rest endpoint method
	 * @param product_id product id from rest endpoint method
	 * @param quantity quantity from rest endpoint method
	 * @return
	 */
	public String addCartItem(int cart_id, int product_id, int quantity) {
		/**
		 *instantiate a factory to get an entity manager from 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
	    EntityManager em = factory.createEntityManager();
	    /**
	     *begin the transaction 
	     */
	    em.getTransaction().begin();
	    /**
	     *query to get cart with specified id from the database 
	     */
	    Query q = em.createQuery("select t from Carts t where t.cart_id = ?1").setParameter(1, cart_id);
	    /**
	     *create a carts object 
	     */
	    Carts myCart;
	    /**
	     *get a list of carts from the query 
	     */
	    List<Carts> myList =  q.getResultList();
	    /**
	     * list size should be 1
	     */
	    if (myList.size()==1){
	    	/**
	    	 *get the first cart from the query 
	    	 */
	    	myCart = myList.get(0);
	    	}
	    /**
	     * no results
	     */
	    else{
	    	/**
	    	 * error condition
	    	 */
	    	return null;
	    	}
	    /**
	     *create a CustomerProducts object to hold customer's purchase 
	     */
	    CustomerProducts myProduct = new CustomerProducts();
	    /**
	     *create a query to get the product information from the database 
	     */
	    Query q2 = em.createQuery("select t from Products t where t.product_id=?1",Products.class).setParameter(1, product_id);
	    /**
	     *get the result from the query 
	     */
	    Products selectedProduct = (Products) q2.getResultList().get(0);
	    /**
	     *set name of product for customer inventory 
	     */
	    myProduct.setProductName(selectedProduct.getProductName());
	    /**
	     *set price of product for customer inventory 
	     */
	    myProduct.setPrice(selectedProduct.getPrice());
	    /**
	     *set product id for customer inventory 
	     */
	    myProduct.setProduct_id(product_id);
	    /**
	     *set quantity for customer inventory 
	     */
	    myProduct.setQuantity(quantity);
	    /**
	     *calculate new inventory quantity 
	     */
	    int newInvQuantity = selectedProduct.getQuantity()-quantity;
	    /**
	     * request greater than number of products in inventory
	     */
	    if(newInvQuantity < 0) {
	    	return "Quantity would set inventory negative. Select a lower value.";
	    }
	    /**
	     *query to update inventory quantity 
	     */
	    Query q3 = em.createQuery("UPDATE Products t SET t.quantity = ?1 where t.product_id=?2").setParameter(1, newInvQuantity).setParameter(2, product_id);
	    /**
	     *execute the update 
	     */
	    int rowcount = q3.executeUpdate();
	    /**
	     *log the change in inventory 
	     */
	    logger.info("product: "+product_id+"updated to inventory quantity: "+newInvQuantity+" rowcount from query:"+rowcount);
	    /**
	     *get a list of old cart items to modify quantity is item is already in cart 
	     */
	    List<CustomerProducts> oldCart = myCart.getCartItems();
	    /**
	     *check if cart already contains the item 
	     */
	    boolean contained=false;
	    /**
	     * iterate over cart items
	     */
	    for(CustomerProducts thisProduct : oldCart){
	    	/**
	    	 * find a match for product id already in cart matching item to be added
	    	 */
		   if(thisProduct.getProduct_id()==product_id){
			   /**
			    *calculate the new quantity 
			    */
			   int newQuantity = quantity+thisProduct.getQuantity();
			   /**
			    *sets the updated quantity 
			    */
			   thisProduct.setQuantity(newQuantity);
			   /**
			    *sets flag to true to represent item already in customer's cart 
			    */
			   contained=true;
		   }
	   }   
	    /**
	     * product is not already in cart
	     */
	   if(!contained){
		   /**
		    *log info for product added to cart 
		    */
		   logger.info("product: "+product_id+" not already in cart: "+cart_id+" with quantity:"+myProduct.getQuantity());
		   /**
		    *add new product to shopping cart 
		    */
		   oldCart.add(myProduct);
	   }
	   /**
	    *set cart items 
	    */
	   myCart.setCartItems(oldCart);
	   /**
	    *persist modified cart in database 
	    */
	   em.persist(myCart);
	   /**
	    * commit transaction
	    */
	   em.getTransaction().commit();
	   /**
	    * close entity manager
	    */
	   em.close();
	   /**
	    * log item added to cart
	    */
	   logger.info("item: "+product_id+" added to cart: "+cart_id+" with quantity: "+quantity);
	   /**
	    * return data for new cart
	    */
	   return "Cart item added successfully: "+myCart.toString();
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Service backing rest endpoint to list contents of a cart
	 * @param cartid id of cart from rest endpoint method
	 * @return return the linked list of products in the specified cart
	 */
	public List<CustomerProducts> listCartContents(int cartid){
		/**
		 *instantiate a factory to get the entity manager from 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *query to get cart with specified id 
		 */
		Query q = em.createQuery("select t from Carts t where t.cart_id = ?1").setParameter(1,cartid);
		/**
		 *set a list from the query results 
		 */
		List<Carts> todoList = q.getResultList();
		/**
		 *create a list of products for user inventory 
		 */
		List<CustomerProducts> productsList;
		/**
		 * should have 1 item returned
		 */
		if(todoList.size()==1){
			/**
			 *get products list from query results 
			 */
			productsList = todoList.get(0).getCartItems();
			}
		/**
		 * list size is not 1
		 */
		else{
			/**
			 * error condition
			 */
			return null;
			}
		/**
		 *close the entity manager 
		 */
		em.close();
		/**
		 *log that cart contents were listed 
		 */
		logger.info("cart contents listed for cartid: "+cartid);
		return productsList;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Service backing rest endpoint to delete an item from a cart
	 * @param cart_id id of the cart from rest endpoint
	 * @param product_id id of product from the rest endpoint
	 * @return returns a string representation of modified cart
	 */
	public String deleteCartItem(int cart_id, int product_id) {
		/**
		 *instantiate the factory to get an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
	    EntityManager em = factory.createEntityManager();
	    /**
	     *begin the database transaction 
	     */
	    em.getTransaction().begin();
	    /**
	     *query to find the selected cart in the database 
	     */
	    Query q = em.createQuery("select t from Carts t where t.cart_id = ?1").setParameter(1, cart_id);
	    /**
	     *create a carts object 
	     */
	    Carts myCart;
	    /**
	     *create a list of carts from the query result 
	     */
	    List<Carts> myList =  q.getResultList();
	    /**
	     * query returned 1 item
	     */
	    if (myList.size()==1){
	    	/**
	    	 *set cart to retrieved cart from database 
	    	 */
	    	myCart = myList.get(0);
	    	}
	    /**
	     * query did not return 1 result
	     */
	    else{
	    	/**
	    	 * error condition
	    	 */
	    	return null;
	    	}
	    /**
	     *create a customersproduct object 
	     */
	    CustomerProducts myProduct=null;
	    /**
	     *get a list of items from the cart selected in the database 
	     */
	    List<CustomerProducts> oldCart = myCart.getCartItems();
	    /**
	     * iterate through cart items to find product of interest
	     */
	    for(CustomerProducts myProductList : oldCart){
	    	/**
	    	 * product id matches that to be deleted
	    	 */
		   if(myProductList.getProduct_id()==product_id){
			   /**
			    *set product to list 
			    */
			   myProduct = myProductList;
			   }
	    	}
	    /**
	     *remove item from the cart 
	     */
	  oldCart.remove(myProduct);
	  /**
	   * get quantity
	   */
	  int quantity = myProduct.getQuantity();
	  /**
	   * query to get products entity matching target product id
	   */
	  Query q2 = em.createQuery("SELECT t FROM Products t where t.product_id=?1",Products.class).setParameter(1,product_id);
	  /**
	   * list of products to modofy quantity
	   */
	  List<Products> prodListForQuantity = q2.getResultList();
	  /**
	   * product to change quantity of
	   */
	  Products prodForQuantity = prodListForQuantity.get(0);
	  /**
	   * calculate new inventory quantity
	   */
	  int newInvQuantity = prodForQuantity.getQuantity()+quantity;
	  /**
	   * query to update product quantity in inventory
	   */
	  Query q3 = em.createQuery("UPDATE Products t SET t.quantity = ?1 where t.product_id=?2").setParameter(1,newInvQuantity).setParameter(2,product_id);
	  @SuppressWarnings("unused")
	  /**
	   * run the update query, rowcount is not used
	   */
	  int rowcount = q3.executeUpdate();
	  /**
	   *set cart items to modified cart contents 
	   */
	  myCart.setCartItems(oldCart);
	  /**
	   *persist the change in the database 
	   */
	  em.persist(myCart);
	  /**
	   *commit the transaction 
	   */
	  em.getTransaction().commit();
	  /**
	   *close the entity manger 
	   */
	  em.close();
	  /**
	   *log that the item was deleted 
	   */
	  logger.info("item with itemid:"+product_id+" deleted from cart with cartid: "+cart_id);
	  /**
	   *return a string representation of the modified cart 
	   */
	  return myCart.toString();
	}
	
	/**
	 * Service backing rest method to update cart
	 * @param cartid cart id of interest
	 * @param customerid new customer id to associate cart to
	 * @return return string representation of updated cart 
	 */
	public String UpdateCart(int cartid, int customerid) {
		/**
		 * instantiate a factory to get an entity manager
		 */
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			/**
			 *create an entity manager 
			 */
			EntityManager em = factory.createEntityManager();
			/**
			 *get the cart to be updated from the database 
			 */
			List<Carts> test = em.createQuery("select t from Carts t where t.cart_id = ?1", Carts.class).setParameter(1, cartid).getResultList();
			/**
			 * error to get non 1 result
			 */
			if(test.size()!=1){
				/**
				 *close entity manager 
				 */
				em.close();
				/**
				 *log transaction failure 
				 */
				logger.error("update cart query failed");
				/**
				 * error condition
				 */
				return null;
			}
			/**
			 * list size is 1 as expected
			 */
			else{
				/**
				 *begin transaction 
				 */
				em.getTransaction().begin();
				/**
				 *get cart from result list 
				 */
				Carts newCart = test.get(0);
				/**
				 * set cart it
				 */
				newCart.setCart_id(cartid);
				/**
				 * set customer
				 */
				newCart.setCustomer(new Customers());
				/**
				 *persist the change to the database 
				 */
				em.persist(newCart);
				/**
				 *commit the transaction 
				 */
				em.getTransaction().commit();
				/**
				 *close the entity manager 
				 */
				em.close();
				/**
				 *log cart info change 
				 */
				logger.info("cart with cartid: "+cartid+" updated");

				return newCart.toString();
			}
		}
	/**
	 * Service method backing rest endpoint to update a cart item
	 * @param cartid cart id from rest endpoint
	 * @param productid product id from rest endpoint
	 * @param quantity from rest endpoint
	 * @return return a string representation of the new cart
	 */
	public String UpdateCartItem(int cartid, int productid, int quantity){
		/**
		 *instantiate factory to get an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *get list of cart from query on cart id 
		 */
		List<Carts> test = em.createQuery("select t from Carts t where t.cart_id = ?1 ", Carts.class).setParameter(1, cartid).getResultList();
		/**
		 * error size should be 1
		 */
		if(test.size()!=1){
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 *log the error 
			 */
			logger.error("error updating cart item: query response size not 1");
			/**
			 * error condition
			 */
			return null;
		}
		/**
		 * list size is 1
		 */
		else
		{
			/**
			 *begin transaction 
			 */
			em.getTransaction().begin();
			/**
			 *new cart object from list 
			 */
			Carts newItem = test.get(0);
			/**
			 *get cart items in existing list 
			 */
			List<CustomerProducts> oldList = newItem.getCartItems();
			/**
			 * iterate through cart items
			 */
			for(CustomerProducts item : oldList){
				/**
				 * product matching specified id in list
				 */
				if(item.getProduct_id()==productid){
					/**
					 *get quantity before update 
					 */
					int oldQuantity=item.getQuantity();
					/**
					 *calculate difference in quantity from old value 
					 */
					int difference = oldQuantity-quantity;
					/**
					 *get product from database 
					 */
					Query q1=em.createQuery("select t from Products t where t.product_id = ?1").setParameter(1, productid);
					@SuppressWarnings("unchecked")
					/**
					 *get results of query 
					 */
					List<Products> plist = q1.getResultList();
					/**
					 *get product entity 
					 */
					Products itemofinterest = plist.get(0);
					/**
					 *get quantity from inventory 
					 */
					int invQuant = itemofinterest.getQuantity();
					/**
					 *calculate new quantity for inventory 
					 */
					int newInvQuant = invQuant+difference;
					/**
					 * Check if new quantity would make inventory negative
					 */
					if(newInvQuant < 0) {
						/**
						 * error condition
						 */
						return "Quantity would set inventory negative. Select a lower value.";
					}
					/**
					 *update query to set new inventory value 
					 */
					Query q2 = em.createQuery("UPDATE Products t SET t.quantity=?1 where t.product_id=?2").setParameter(1, newInvQuant).setParameter(2,productid);
					@SuppressWarnings("unused")
					/**
					 *run the update query 
					 */
					int rowcount = q2.executeUpdate();
					/**
					 *update quantity of item 
					 */
					item.setQuantity(quantity);
				}
			}
			/**
			 *set cart items now that list has been modified 
			 */
			newItem.setCartItems(oldList);
			/**
			 *persist the new entity 
			 */
			em.persist(newItem);
			/**
			 *commit the transaction 
			 */
			em.getTransaction().commit();
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 *log the updated item 
			 */
			logger.info("cart item with productid: "+productid+" updated to quantity: "+quantity+" for cartid: "+cartid);
			return "Cart Item Updated: "+newItem.toString();
		}
	}

	/**
	 * Service method backing rest endpoint to delete cart
	 * @param id cart id sent from rest endpoint
	 * @return return the id of the deleted cart
	 */
	public String DeleteCart(int id) {
		/**
		 *instantiate the factory to get an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		try {
			/**
			 *carts entity tohold cart for selected cartid 
			 */
			Carts result =em.createQuery("select t from Carts t where t.cart_id = ?1", Carts.class).setParameter(1, id).getSingleResult();
			/**
			 *begin transaction 
			 */
			em.getTransaction().begin();
			/**
			 *remove the item from the cart 
			 */
			em.remove(result);
			/**
			 *commit the transaction 
			 */
			em.getTransaction().commit();
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 *log the deleted cart 
			 */
			logger.info("cart with id: "+id+" deleted");
		}
		/**
		 * error condition
		 */
		catch(Exception e) {
			/**
			 *return error message for invalid id 
			 */
			return "There is no cart with id:"+id+"so cannot delete.<br>Please supply a valid id.<br>";
		}
		return "deleted"+id;
		}

	public int getCartFromUserid(int userid) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select t from Customers t where t.customer_id = ?1",Customers.class).setParameter(1, userid);
		List<Customers> customerlist =  q.getResultList();
		Customers mycustomer = customerlist.get(0);
		Query q2 = em.createQuery("select t from Carts t where t.customer=?1",Carts.class).setParameter(1, mycustomer);
		List<Carts> cartlist = q2.getResultList();
		Carts mycart = cartlist.get(0);
		int mycartid = mycart.getCart_id();
		return mycartid;
	}

	public int getUseridFromCartid(int cartId) {
		
		/**
		 *access database to lookup userid from cartid 
		 */
		int userid=-1;
		/**
	     *instantiate a factory to get an entity manager from 
	     */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 * create an entity manager
		 */
	    EntityManager em = factory.createEntityManager();
	    em.getTransaction().begin();
	    Query q = em.createQuery("select t from Carts t where t.cart_id=?1",Carts.class).setParameter(1,cartId);
	    @SuppressWarnings("unchecked")
		List<Carts> list = q.getResultList();
	    try {
	    	userid = list.get(0).getCustomer().getCustomer_id();
	    }catch(Exception e) {
	    	userid=-1;
	    }
		return userid;
	}
}
