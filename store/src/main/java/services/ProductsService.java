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
import model.Products;

public class ProductsService {
	String PERSISTENCE_UNIT_NAME = "eclipselinkschema";
	//persistence unit name for connecting to the database
    EntityManagerFactory factory;
    //factory for creating entity managers
    static Logger logger = LoggerFactory.getLogger(ProductsService.class);
    //logger set to log messages for methods in ProductsService class
    
	public String addProduct(String productName, double price, int quantity) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate the factory for getting an entity manager
	    EntityManager em = factory.createEntityManager();
	    //create an entity manager
	    em.getTransaction().begin();
	    //begin the transaction
	    Products newProduct = new Products();
	    //create a new Products entity
	   newProduct.setProductName(productName);
	   //set the product name
	   newProduct.setPrice(price);
	   //set the product price
	   newProduct.setQuantity(quantity);
	   //set the product quantity
	    em.persist(newProduct);
	    //persist the new product
	    em.getTransaction().commit();
	    //commit the transaction
	    em.close();
	    //close the entity manager
	    logger.info("product: "+productName+" added with price: "+price+" and quantity: "+quantity);
	    //log the prodcut that was added
	    String retval="<html><body>";
	    //set html envelope
	    retval+=newProduct.toString();
	    //set string representation of new product
	    retval+="<BR><a href='/store/addProduct.jsp'>Return to Add Products</a></body></html>";
	    //close the html envelope and set a link to add more products
	return retval;
	//return the full string containing product info and link
	
	}

	@SuppressWarnings("unchecked")
	public List<Products> listProducts() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate the factory for creating entity manager
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		Query q = em.createQuery("select t from Products t");
		//query to search the database for all products
		List<Products> todoList = q.getResultList();
		//set a list of products from the query
		em.close();
		//close the entity manager
		logger.info("products listed");
		//log that products were listed
		return todoList;
		//return the list of products
	}

	public Object UpdateProduct(int product_id, String productName, double price, int quantity) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate the factory to get an entity manager
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		List<Products> test = em.createQuery("select t from Products t where t.product_id = ?1", Products.class).setParameter(1, product_id).getResultList();
		//get a list of products corresponding to the product id specified
		if(test.size()!=1){
			//error - should return 1 result
			em.close();
			//close the entity manager
			logger.error("error updating product with id: "+product_id);
			return null;
			//error condition
		}
		else
		{
			//list is comprised on one element
			em.getTransaction().begin();
			//begin transaction
			Products newProducts = test.get(0);
			//get product from list
			newProducts.setPrice(price);
			//set updated price
			newProducts.setProductName(productName);
			//set updated name
			newProducts.setQuantity(quantity);
			//set updated quantity
			em.persist(newProducts);
			//persist the modified product
			em.getTransaction().commit();
			//commit the transaction
			em.close();
			//close the entity manager
			logger.info("product with id: "+product_id+" updated");
			//log that the product was updated
			return newProducts.toString();
			//return a string representation of the updated product
		}
	}

	public Object DeleteProduct(int id) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		//instantiate a factory to get an entity manager
		EntityManager em = factory.createEntityManager();
		//create an entity manager
		
		//check all carts for product and remove item from carts to proceed
		List<Carts> allcarts = em.createQuery("select t from Carts t",Carts.class).getResultList();
		for(Carts cartindex: allcarts) {
			//for each cart
			List<CustomerProducts> productlist=cartindex.getCartItems();
			//get products list
			List<CustomerProducts> temp=new ArrayList<CustomerProducts>();
				for(CustomerProducts productindex: productlist) {
					//for each cart item
					
					if(productindex.getProduct_id()==id) {
						//customer cart item matches id of product to be deleted
						temp.add(productindex);
						//remove from list
					}
				}
				productlist.removeAll(temp);
				//remove items found in search
				cartindex.setCartItems(productlist);
				//set list to new list missing targeted product
			
		}
		//for each cart
			//check if product id matches id to be deleted
			//if id matches remove product from cart
		
		
		
		Products result =em.createQuery("select t from Products t where t.product_id = ?1", Products.class).setParameter(1, id).getSingleResult();
		//get the product with the specified id
		em.getTransaction().begin();
		//begin transaction
		em.remove(result);
		//remove the product matching the query
		em.getTransaction().commit();
		//commit the transaction
		em.close();
		//close the entity manager
		logger.info("product with product id: "+id+" deleted");
		//log that the product was deleted
		return "deleted"+id;
		//return the id of the deleted product
	}

}
