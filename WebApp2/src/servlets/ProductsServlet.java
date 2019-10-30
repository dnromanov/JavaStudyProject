package servlets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.ProductsDAO;
import dao.ProductsDAOImpl;
import model.Product;

/**
 * Servlet implementation class ProductsServlet
 */
@WebServlet("/products")
@MultipartConfig
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductsDAO productsImpl = new ProductsDAOImpl();
	    List<Product> products = productsImpl.getAllProducts();
	    request.setAttribute("products", products);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("description") != null) {
			int productId = Integer.parseInt(request.getParameter("description"));
			Product product = new Product();
			product.setId(productId);
			new ProductsDAOImpl().getProductById(product);
			Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
			String fileName = "/productImages/" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
			InputStream fileContent = filePart.getInputStream();

			BufferedImage image = null;
			try {
				image = ImageIO.read(fileContent);
//	        File sourceimage = new File(request.getSession().getServletContext().getRealPath("/") + (int)(Math.random()*1000) + ".jpeg");
				File sourceimage = new File(request.getSession().getServletContext().getRealPath(fileName));
				ImageIO.write(image, "jpeg", sourceimage);
				product.setImage(fileName);
				new ProductsDAOImpl().updateProduct(product);
				List<Product> products = new ProductsDAOImpl().getAllProducts();
				request.setAttribute("products", products);
				request.getRequestDispatcher("/products.jsp").forward(request, response);
			} catch (IOException e) {
			}
		}
	}

}
