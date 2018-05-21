

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.CategoryBean;
import bean.SyouhinBean;

/**
 * Servlet implementation class OrderSearch
 */
@WebServlet("/OrderSearch")
public class OrderSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderSearch() {
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

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		String s_name = request.getParameter("s_name");

		String c_id = request.getParameter("category");

		String dflg = request.getParameter("dflg");

		ArrayList<SyouhinBean> syohin = new ArrayList<SyouhinBean>();

		ArrayList<CategoryBean> category = new ArrayList<CategoryBean>();

		DBAccess db = new DBAccess();

		if(dflg.equals("denger")) {
			syohin = db.select_SyohinA();
		}else {
			syohin = db.select_SyohinB(s_name,c_id);
		}

		category = db.select_Category();

		session.setAttribute("syohin", syohin);
		session.setAttribute("category", category);

		RequestDispatcher rd = request.getRequestDispatcher("orderA.jsp");

		rd.forward(request, response);


	}

}
