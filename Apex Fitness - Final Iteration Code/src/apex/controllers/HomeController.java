package apex.controllers;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import apex.models.Customer;
import apex.models.Equipment;

@WebServlet("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Overridden doPost method TODO: Comments
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: do equipment user session

		// Get current users equipment
		HttpSession session = request.getSession();
		Customer myCustomer = (Customer) session.getAttribute("customer");
		ArrayList<Equipment> custEquipment = myCustomer.getEquipmentList();
		request.setAttribute("custEq", custEquipment); // Set for jsp page

		// Forward to next page
		getServletContext().getRequestDispatcher("/equipment.jsp").forward(request, response);

	}// End doPost method

}
