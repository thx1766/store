<html>

<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

	


function sendData() {
	console.log("in functiuon");
	var article = new Object();
	article.cartid = $('#cartID').val();

    $.ajax({
        url: '/store/webapi/cartItems/list',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(article),
        dataType: 'json'
    });
    
    console.log("after json");
}
</script>
</head>


<body>


<form enctype="application/json" action="/store/webapi/cartItems/list" method="post">
<input type="text" name = "cartID" id="cartID"/>

<button type="submit"  >submit</button>
</form>

<br>
<form action="/store/">
    <input type="submit" value="Main Menu" />
</form>
</body>

</html>