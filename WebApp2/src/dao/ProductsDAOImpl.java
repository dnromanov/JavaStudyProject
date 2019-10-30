package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.Role;
import model.User;
import util.DBUtils;

public class ProductsDAOImpl implements ProductsDAO{

	@Override
	public List<Product> getAllProducts() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List<Product> products = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			String sql = "SELECT ID, NAME, IMAGE, TYPE, DESCRIPTION FROM " + DBUtils.DB_NAME + ".products";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("ID"));
				product.setName(rs.getString("NAME"));
				product.setImage(rs.getString("IMAGE"));
				product.setType(rs.getString("TYPE"));
				product.setDescription(rs.getString("DESCRIPTION"));;
				products.add(product);
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product getProductById(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + ".products WHERE ID = ?");
			pstmt.setInt(1, product.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				product.setImage(rs.getString("IMAGE"));
				product.setName(rs.getString("NAME"));
				product.setType(rs.getString("TYPE"));
				product.setDescription(rs.getString("DESCRIPTION"));
				return product;
			} else {
				System.out.println("CAN'T FIND PRODUCT WITH ID = " + product.getId());
				product = null;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public Product getProductByName(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM " + DBUtils.DB_NAME + ".products WHERE NAME = ?");
			pstmt.setString(1, product.getName());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				product.setId(rs.getInt("ID"));
				product.setImage(rs.getString("IMAGE"));
				product.setType(rs.getString("TYPE"));
				product.setDescription(rs.getString("DESCRIPTION"));
				return product;
			} else {
				System.out.println("CAN'T FIND PRODUCT WITH NAME = " + product.getName());
				product = null;
			}
			DBUtils.release(conn, null, pstmt);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public boolean createProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(
					"INSERT INTO " + DBUtils.DB_NAME + ".products(NAME, IMAGE, TYPE, DESCRIPTION) VALUES (?, ?, ?, ?)");
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getImage());
			pstmt.setString(3, product.getType());
			pstmt.setString(4, product.getDescription());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				System.out.println("PRODUCT INSERTED");
				return true;
			}

			DBUtils.release(conn, null, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			String sql = "UPDATE " + DBUtils.DB_NAME + ".products SET NAME = ? , IMAGE = ?, TYPE = ?, DESCRIPTION = ? WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getImage());
			pstmt.setString(3, product.getType());
			pstmt.setString(4, product.getDescription());
			pstmt.setInt(5, product.getId());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				DBUtils.release(conn, null, pstmt);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}

	@Override
	public boolean deleteProduct(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtils.getConnection();
			String sql = "DELETE FROM " + DBUtils.DB_NAME + ".products WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product.getId());

			int rowCount = pstmt.executeUpdate(); // INSERT, UPDATE or DELETE

			if (rowCount != 0) {
				DBUtils.release(conn, null, pstmt);
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtils.release(conn, null, pstmt);
		return false;
	}
	
	public static void main(String[] args) {
		ProductsDAO productsImpl = new ProductsDAOImpl();
	    List<Product> products = productsImpl.getAllProducts();
	    System.out.println(products);
	}

}
