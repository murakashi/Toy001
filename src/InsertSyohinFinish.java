

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InsertSyohinFinish
 */
@WebServlet("/InsertSyohinFinish")
public class InsertSyohinFinish extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertSyohinFinish() {
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

		String s_id = (String)session.getAttribute("s_id");
		String s_name = (String)session.getAttribute("s_name");
		String category = (String)session.getAttribute("category");
		String siire_tanka = (String)session.getAttribute("siire_tanka");
		String h_tanka = (String)session.getAttribute("h_tanka");
		String safe_zaiko = (String)session.getAttribute("safe_zaiko");

		DBAccess db = new DBAccess();

		db.insert_Syohin(s_id,s_name,category,siire_tanka,h_tanka,safe_zaiko);

		RequestDispatcher rd = request.getRequestDispatcher("insertSyohinFinish.jsp");

		rd.forward(request, response);


	}

}
