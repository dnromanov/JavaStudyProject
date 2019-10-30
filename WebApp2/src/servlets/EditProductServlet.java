package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import dao.ProductsDAOImpl;
import model.Product;

/**
 * Servlet implementation class EditProductServlet
 */
@WebServlet("/editproduct")
@MultipartConfig
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditProductServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Product product = new Product();
		ProductsDAOImpl productImpl = new ProductsDAOImpl();
		if (request.getParameter("editProduct") != null) {
			int id = Integer.parseInt(request.getParameter("editProduct"));
			product.setId(id);
			productImpl.getProductById(product);
			request.setAttribute("EDITED_PRODUCT", product);
			request.getRequestDispatcher("editProduct.jsp").forward(request, response);
		}
		if (request.getParameter("addProduct") != null) {
			request.getRequestDispatcher("editProduct.jsp").forward(request, response);
		}
		if (request.getParameter("action") != null) {
//		if (request.getPart("action") != null) {
			String action = request.getParameter("action");
			if (action.equals("edition")) {
				int productid = Integer.parseInt(request.getParameter("productId"));
				product.setId(productid);
				productImpl.getProductById(product);
				product.setName(request.getParameter("productName"));
				product.setType(request.getParameter("productType"));
				product.setDescription(request.getParameter("productDescription"));

				Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
				String fileName = "/productImages/"
						+ Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
				InputStream fileContent = filePart.getInputStream();

				BufferedImage image = null;
				try {
					image = ImageIO.read(fileContent);
					File sourceimage = new File(request.getSession().getServletContext().getRealPath(fileName));
					ImageIO.write(image, "jpeg", sourceimage);
					product.setImage(fileName);
					new ProductsDAOImpl().updateProduct(product);
					List<Product> products = new ProductsDAOImpl().getAllProducts();
					request.setAttribute("products", products);
					request.getRequestDispatcher("/products.jsp").forward(request, response);
				} catch (IOException e) {
				}
			} else if (action.equals("adding")) {
				product.setName(request.getParameter("productName"));
				product.setType(request.getParameter("productType"));
				product.setDescription(request.getParameter("productDescription"));

				Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
				String fileName = "/productImages/"
						+ Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
				InputStream fileContent = filePart.getInputStream();

				BufferedImage image = null;
				try {
					image = ImageIO.read(fileContent);
					File sourceimage = new File(request.getSession().getServletContext().getRealPath(fileName));
					ImageIO.write(image, "jpeg", sourceimage);
					product.setImage(fileName);
					new ProductsDAOImpl().createProduct(product);
					List<Product> products = new ProductsDAOImpl().getAllProducts();
					request.setAttribute("products", products);
					request.getRequestDispatcher("/products.jsp").forward(request, response);
				} catch (IOException e) {
				}
			}

		}
	}
}
