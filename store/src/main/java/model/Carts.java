package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Carts")
public class Carts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_id;
	//primary key
	private Customers customer;
	//customer entity
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<CustomerProducts> cartItems;
	//list of CustomerProducts entities
	public int getCart_id() {
		//getter for cart id
		return cart_id;
	}
	public void setCart_id(int cart_id) {
		//setter for cart id
		this.cart_id = cart_id;
	}
	public Customers getCustomer() {
		//getter for customer
		return customer;
	}
	public void setCustomer(Customers customer) {
		//setter for customer
		this.customer = customer;
	}
	public List<CustomerProducts> getCartItems() {
		//getter for cartItems
		return cartItems;
	}
	public void setCartItems(List<CustomerProducts> cartItems) {
		//setter for cart items
		this.cartItems = cartItems;
	}

	

	
	
}
