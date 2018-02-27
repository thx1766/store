package resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import model.Carts;
import model.Customers;
import model.Products;
import services.ProductsService;
/**
 * 
 * @author nschaffner
 *
 */


@Path("/products")
public class ProductsResource {
	
	ProductsService service = new ProductsService();
	
	
	/**
	 * add a product
	 * @param productName
	 * @param price
	 * @param quantity
	 * @return
	 */
	public String addProductNonRest(String productName, double price, int quantity) {
		return service.addProduct(productName,price,quantity);
	}
	
	
	/**
	 * list products
	 * @return
	 */
	public List<Products> listProductsNonRest(){
		return service.listProducts();
	}
	
	/**
	 * gets cartid from supplied userid
	 * @param userid
	 * @return
	 */
	public int getCartFromUserid(int userid) {
		return service.getCartFromUserid( userid);
	}

	/**
	 * update a product
	 * @param product_id
	 * @param productName
	 * @param price
	 * @param quantity
	 * @return
	 */
	public String updateProductNonRest(int product_id, String productName, double price, int quantity) {
		return service.updateProduct(product_id,productName,price,quantity);
	}
	
	
	/**
	 * delete a product
	 * @param id
	 * @return
	 */
	public String deleteProductNonRest(int id) {
		return service.deleteProduct(id);
	}
	
	
}