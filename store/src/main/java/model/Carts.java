package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 
 * @author nschaffner
 *
 */
@Entity
@Table(name="Carts")
public class Carts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 *primary key 
	 */
    private int cart_id;
	/**
	 * customers entity
	 */
	private Customers customer;
	@OneToMany(cascade=CascadeType.PERSIST)
	/**
	 *list of CustomerProducts entities 
	 */
	private List<CustomerProducts> cartItems;

	/**
	 * getter for cart id
	 * @return cart id
	 */
	public int getCart_id() {
		return cart_id;
	}
	/**
	 * setter for cart id
	 * @param cart_id
	 */
	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}
	/**
	 * getter for customer
	 * @return customer
	 */
	public Customers getCustomer() {
		return customer;
	}
	/**
	 * setter for customer
	 * @param customer
	 */
	public void setCustomer(Customers customer) {
		this.customer = customer;
	}
	/**
	 * getter for cartItems
	 * @return cartItems
	 */
	public List<CustomerProducts> getCartItems() {
		return cartItems;
	}
	/**
	 * setter for cart items
	 * @param cartItems
	 */
	public void setCartItems(List<CustomerProducts> cartItems) {
		this.cartItems = cartItems;
	}

	

	
	
}
