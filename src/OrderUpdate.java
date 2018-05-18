

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
 * Servlet implementation class OrderUpdate
 */
@WebServlet("/OrderUpdate")
public class OrderUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderUpdate() {
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

		String o_id = request.getParameter("o_id");//伝票ID取得

		DBAccess db = new DBAccess();

		db.update_order(o_id);//発注テーブルの入庫フラグを更新(1)にする

		String[] sId_arr = request.getParameterValues("s_id");//商品IDの配列取得

		String[] count_arr = request.getParameterValues("count");//発注数の配列取得

		if(sId_arr != null && count_arr != null) {
			/*************伝票の詳細からインサート***********************/
			for(int i=0;i<sId_arr.length;i++) {
				db.insert_Zaiko(sId_arr[i],count_arr[i]);
			}
		}else {
			/*************詳細いかずにインサート*************************/
			ArrayList<OrderBean> detail_list = new ArrayList<OrderBean>();
			detail_list = db.select_OrderDetail(o_id);
			for(OrderBean order : detail_list) {
				db.insert_Zaiko(order.getS_id()+"",order.getO_count()+"");
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("updateFinish.jsp");

		rd.forward(request, response);
	}

}
