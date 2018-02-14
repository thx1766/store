<html>
<head>
<style>


td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}


</style>
</head>
<body>
<h2>Welcome Administrator!</h2>
<!-- add items -->
<!-- update items -->
<!-- remove items -->
   <table>
    	<tr><td>Products</td></tr>
    		<tr><td><a href="addProduct.jsp">Add Product</a>
    		<br><a href="deleteProduct.jsp">Delete Product</a>
    		<br><a href="listProductsAdmin.jsp">List Products</a>
    		<br><a href="updateProduct.jsp">Update Product</a></td></tr>
		<tr><td>Customers</td></tr>
    		<tr><td><a href="addCustomer.jsp">Add Customer</a>
    		<br><a href="deleteCustomer.jsp">Delete Customer</a>
    		<br><a href="listCustomers.jsp">List Customers</a>
    		<br><a href="updateCustomer.jsp">Update Customer</a></td></tr>
   		<tr><td>Carts</td></tr>
    		<tr><td><a href="addCart.jsp">Add Cart</a>
    		<br><a href="deleteCart.jsp">Delete Cart</a>
    		<br><a href="listCarts.jsp">List Carts</a>
    		<br><a href="updateCart.jsp">Update Cart</a></td></tr>
    	<tr><td>Cart Items</td></tr>
    		<tr><td><a href="addCartItem.jsp">Add Cart Item</a>
    		<br><a href="deleteCartItem.jsp">Delete Cart Item</a>
    		<br><a href="listCartItemsAdmin.jsp">List Cart Items</a>
    		<br><a href="updateCartItem.jsp">Update Cart Item</a></td></tr>
    </table>
</body>
</html>