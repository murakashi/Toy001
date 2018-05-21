
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OrderSum
 */
@WebServlet("/OrderSum")
public class OrderSum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderSum() {
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

		try {
			//数量取得
			String[] count_arr = request.getParameterValues("count");

			RequestDispatcher rd;

			//仕入先ID取得
			String siire_id = request.getParameter("siire_id");

			/**************仕入先未入力かつ、数値未入力の場合の処理****************/
			int error=0;
			for(String count : count_arr) {
				if(count == null || count.equals("")) {
					error++;
				}
			}

			if(siire_id.equals("未選択") && error > 0) {
				rd = request.getRequestDispatcher("orderErrorE.jsp");
				rd.forward(request, response);
				return;
			}
			/**************ここまで仕入先未入力かつ、数値未入力の場合の処理****************/

			/***************仕入先未入力エラーの場合の処理**********************/
			if (siire_id.equals("") || siire_id.equals("未選択") || siire_id == null) {
				rd = request.getRequestDispatcher("orderErrorD.jsp");
				rd.forward(request, response);
				return;
			}
			/***************ここまで仕入先未入力エラーの場合の処理**********************/

			/***************ここから数値未入力エラーの場合の処理**********************/
			for (String count : count_arr) {
				/*********数量が1つでも未入力だったら********/
				if (count.equals("") || count == null) {
					rd = request.getRequestDispatcher("orderErrorB.jsp");
					rd.forward(request, response);
					return;
				}
			}
			/***************ここまで数値未入力エラーの場合の処理**********************/

			//hiddenで商品ID取得
			String[] id_arr = request.getParameterValues("s_id");

			//hiddenで仕入基準単価取得（postgresで気づいた）
			String[] price_arr = request.getParameterValues("s_basePrice");

			int[] num_arr = new int[count_arr.length];

			int sum = 0;

			DBAccess db = new DBAccess();

			String siire_name = db.get_SiireName(siire_id);

			for (int i = 0; i < num_arr.length; i++) {
				num_arr[i] = Integer.parseInt(count_arr[i]);
				sum = sum + db.get_SiireKingaku(id_arr[i], num_arr[i]);
			}

			/****後の確定画面で発注DBにインサートするためにセッションに格納しておく****/
			session.setAttribute("count_arr", count_arr);//数量を格納している配列

			session.setAttribute("id_arr", id_arr);//商品IDを格納している配列

			session.setAttribute("price_arr", price_arr);//仕入基準単価を格納している配列（postgresで気づいた）

			session.setAttribute("siire_id", siire_id);//仕入先IDを格納している文字列
			/*************************************************************************/

			session.setAttribute("siire_name", siire_name);//仕入先名を格納

			session.setAttribute("siire_kin", sum + "");//発注合計金額を格納

			rd = request.getRequestDispatcher("orderSumCheck.jsp");

			rd.forward(request, response);
		} catch (NumberFormatException e) {
			RequestDispatcher rd2 = request.getRequestDispatcher("orderErrorC.jsp");
			rd2.forward(request, response);
			return;
		}

	}

}
