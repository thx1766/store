package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CustomerProducts")
public class CustomerProducts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int item_id;
	//primary key
    private int product_id;
    //product id from inventory, foreign key
	private String productName;
	//product name
	private double price;
	//product price
	private int quantity;
	//product cart quantity
	
	public CustomerProducts() {
		//noarg constructor required
	}
	
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

	public int getItem_id() {
		//getter for item id
		return item_id;
	}

	public void setItem_id(int item_id) {
		//setter for item id
		this.item_id = item_id;
	}
	
}
