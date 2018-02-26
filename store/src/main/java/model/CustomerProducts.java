package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author nschaffner
 *
 */
@Entity
@Table(name="CustomerProducts")
public class CustomerProducts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * primary key
	 */
	private int item_id;
	/**
	 *product id from inventory, foreign key 
	 */
    private int product_id;
    /**
     * product name
     */
	private String productName;
	/**
	 * product price
	 */
	private double price;
	/**
	 * product cart quantity
	 */
	private int quantity;
	
	/**
	 * noarg constructor required
	 */
	public CustomerProducts() {
	}
	/**
	 * getter for product id
	 * @return product id
	 */
	public int getProduct_id() {
		return product_id;
	}
	/**
	 * setter for product id
	 * @param product_id
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	/**
	 * getter for product name
	 * @return product name
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * setter for product name
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * getter for price
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * setter for price
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * getter for quantity
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * setter for cart quantity
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * getter for item id
	 * @return item id
	 */
	public int getItem_id() {
		return item_id;
	}
	/**
	 * setter for item id
	 * @param item_id
	 */
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	
}
