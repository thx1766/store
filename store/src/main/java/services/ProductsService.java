package services;

import java.util.ArrayList;
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

public class ProductsService {
	/**
	 *persistence unit name for connecting to the database 
	 */
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	/**
	 *factory for creating entity managers 
	 */
    EntityManagerFactory factory;
    /**
     *logger set to log messages for methods in ProductsService class 
     */
    static Logger logger = LoggerFactory.getLogger(ProductsService.class);
  
    /**
     * service method backing rest endpoint for adding a product
     * @param productName
     * @param price
     * @param quantity
     * @return return the full string containing product info and link
     */
	public String addProduct(String productName, double price, int quantity) {
		/**
		 *instantiate the factory for getting an entity manager 
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
	     *create a new Products entity 
	     */
	    Products newProduct = new Products();
	    /**
	     *set the product name 
	     */
	   newProduct.setProductName(productName);
	   /**
	    *set the product price 
	    */
	   newProduct.setPrice(price);
	   /**
	    *set the product quantity 
	    */
	   newProduct.setQuantity(quantity);
	   /**
	    * persist the new product
	    */
	    em.persist(newProduct);
	    /**
	     *commit the transaction 
	     */
	    em.getTransaction().commit();
	    /**
	     *close the entity manager 
	     */
	    em.close();
	    /**
	     *log the product that was added 
	     */
	    logger.info("product: "+productName+" added with price: "+price+" and quantity: "+quantity);
	    
	return newProduct.toString();
	
	}

	@SuppressWarnings("unchecked")
	/**
	 * service method backing rest endpoint to list products
	 * @return return the list of products
	 */
	public List<Products> listProducts() {
		/**
		 *instantiate the factory for creating entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *query to search the database for all products 
		 */
		Query q = em.createQuery("select t from Products t");
		/**
		 *set a list of products from the query 
		 */
		List<Products> todoList = q.getResultList();
		/**
		 *close the entity manager 
		 */
		em.close();
		/**
		 *log that products were listed 
		 */
		logger.info("products listed");
		return todoList;
	}

	/**
	 * service method backing rest endpoint to update a product
	 * @param product_id
	 * @param productName
	 * @param price
	 * @param quantity
	 * @return return a string representation of the updated product
	 */
	public String updateProduct(int product_id, String productName, double price, int quantity) {
		/**
		 *instantiate the factory to get an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *get a list of products corresponding to the product id specified 
		 */
		List<Products> test = em.createQuery("select t from Products t where t.product_id = ?1", Products.class).setParameter(1, product_id).getResultList();
		/**
		 * error - should return 1 result
		 */
		if(test.size()!=1){
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 * log the error
			 */
			logger.error("error updating product with id: "+product_id);
			/**
			 * error condition
			 */
			return null;			
		}
		/**
		 * list is comprised on one element
		 */
		else
		{
			/**
			 *begin transaction 
			 */
			em.getTransaction().begin();
			/**
			 *get product from list 
			 */
			Products newProducts = test.get(0);
			/**
			 *set updated price 
			 */
			newProducts.setPrice(price);
			/**
			 *set updated name 
			 */
			newProducts.setProductName(productName);
			/**
			 *set updated quantity 
			 */
			newProducts.setQuantity(quantity);
			/**
			 *persist the modified product 
			 */
			em.persist(newProducts);
			/**
			 *commit the transaction 
			 */
			em.getTransaction().commit();
			/**
			 *close the entity manager 
			 */
			em.close();
			/**
			 *log that the product was updated 
			 */
			logger.info("product with id: "+product_id+" updated");
			return newProducts.toString();
		}
	}

	/**
	 * service method backing rest endpoint to delete a product from inventory
	 * @param id
	 * @return return the id of the deleted product
	 */

	public String deleteProduct(int id) {
		/**
		 *instantiate a factory to get an entity manager 
		 */
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		/**
		 *create an entity manager 
		 */
		EntityManager em = factory.createEntityManager();
		/**
		 *check all carts for product and remove item from carts to proceed 
		 */
		Query q = em.createNativeQuery("select eclipselinkschema.carts.CART_ID from eclipselinkschema.carts, eclipselinkschema.carts_customerproducts, eclipselinkschema.customerproducts where eclipselinkschema.carts.cart_id = eclipselinkschema.carts_customerproducts.Carts_CART_ID and eclipselinkschema.carts_customerproducts.cartItems_ITEM_ID=eclipselinkschema.customerproducts.ITEM_ID and eclipselinkschema.customerproducts.PRODUCT_ID = ?1");
		q.setParameter(1,id);
		List<Integer> cartList =q.getResultList();
		for(Integer item: cartList) {
			List<Carts> allcarts = em.createQuery("select t from Carts t where t.cart_id=?1",Carts.class).setParameter(1, item).getResultList();
			/**
			 * for each cart
			 */
			for(Carts cartindex: allcarts) {
				/**
				 * get products list
				 */
				List<CustomerProducts> productlist=cartindex.getCartItems();
				List<CustomerProducts> temp=new ArrayList<CustomerProducts>();
					/**
					 * For each cart item
					 */
					for(CustomerProducts productindex: productlist) {
						/**
						 * customer cart item matches id of product to be deleted
						 */
						if(productindex.getProduct_id()==id) {
							//add to remove list
							temp.add(productindex);
						}
					}
					/**
					 * remove items found in search
					 */
					productlist.removeAll(temp);
					/**
					 *set list to new list missing targeted product 
					 */
					cartindex.setCartItems(productlist);
			}
		}
		/**
		 *get the product with the specified id 
		 */
		Products result =em.createQuery("select t from Products t where t.product_id = ?1", Products.class).setParameter(1, id).getSingleResult();
		/**
		 *begin transaction 
		 */
		em.getTransaction().begin();
		/**
		 *remove the product matching the query 
		 */
		em.remove(result);
		/**
		 * commit the transaction
		 */
		em.getTransaction().commit();
		/**
		 * close the entity manager
		 */
		em.close();
		/**
		 *log that the product was deleted 
		 */
		logger.info("product with product id: "+id+" deleted");
		return "deleted"+id;
	}

	public int getCartFromUserid(int userid) {
		String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select t from Customers t where t.customer_id = ?1",Customers.class).setParameter(1, userid);
		List<Customers> customerlist =  q.getResultList();
		Customers mycustomer;
		try{
			mycustomer = customerlist.get(0);
		}catch(IndexOutOfBoundsException e) {
			return -1;
		}
		Query q2 = em.createQuery("select t from Carts t where t.customer=?1",Carts.class).setParameter(1, mycustomer);
		List<Carts> cartlist = q2.getResultList();
		int mycartid =0;
		try{
			Carts mycart = cartlist.get(0);
			mycartid = mycart.getCart_id();
		}catch(Exception e){
			mycartid=-1;
		}
		return mycartid;
	}
	
	public int test(int userid) {
		/**
		 *persistence unit name for accessing the database 
		 */
		String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
		/**
		 *factory for creating an entity manager 
		 */
	    EntityManagerFactory factory;
	    /**
	     *instantiate factory for creating an entity manager 
	     */
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    /**
	     *create the entity manager 
	     */
	    EntityManager em = factory.createEntityManager();
	    /**
	     *query to get customer with specified id 
	     */
	    Query q = em.createQuery("select t from Customers t where t.customer_id = ?1").setParameter(1, userid);
	    /**
	     *list of one customer corresponding to specified id 
	     */
	    List<Customers> customerlist = q.getResultList();
	    /**
	     *customer extracted from list 
	     */
	    Customers mycustomer = customerlist.get(0);
	    /**
	     *query to get cart corresponding to customer 
	     */
	    Query q2 = em.createQuery("select t from Carts t where t.customer=?1").setParameter(1, mycustomer);
	    /**
	     *list of carts from query 
	     */
	    List<Carts> cartlist = q2.getResultList();
	    /**
	     *cart entity 
	     */

	    Carts mycart;
	    try {
	    	/**
	    	 * try to get the first element
	    	 */
	    	mycart = cartlist.get(0);
	    }catch(Exception e) {
	    	/**
	    	 * the cart does not exist, provide a link to create one
	    	 */
	    	return -1;//"<html><body>You must create a cart first!<br><a href='/store/addCart.jsp'>Create a cart</a></body></html>";
	    }
	    /**
	     *cart id from query 
	     */
	   // int mycartid = mycart.getCart_id();
		return userid;
	}
}
