

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.OrderBean;

/**
 * Servlet implementation class PayDetail
 */
@WebServlet("/PayDetail")
public class PayDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		String o_id = request.getParameter("o_id");

		DBAccess db = new DBAccess();

		ArrayList<OrderBean> pay_list = db.select_PayDetail(o_id);

		session.setAttribute("pay_list", pay_list);

		double tax = db.select_tax();

		tax = 1 + (tax / 100);

		session.setAttribute("tax", tax);

		int sum = db.select_PaySum(o_id);

		session.setAttribute("sum", sum);

		RequestDispatcher rd = request.getRequestDispatcher("payDetail.jsp");

		rd.forward(request, response);
	}

}
