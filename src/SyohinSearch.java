

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SyohinSearch
 */
@WebServlet("/SyohinSearch")
public class SyohinSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyohinSearch() {
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

		/***取得した検索項目が文字化けしないようにする***/
		request.setCharacterEncoding("UTF-8");

		String s_name = request.getParameter("s_name");

		DBAccess db = new DBAccess();

		ArrayList<String[]> syohin_list = db.search_Syohin(s_name);

		HttpSession session = request.getSession();

		session.setAttribute("syohin_list", syohin_list);

		RequestDispatcher rd = request.getRequestDispatcher("syohin.jsp");

		rd.forward(request, response);
	}

}