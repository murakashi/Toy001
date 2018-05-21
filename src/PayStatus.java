

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
 * Servlet implementation class PayStatus
 */
@WebServlet("/PayStatus")
public class PayStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayStatus() {
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

		ArrayList<OrderBean> order_payList = new ArrayList<OrderBean>();

		DBAccess db = new DBAccess();

		double tax = db.select_tax();

		tax = 1 + (tax / 100);

		order_payList = db.select_payList();//発注テーブルに支払い状況を問い合わせる

		session.setAttribute("order_payList", order_payList);

		session.setAttribute("tax", tax);

		RequestDispatcher rd = request.getRequestDispatcher("payStatus.jsp");

		rd.forward(request, response);
	}

}
