
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SiireBean;
import bean.SyouhinBean;

/**
 * Servlet implementation class OrderCount
 */
@WebServlet("/OrderCount")
public class OrderCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderCount() {
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
			String[] order_check = request.getParameterValues("order_check");

			session.setAttribute("order_check", order_check);//

			DBAccess db = new DBAccess();

			ArrayList<SyouhinBean> syohin = new ArrayList<SyouhinBean>();

			for (String s_id : order_check) {
				SyouhinBean syohin_bean = new SyouhinBean();
				syohin_bean = db.select_Syohin(s_id);
				syohin.add(syohin_bean);
			}

			session.setAttribute("syohin", syohin);

			ArrayList<SiireBean> siire_list = db.select_Siire();//仕入先のセレクトボックスで使うため

			session.setAttribute("siire_list", siire_list);

			RequestDispatcher rd = request.getRequestDispatcher("orderCount.jsp");

			rd.forward(request, response);

		} catch (NullPointerException e) {//１つもチェックしてない状態で発注ボタン押した場合
			RequestDispatcher rd2 = request.getRequestDispatcher("orderError.jsp");
			rd2.forward(request, response);
			return;
		}

	}

}
