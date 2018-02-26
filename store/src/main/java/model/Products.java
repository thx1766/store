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
@Table(name="Products")
public class Products {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * primary key
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
	 * inventory quantity
	 */
	private int quantity;
	
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
	 * @return
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
	 * @return
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
	 * getter for inventory quantity
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * setter for quantity
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
