

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InsertSyohinCheck
 */
@WebServlet("/InsertSyohinCheck")
public class InsertSyohinCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertSyohinCheck() {
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

		RequestDispatcher rd;

		//エラーメッセージ格納用配列
		String[] message = {"","","","","","","",""};

		/***********入力項目取得************************/
		String s_id = request.getParameter("s_id");

		String s_name = request.getParameter("s_name");

		String category = request.getParameter("category");

		String siire_tanka = request.getParameter("siire_tanka");

		String h_tanka = request.getParameter("h_tanka");

		String safe_zaiko = request.getParameter("safe_zaiko");

		/*********入力チェック***************************/

		try {
			int x = Integer.parseInt("siire_tanka");
		} catch (NumberFormatException e) {
			message[5] = "仕入基準単は数値で入力してください";
		}

		try {
			int y = Integer.parseInt("h_tanka");
		} catch (NumberFormatException e) {
			message[6] = "販売単価は数値で入力してください";
		}

		try {
			int x = Integer.parseInt("safe_zaiko");
		} catch (NumberFormatException e) {
			message[7] = "安全在庫数は数値で入力してください";
		}

		if(s_name.equals("")) {
			message[0] = "商品名を入力してください";
		}

		if(category.equals("未選択")) {
			message[1] = "カテゴリを選択してください";
		}

		if(siire_tanka.equals("")) {
			message[2] = "仕入基準単価を入力してください";
		}

		if(h_tanka.equals("")) {
			message[3] = "販売単価を入力してください";
		}

		if(safe_zaiko.equals("")) {
			message[4] = "安全在庫数を入力してください";
		}

		for(int i=0;i<message.length;i++) {
			if(!(message[i].equals(""))) {
				session.setAttribute("message", message);
				rd = request.getRequestDispatcher("insertSyohinError.jsp");
				rd.forward(request, response);
				return;
			}
		}

		/************ここまで入力値チェック*********************/

		session.setAttribute("s_id", s_id);
		session.setAttribute("s_name", s_name);
		session.setAttribute("category", category);
		session.setAttribute("siire_tanka", siire_tanka);
		session.setAttribute("h_tanka", h_tanka);
		session.setAttribute("safe_zaiko", safe_zaiko);

		rd = request.getRequestDispatcher("insertSyohinCheck.jsp");

		rd.forward(request, response);

	}

}
