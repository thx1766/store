import resources.ProductsResource;
import services.ProductsService;

public class maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ProductsResource resource = new ProductsResource();
		resource.deleteProductSQLNonRest(1);
	}

}
