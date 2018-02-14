package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Products")
public class Products {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
	//primary key
	private String productName;
	//product name
	private double price;
	//product price
	private int quantity;
	//inventory quantity
	
	public int getProduct_id() {
		//getter for product id
		return product_id;
	}
	public void setProduct_id(int product_id) {
		//setter for product id
		this.product_id = product_id;
	}
	public String getProductName() {
		//getter for product name
		return productName;
	}
	public void setProductName(String productName) {
		//setter for product name
		this.productName = productName;
	}
	public double getPrice() {
		//getter for price
		return price;
	}
	public void setPrice(double price) {
		//setter for price
		this.price = price;
	}
	public int getQuantity() {
		//getter for quantity
		return quantity;
	}
	public void setQuantity(int quantity) {
		//setter for quantity
		this.quantity = quantity;
	}
	
}
