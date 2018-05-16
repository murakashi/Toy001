
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
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCheck() {
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
		/***************文字化け対策？************/
		request.setCharacterEncoding("UTF-8");

		/***login.jspで入力されたユーザIDとパスワードを取得する***/
		String u_id = request.getParameter("u_id");
		String pass = request.getParameter("pass");

		/***DBに接続するためにDBクラスのインスタンスを生成する***/
		DBAccess db = new DBAccess();

		/***入力情報をもとにログインメソッドを呼び、結果を格納する***/
		ArrayList<String[]> u_list = db.login(u_id, pass);

		/***遷移するための用意***/
		RequestDispatcher rd;

		/***ログイン情報を受け渡すための用意***/
		HttpSession session = request.getSession();
		String login_flag = null;

		/***ログイン成功かどうかの判断**********************/
		if (u_list.size() == 0) {//ログイン失敗の場合
			/***失敗であることを受け渡す（未ログイン時のURL直接入力によるアクセス対策）***/
			login_flag = "NG";
			session.setAttribute("login_flag", login_flag);
			rd = request.getRequestDispatcher("loginNG.jsp");//ログインエラーページの行き先を格納
		} else {//ログイン成功の場合
			/***成功であることを受け渡す（未ログイン時のURL直接入力によるアクセス対策）***/
			login_flag = "OK";
			session.setAttribute("login_flag", login_flag);
			rd = request.getRequestDispatcher("menu.jsp");//メニューページの行き先を格納
		}

		/***対象の遷移ページに遷移する***/
		rd.forward(request, response);
	}
}
