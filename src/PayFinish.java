

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PayFinish
 */
@WebServlet("/PayFinish")
public class PayFinish extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayFinish() {
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

		try {
		String o_id = request.getParameter("o_id");//hiddenの伝票ID取得

		String pay_date = request.getParameter("pay_date");

		RequestDispatcher rd;

		DBAccess db = new DBAccess();


		/************日付未入力の場合のエラー処理***********/
		if(pay_date.equals("") || pay_date == null) {
			rd = request.getRequestDispatcher("payError.jsp");
			rd.forward(request, response);
			return;
		}

		db.pay(o_id);

		rd = request.getRequestDispatcher("payFinish.jsp");

		rd.forward(request, response);
		}catch (NullPointerException e) {
			RequestDispatcher rd2 = request.getRequestDispatcher("payError.jsp");
			rd2.forward(request, response);
			return;
		}
	}

}
