package dao;

import java.util.List;

import model.Product;

public interface ProductsDAO {
	public List<Product> getAllProducts();
	public Product getProductById(Product product);
	public Product getProductByName(Product product);
	public boolean createProduct(Product product);
	public boolean updateProduct(Product product);
	public boolean deleteProduct(Product product);
	
}