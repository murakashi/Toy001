import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import bean.OrderBean;
import bean.SyouhinBean;

public class DBAccess {

	String sql;

	Connection objCon;

	public DBAccess() {
		try {
			//JDBCドライバを設定する
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			//データベース名、ユーザ名、パスワード
			String connUrl = "jdbc:sqlserver://STRA-CL0061\\SQLEXPRESS2012;database=Toy;" +
					"integratedSecurity=false;user=sa;password=Step2154822";

			//接続開始
			objCon = DriverManager.getConnection(connUrl);//
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
			System.out.println(sql);
		}
	}

	/******************ログインする**************************/
	public ArrayList<String[]> login(String u_id, String pass) {

		sql = "select * from ユーザマスタ where ユーザID = '" + u_id + "' and パスワード = '" + pass + "'";

		//selectした結果を格納する用
		ArrayList<String[]> list = new ArrayList<String[]>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String[] recdata = new String[3];
				recdata[0] = rs.getString("ユーザID");
				recdata[1] = rs.getString("ユーザ名");
				recdata[2] = rs.getString("パスワード");
				list.add(recdata);
				break;
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return list;
	}

	/******************商品マスタから全件セレクトする（発注に送る）**************************/
	public ArrayList<SyouhinBean> select_AllSyohin() {

		sql = "select 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"order by 商品ID";

		//selectした結果を格納する用
		ArrayList<SyouhinBean> syohin_list = new ArrayList<SyouhinBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SyouhinBean syohin = new SyouhinBean();
				syohin.setS_id(rs.getInt("商品ID"));
				syohin.setS_name(rs.getString("商品名"));
				syohin.setC_id(rs.getString("カテゴリ名"));
				syohin.setBaseprice(rs.getInt("仕入基準単価"));
				syohin.setHtanka(rs.getInt("販売単価"));
				syohin.setSafezaiko(rs.getInt("安全在庫数"));
				syohin_list.add(syohin);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin_list;
	}

	/******************商品マスタから検索条件を指定してセレクトする**************************/
	public SyouhinBean select_Syohin(String s_id) {

		sql = "select 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"and 商品ID = '"+ s_id +"' "+
				"order by 商品ID";

		//selectした結果を格納する用
		SyouhinBean syohin = new SyouhinBean();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				syohin.setS_id(rs.getInt("商品ID"));
				syohin.setS_name(rs.getString("商品名"));
				syohin.setC_id(rs.getString("カテゴリ名"));
				syohin.setBaseprice(rs.getInt("仕入基準単価"));
				syohin.setHtanka(rs.getInt("販売単価"));
				syohin.setSafezaiko(rs.getInt("安全在庫数"));
				break;
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin;
	}

	/***削除する（商品IDを指定して対象の商品マスタの削除フラグを'1'に更新する）***/
	public void delete_Syohin(String s_id) {

		sql = "update 商品マスタ set 削除フラグ = '1' where 商品ID = '" + s_id + "'";

		try {
			Statement stmt = objCon.createStatement();

			stmt.executeUpdate(sql);

			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
	}

	/********仕入先IDをもとに仕入先名を取得する（発注金額確認画面で使う）*******/
	public String get_SiireName(String siire_id) {

		sql = "select 仕入先名 from 仕入先マスタ where 仕入先ID = '"+ siire_id +"'";

		//selectした結果を格納する用
		String siire_name = null;
		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				siire_name = rs.getString("仕入先名");
				break;
			}
			//rs.close();
			//stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return siire_name;
	}

	/********商品IDをもとに仕入基準単価を取得する（発注金額確認画面で使う）*******/
	public int get_SiireKingaku(String s_id,int count) {

		sql = "select (商品マスタ.仕入基準単価*"+ count +") as 金額 "+
			  "from 商品マスタ "+
			  "where 商品ID = "+s_id;

		//selectした結果を格納する用
		int siire_kin = 0;
		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				siire_kin = rs.getInt("金額");
				break;
			}
			//rs.close();
			//stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return siire_kin;
	}

	/********伝票番号を自動的に振るための処理（発注金額確認画面で使う）*******/
	public int get_MaxId() {

		sql = "select max(伝票ID)+1 as 最大値 from 発注";

		//selectした結果を格納する用
		int max_id = 0;
		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				max_id = rs.getInt("最大値");
				break;
			}
			//rs.close();
			//stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return max_id;
	}

	/*******発注テーブルにインサートする***************************/
	public int insert_Order(int max_id,String s_id,String siire_id,String count) {

		sql = "insert into 発注 values("+ max_id +","+ s_id +",'"+ siire_id +"',"+ count +",GETDATE(),'0')";

		//selectした結果を格納する用
		int result=0;
		try {
			Statement stmt = objCon.createStatement();

			result = stmt.executeUpdate(sql);
			//rs.close();
			//stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return result;
	}

	/******************発注テーブルから入庫フラグが0のものをセレクトする（発注状況で使う）**************************/
	public ArrayList<OrderBean> select_order() {

		sql = "select 伝票ID,仕入先名,発注日 " +
				"from 発注 inner join 仕入先マスタ " +
				"on 発注.仕入先ID = 仕入先マスタ.仕入先ID " +
				"where 入庫フラグ = '0' " +
				"group by 伝票ID,仕入先名,発注日 " +
				"order by 伝票ID";

		//selectした結果を格納する用
		ArrayList<OrderBean> order_list = new ArrayList<OrderBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrderBean order = new OrderBean();
				order.setO_id(rs.getInt("伝票ID"));
				order.setSiire_name(rs.getString("仕入先名"));
				order.setO_date(rs.getDate("発注日"));
				order_list.add(order);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return order_list;
	}

	/******************詳細ボタンが押されたら発注の詳細をセレクトする（発注状況詳細で使う）**************************/
	public ArrayList<OrderBean> select_OrderDetail(String o_id) {

		sql = "select 伝票ID,仕入先名,発注.商品ID,商品名,発注数 "+
				"from 発注 inner join 仕入先マスタ "+
				"on 発注.仕入先ID = 仕入先マスタ.仕入先ID "+
				"inner join 商品マスタ "+
				"on 発注.商品ID = 商品マスタ.商品ID "+
				"where 伝票ID = "+ o_id;

		//selectした結果を格納する用
		ArrayList<OrderBean> orderdetail_list = new ArrayList<OrderBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrderBean order = new OrderBean();
				order.setO_id(rs.getInt("伝票ID"));
				order.setSiire_name(rs.getString("仕入先名"));
				order.setS_id(rs.getInt("商品ID"));
				order.setS_name(rs.getString("商品名"));
				order.setO_count(rs.getInt("発注数"));
				orderdetail_list.add(order);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return orderdetail_list;
	}

	/*************入庫ボタン押したら発注テーブルの入庫フラグを0に更新する******/
	public int update_order(String o_id) {

		sql = "update 発注 set 入庫フラグ = '1' where 伝票ID ="+ o_id;

		//selectした結果を格納する用
		int result=0;
		try {
			Statement stmt = objCon.createStatement();

			result = stmt.executeUpdate(sql);
			//rs.close();
			//stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return result;
	}

	/***********入庫ボタン押したら在庫テーブルにインサートする（未完成）******/
	public int insert_Zaiko(String s_id,String count) {

		sql = "insert into 在庫 values((select max(入出庫ID)+1 from 在庫),"+ s_id +","+ count +")";

		//selectした結果を格納する用
		int result=0;
		try {
			Statement stmt = objCon.createStatement();

			result = stmt.executeUpdate(sql);
			//rs.close();
			//stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return result;
	}

	/******************支払状況の情報（発注テーブルの入庫フラグが1のもの）をセレクトする（支払状況で使う）**************************/
	public ArrayList<OrderBean> select_payList() {

		sql = "select 伝票ID,仕入先名,sum(発注数量*仕入基準単価) as 合計金額 "+
				"from 発注 inner join 仕入先マスタ "+
				"on 発注.仕入先ID = 仕入先マスタ.仕入先ID "+
				"inner join 商品マスタ "+
				"on 発注.商品ID = 商品マスタ.商品ID "+
				"where 入庫フラグ = '1' "+
				"group by 伝票ID,仕入先名";

		//selectした結果を格納する用
		ArrayList<OrderBean> orderdetail_list = new ArrayList<OrderBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrderBean order = new OrderBean();
				order.setO_id(rs.getInt("伝票ID"));
				order.setSiire_name(rs.getString("仕入先名"));
				order.setS_id(rs.getInt("合計金額"));
				orderdetail_list.add(order);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return orderdetail_list;
	}


}
