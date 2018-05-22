import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bean.CategoryBean;
import bean.OrderBean;
import bean.SiireBean;
import bean.SyouhinBean;

public class DBAccess {

	/***********SQLServer用↓***********************************/
	String sql;

	Connection objCon;
	/**********↑SQLServer用***********************************/

	/**************postgreSQL用↓****************************/
	/*String url = "jdbc:postgresql://localhost:5432/kashi";
	String user = "postgres";
	String pass = "kashi1203";
	String sql;

	Connection objCon = null;

	public DBAccess() {
		try {

		Class.forName("org.postgresql.Driver");
		//コネクションを生成
		objCon = DriverManager.getConnection(url, user, pass);

		//コネクションに対するステートメントを生成
		//PreparedStatement ps = con.prepareStatement(sql);

		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	/**************↑postgreSQL用***************************************/

	/************SQLServer用↓*******************************************/
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
	/***********↑SQLServer用************************************************/

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

	/****************商品IDの最大値+1を取得する******************************/
	public int autoShohinId(){
		try
		{
			//statcment生成
			Statement stmt = objCon.createStatement();

			//クエリ取得
			String sql = "select max(商品ID) + 1 as 商品ID from 商品マスタ";

			System.out.println(sql);

			//問い合わせの実行
			ResultSet rset = stmt.executeQuery(sql);
			rset.next();
			int newShohinId = rset.getInt("商品ID");
			stmt.close();
			return newShohinId;
			}
			 catch(Exception objEx)
			{
				 System.err.println(objEx.getClass().getName()+":"+objEx);
			}
			return -1;
	}

	/******************商品IDを指定して商品をセレクト（発注数量入力に送る）**************************/
	public SyouhinBean select_Syohin(String s_id) {

		sql = "select 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"and 商品ID = "+ s_id +" "+
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
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin;
	}

	/******************カテゴリマスタから全件セレクトする（発注に送る）**************************/
	public ArrayList<CategoryBean> select_Category() {

		sql = "select * from カテゴリマスタ";

		//selectした結果を格納する用
		ArrayList<CategoryBean> category_list = new ArrayList<CategoryBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				CategoryBean category = new CategoryBean();
				category.setC_id(rs.getString("カテゴリID"));
				category.setC_name(rs.getString("カテゴリ名"));
				category_list.add(category);//ArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return category_list;
	}

	/******************仕入先マスタから全件セレクトする（発注に送る）**************************/
	public ArrayList<SiireBean> select_Siire() {

		sql = "select * from 仕入先マスタ";

		//selectした結果を格納する用
		ArrayList<SiireBean> siire_list = new ArrayList<SiireBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SiireBean siire = new SiireBean();
				siire.setSiire_id(rs.getString("仕入先ID"));
				siire.setSiire_name(rs.getString("仕入先名"));
				siire_list.add(siire);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return siire_list;
	}


	/******************在庫から安全在庫数を下回っている商品をセレクトする（発注検索結果で使う）**************************/
	public ArrayList<SyouhinBean> select_SyohinA() {

		/*String sql ="select 商品マスタ.商品ID,安全在庫数,sum(在庫数) as 在庫数合計\r\n" +
				"from 商品マスタ inner join 在庫 \r\n" +
				"on 商品マスタ.商品ID = 在庫.商品ID where 削除フラグ=0\r\n" +
				"group by 商品マスタ.商品ID,安全在庫数\r\n" +
				"having sum(在庫数) < 安全在庫数";*/

		String sql ="select 商品マスタ.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,sum(在庫数) as 在庫数合計\r\n" +
				"from 商品マスタ inner join 在庫 \r\n" +
				"on 商品マスタ.商品ID = 在庫.商品ID "+
				"inner join カテゴリマスタ "+
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID "+
				"where 削除フラグ = '0' " +
				"group by 商品マスタ.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数\r\n" +
				"having sum(在庫数) < 安全在庫数";

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
				rs.getInt("在庫数合計");
				syohin_list.add(syohin);
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin_list;
	}

	/******************在庫から商品名、カテゴリ名を指定して商品をセレクトする（発注検索結果で使う）**************************/
	public ArrayList<SyouhinBean> select_SyohinB(String s_name,String c_id) {

		/*sql = "select 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"inner join 在庫 "+
				"on 商品マスタ.商品ID = 在庫.商品ID "+
				"where 削除フラグ = '0' ";*/

		sql = "select 商品マスタ.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,COALESCE(sum(在庫数),0) as 在庫数\r\n" +
				"from 商品マスタ inner join カテゴリマスタ\r\n" +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID\r\n" +
				"left outer join 在庫\r\n" +
				"on 商品マスタ.商品ID = 在庫.商品ID\r\n" +
				"where 1=1 ";
				/*+ "商品名 like '%の%'\r\n" +
				"and 商品マスタ.カテゴリID = '01'\r\n" +
				"group by 商品マスタ.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数\r\n" +
				"order by 商品マスタ.商品ID";*/

		//商品名とカテゴリIDどちらも入力されている場合
		if(!(s_name.equals("")) && !(c_id.equals("未選択"))) {
			sql = sql + "and 商品名 like '%"+s_name+"%' and 商品マスタ.カテゴリID = '"+c_id+"' ";
		}

		//商品名が未入力で、カテゴリIDが入力されている場合
		if(s_name.equals("") && !(c_id.equals("未選択"))) {
			sql = sql + "and 商品マスタ.カテゴリID = '"+c_id+"' ";
		}

		//商品名が入力されていて、カテゴリIDが未入力の場合
		if(!(s_name.equals("")) && c_id.equals("未選択")) {
			sql = sql + "and 商品名 like '%"+s_name+"%' ";
		}

		sql = sql + "group by 商品マスタ.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 "+
		            "order by 商品マスタ.商品ID";


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
				syohin_list.add(syohin);
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin_list;
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
	public int insert_Order(int max_id,String s_id,String siire_id,String count,String price) {

		//SQLServer用
		//sql = "insert into 発注 values("+ max_id +","+ s_id +",'"+ siire_id +"',"+ count +","+price+",GETDATE(),'0')";

		//postgres用↓
		sql = "insert into 発注 values("+ max_id +","+ s_id +",'"+ siire_id +"',"+ count +","+price+",(select current_date),'0')";

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

		sql = "select 伝票ID,仕入先名,発注日,発注.商品ID,商品名,発注数 "+
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
				order.setO_date(rs.getDate("発注日"));
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
	/*public ArrayList<OrderBean> select_payList() {

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
	}*/

	/**********消費税マスタから消費税率を求める（1.08）**********************************************/
	public float select_tax() {

		sql = "select 消費税率 " +
				"from 消費税マスタ";

		//selectした結果を格納する用
		float tax = 0;

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				tax = rs.getLong("消費税率");
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return tax;
	}

	/**********発注テーブルから入庫フラグが1のものをセレクトする（支払状況ページで使用する）******/
	public ArrayList<OrderBean> select_payList(float tax) {

		sql = "select 伝票ID,仕入先名,floor(sum(発注数*発注.仕入基準単価*"+tax+")) as 支払合計金額 " +
				"from 発注 inner join 仕入先マスタ " +
				"on 発注.仕入先ID = 仕入先マスタ.仕入先ID " +
				"inner join 商品マスタ "+
				"on 発注.商品ID = 商品マスタ.商品ID "+
				"where 入庫フラグ = '1' " +
				"group by 伝票ID,仕入先名 " +
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
				order.setKingaku(rs.getInt("支払合計金額"));
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

	/******************詳細ボタンが押されたら支払の詳細をセレクトする（支払状況詳細で使う）**************************/
	public ArrayList<OrderBean> select_PayDetail(String o_id,float tax) {

		sql = "select 伝票ID,仕入先名,発注日,発注.商品ID,商品名,発注数,発注.仕入基準単価,"+
				"floor((発注.仕入基準単価*発注数*"+tax+")) as 金額 "+
				"from 発注 inner join 仕入先マスタ "+
				"on 発注.仕入先ID = 仕入先マスタ.仕入先ID "+
				"inner join 商品マスタ "+
				"on 発注.商品ID = 商品マスタ.商品ID "+
				"where 伝票ID = "+ o_id;

		//selectした結果を格納する用
		ArrayList<OrderBean> paydetail_list = new ArrayList<OrderBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				OrderBean order = new OrderBean();
				order.setO_id(rs.getInt("伝票ID"));
				order.setSiire_name(rs.getString("仕入先名"));
				order.setO_date(rs.getDate("発注日"));
				order.setS_id(rs.getInt("商品ID"));
				order.setS_name(rs.getString("商品名"));
				order.setO_count(rs.getInt("発注数"));
				order.setBaseprice(rs.getInt("仕入基準単価"));
				order.setKingaku(rs.getInt("金額"));
				paydetail_list.add(order);//ArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return paydetail_list;
	}

	/******************支払の合計金額をセレクトする（支払状況詳細で使う）**************************/
	public int select_PaySum(String o_id,float tax) {

		sql = "select 伝票ID,仕入先名,floor(sum(発注数*発注.仕入基準単価*"+tax+")) as 支払合計金額 " +
				"from 発注 inner join 仕入先マスタ " +
				"on 発注.仕入先ID = 仕入先マスタ.仕入先ID " +
				"inner join 商品マスタ "+
				"on 発注.商品ID = 商品マスタ.商品ID "+
				"where 入庫フラグ = '1' " +
				"and 伝票ID = "+ o_id +" "+
				"group by 伝票ID,仕入先名 ";

		//selectした結果を格納する用
		int sum = 0;

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				sum = rs.getInt("支払合計金額");
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return sum;
	}

	/*******入金ボタン押したら発注テーブルの入庫フラグ2（支払済み）にする***************************/
	public int pay(String o_id) {

		sql = "update 発注 set 入庫フラグ = '2' where 伝票ID = "+ o_id;

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

	/**************ここから統合分（佐藤さん）***********************************************************************/

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
	/***消費税をもらってくる*****************************************************/
	public int getTax() {
		int rset = 0;
		try
		{
			Statement stmt = objCon.createStatement();
			String sql = "select 消費税率 from 消費税マスタ";

			ResultSet result = stmt.executeQuery(sql);
			result.next();
			rset = result.getInt("消費税率");

			stmt.close();

		}
		catch(Exception objEx)
		{
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());
		}

		return rset;

	}

	/***在庫マイナスを追加する******************************************************/
	public int zaikoReduce(String syouID,int salNum2) {

		try {
			Statement stmt = objCon.createStatement();

			String sql = "insert into 在庫 "
		 		+ "values ((select max(入出庫ID)+1 from 在庫), "+syouID+","+salNum2+")";
			int rset=stmt.executeUpdate(sql);
			stmt.close();
			return rset;
		}
		catch(Exception objEx) {
			System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());

		}
		//リターン処理
		return 0;

	}


	/***売上を入力する（正常販売なので入庫フラグは0）*************************************************/
	public int register(String syouID,String day,String salNum,String tanka,String hason,int taxIn) {

		try {
			Statement stmt = objCon.createStatement();

			String sql = "insert into 売上 "
		 		+ "values ((select max(売上ID)+1 from 売上), "+syouID+", '"+day+"', "+salNum+", "+tanka+", '"+hason+"', "+taxIn+")";
			int rset=stmt.executeUpdate(sql);
			stmt.close();
			return rset;
		}
		catch(Exception objEx) {
			System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());

		}
		//リターン処理
		return 0;

	}
	/***危険在庫を見つける***********************************************************************/
	public ArrayList<String[]> getRiskData(){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{
				Statement stmt = objCon.createStatement();
				String sql ="select 商品マスタ.商品ID,安全在庫数,sum(在庫数) as 在庫数合計\r\n" +
						"from 商品マスタ inner join 在庫 \r\n" +
						"on 商品マスタ.商品ID = 在庫.商品ID where 削除フラグ=0\r\n" +
						"group by 商品マスタ.商品ID,安全在庫数\r\n" +
						"having sum(在庫数) < 安全在庫数";

				ResultSet rset = stmt.executeQuery(sql);
				while(rset.next())
				{
					String[] recdata = new String[1];
					recdata[0] = rset.getString("商品ID");
					ret.add(recdata);
				}
				rset.close();
				stmt.close();
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());
			}
			return ret;
	}

	/***売上データを取ってくる*******************************************************************************************/
	public ArrayList<String[]> getUriageData(){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{
				Statement stmt = objCon.createStatement();
				String sql ="select * from 売上 inner join 商品マスタ on 売上.商品ID = 商品マスタ.商品ID where 売上フラグ=0 order by 売上日";

				ResultSet rset = stmt.executeQuery(sql);
				while(rset.next())
				{
					String[] recdata = new String[4];
					recdata[0] = rset.getString("売上日");
					recdata[1] = rset.getString("商品名");
					recdata[2] = rset.getString("売上数");
					recdata[3] = rset.getString("販売単価");

					ret.add(recdata);
				}
				rset.close();
				stmt.close();
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());
			}
			return ret;
	}

	/***売上データを取ってくる(年)**********************************************************/
	public ArrayList<String[]> getUriageYearData(){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{

				Date date = new Date();
				Date date2 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

				for(int y=2005;y<=2018;y++) {
				date = df.parse(y+"/01/01");
				date2 = df.parse(y+"/12/31");

				}

				Statement stmt = objCon.createStatement();
				//String sql ="select * from 売上 inner join 商品マスタ on 売上.商品ID = 商品マスタ.商品ID where 売上日 between  '"+df.format(date)+"' and '"+df.format(date2)+"'order by 売上日 ";
				String sql ="SELECT DATEPART(YEAR, 売上日)as 年,COUNT(*)as 件数,sum(売上数*売上.販売単価)as 金額,sum(売上数*仕入基準単価) as 仕入金額 FROM 売上 inner join 商品マスタ on 売上.商品ID = 商品マスタ.商品ID where 売上フラグ=0 GROUP BY DATEPART (YEAR, 売上日) order by DATEPART(YEAR, 売上日)";




				ResultSet rset = stmt.executeQuery(sql);
				while(rset.next())
				{
					String[] recdata = new String[4];
					recdata[0] = rset.getString("年");
					recdata[1] = rset.getString("件数");
					recdata[2] = rset.getString("金額");
					recdata[3] = rset.getString("仕入金額");

					ret.add(recdata);
				}
				rset.close();
				stmt.close();
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				objEx.printStackTrace();
				//System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());
			}
			return ret;
	}

	/***売上検索(年)**************************************************************************/
	public ArrayList<String[]> UriageYearDataSearch(String year){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{


				Statement stmt = objCon.createStatement();
				//String sql ="select * from 売上 inner join 商品マスタ on 売上.商品ID = 商品マスタ.商品ID where 売上日 between  '"+df.format(date)+"' and '"+df.format(date2)+"'order by 売上日 ";
				String sql ="SELECT DATEPART(YEAR, 売上日)as 年,COUNT(*)as 件数,sum(売上数*売上.販売単価)as 金額,sum(売上数*仕入基準単価) as 仕入金額 FROM 売上 inner join 商品マスタ on 売上.商品ID = 商品マスタ.商品ID where DATEPART(YEAR, 売上日)='"+year+"' and 売上フラグ=0 GROUP BY DATEPART (YEAR, 売上日)";




				ResultSet rset = stmt.executeQuery(sql);
				while(rset.next())
				{
					String[] recdata = new String[4];
					recdata[0] = rset.getString("年");
					recdata[1] = rset.getString("件数");
					recdata[2] = rset.getString("金額");
					recdata[3] = rset.getString("仕入金額");

					ret.add(recdata);
				}
				rset.close();
				stmt.close();
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				objEx.printStackTrace();
				//System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());
			}
			return ret;
	}

		//売上検索
	public ArrayList<String[]> UriageSerch(String name,String category){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{
				Statement stmt = objCon.createStatement();
				String sql="select * from 売上 inner join 商品マスタ on 売上.商品ID = 商品マスタ.商品ID"
						+ " where 売上フラグ=0";

				if(!name.equals("")){
					sql+=" and 商品名 like '%"+name+"%'";
				}
				if(!category.equals("")){
					sql+=" and カテゴリID='"+category+"'";
				}

				sql+="order by 売上日 ";
				System.out.println(sql);
				ResultSet rset = stmt.executeQuery(sql);
				while(rset.next())
				{
					String[] recdata = new String[4];
					recdata[0] = rset.getString("売上日");
					recdata[1] = rset.getString("商品名");
					recdata[2] = rset.getString("売上数");
					recdata[3] = rset.getString("販売単価");

					ret.add(recdata);
				}
				rset.close();
				stmt.close();
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				System.err.println(objEx.getClass().getName()+":"+objEx.getMessage());
			}
			return ret;
	}


//	在庫系処理　ここから
	/******************商品マスタから検索条件を指定してセレクトする**************************/
	public ArrayList<SyouhinBean> select_Single_Syohin(String s_id) {

		sql = "select 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,sum(在庫数) as 在庫残量 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"inner join 在庫 " +
				"on 商品マスタ.商品ID = 在庫.商品ID " +
				"where 削除フラグ = '0' " +
				"and 商品ID = '"+ s_id +"' "+
				"group by 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"order by 残量.商品ID";

		//selectした結果を格納する用
		ArrayList<SyouhinBean>  syohin = new ArrayList<SyouhinBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SyouhinBean sb = new SyouhinBean();
				sb.setS_id(rs.getInt("商品ID"));
				sb.setS_name(rs.getString("商品名"));
				sb.setC_id(rs.getString("カテゴリ名"));
				sb.setBaseprice(rs.getInt("仕入基準単価"));
				sb.setHtanka(rs.getInt("販売単価"));
				sb.setZaiko(rs.getInt("在庫残量"));
				syohin.add(sb);
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

	public ArrayList<SyouhinBean> select_Multi_Syohin(String s_name) {

		sql = "select 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,sum(在庫数) as 在庫残量 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"inner join 在庫 " +
				"on 商品マスタ.商品ID = 在庫.商品ID " +
				"where 削除フラグ = '0' " +
				"and 商品名 Like '%"+ s_name +"%' "+
				"group by 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"order by 在庫.商品ID";



		//selectした結果を格納する用
		ArrayList<SyouhinBean>  syohin = new ArrayList<SyouhinBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SyouhinBean sb = new SyouhinBean();
				sb.setS_id(rs.getInt("商品ID"));
				sb.setS_name(rs.getString("商品名"));
				sb.setC_id(rs.getString("カテゴリ名"));
				sb.setBaseprice(rs.getInt("仕入基準単価"));
				sb.setHtanka(rs.getInt("販売単価"));
				sb.setZaiko(rs.getInt("在庫残量"));
				syohin.add(sb);
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin;
	}

	public ArrayList<SyouhinBean> select_DengerSyohin() {

		sql = "select 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,在庫残量 from " +
				"(select 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,sum(在庫数) as 在庫残量 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"inner join 在庫 " +
				"on 商品マスタ.商品ID = 在庫.商品ID " +
				"where 削除フラグ = '0' " +
				"group by 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数)as 残量 " +
				"where 安全在庫数 > 在庫残量 "+
				"order by 残量.商品ID";

		//selectした結果を格納する用
		ArrayList<SyouhinBean>  syohin = new ArrayList<SyouhinBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SyouhinBean sb = new SyouhinBean();
				sb.setS_id(rs.getInt("商品ID"));
				sb.setS_name(rs.getString("商品名"));
				sb.setC_id(rs.getString("カテゴリ名"));
				sb.setBaseprice(rs.getInt("仕入基準単価"));
				sb.setHtanka(rs.getInt("販売単価"));
				sb.setZaiko(rs.getInt("在庫残量"));
				syohin.add(sb);
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin;
	}

	public ArrayList<SyouhinBean> select_Category(String categoryid) {

		sql = "select 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,sum(在庫数) as 在庫残量 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"inner join 在庫 " +
				"on 商品マスタ.商品ID = 在庫.商品ID " +
				"where 削除フラグ = '0' " +
				"and カテゴリマスタ.カテゴリID = '"+ categoryid +"' "+
				"group by 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"order by 在庫.商品ID";


		//selectした結果を格納する用
		ArrayList<SyouhinBean>  syohin = new ArrayList<SyouhinBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SyouhinBean sb = new SyouhinBean();
				sb.setS_id(rs.getInt("商品ID"));
				sb.setS_name(rs.getString("商品名"));
				sb.setC_id(rs.getString("カテゴリ名"));
				sb.setBaseprice(rs.getInt("仕入基準単価"));
				sb.setHtanka(rs.getInt("販売単価"));
				sb.setZaiko(rs.getInt("在庫残量"));
				syohin.add(sb);
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin;
	}

	public ArrayList<SyouhinBean> select_Syohin_Category(String s_name, String categoryid) {

		sql = "select 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数,sum(在庫数) as 在庫残量 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"inner join 在庫 " +
				"on 商品マスタ.商品ID = 在庫.商品ID " +
				"where 削除フラグ = '0' " +
				"and 商品名 Like '%"+ s_name +"%' "+
				"and 商品マスタ.カテゴリID = '"+ categoryid +"' "+
				"group by 在庫.商品ID,商品名,カテゴリ名,仕入基準単価,販売単価,安全在庫数 " +
				"order by 在庫.商品ID";


		//selectした結果を格納する用
		ArrayList<SyouhinBean>  syohin = new ArrayList<SyouhinBean>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				SyouhinBean sb = new SyouhinBean();
				sb.setS_id(rs.getInt("商品ID"));
				sb.setS_name(rs.getString("商品名"));
				sb.setC_id(rs.getString("カテゴリ名"));
				sb.setBaseprice(rs.getInt("仕入基準単価"));
				sb.setHtanka(rs.getInt("販売単価"));
				sb.setZaiko(rs.getInt("在庫残量"));
				syohin.add(sb);
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin;
	}
//	在庫系処理　ここまで


//	売上確認系処理ここから
	public int getStartYear() {
		int year = 0;

		try
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);

			Statement stmt = objCon.createStatement();
			 sql = "SELECT * FROM 開始年";


			ResultSet rset = stmt.executeQuery(sql);
			while(rset.next())
			{
				year = rset.getInt("年");
				break;
			}
			rset.close();
			stmt.close();
		}
		catch(Exception objEx)
		{
			//コンソールに「接続エラー内容」を表示
			objEx.printStackTrace();;
		}

		return year;
	}

	public ArrayList<String[]> getUriageMonthData(){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);

				Statement stmt = objCon.createStatement();
				 sql = "SELECT 年,月,売上件数,金額,仕入金額 FROM "+
						 "(SELECT DATEPART(YEAR, 売上日) as 年 "+
						 ", DATEPART(MONTH, 売上日) as 月 "+
						 ", COUNT(*) as 売上件数 "+
						 ", SUM(売上数 * 売上.販売単価) as 金額 "+
						 ", SUM(売上数 * 仕入基準単価) as 仕入金額 "+
						 "FROM 売上 "+
						 "inner join 商品マスタ "+
						 "on 売上.商品ID = 商品マスタ.商品ID "+
						 "where 売上フラグ = '0' "+
						 "GROUP BY DATEPART(YEAR, 売上日) "+
						 ", DATEPART(MONTH, 売上日)) as 月別売上 order by 年";


				ResultSet rset = stmt.executeQuery(sql);
				while(rset.next())
				{
					String[] recdata = new String[4];
					recdata[0] = rset.getString("年") +"-"+ rset.getString("月");
					recdata[1] = rset.getString("売上件数");
					recdata[2] = rset.getString("金額");
					recdata[3] = rset.getString("仕入金額");

					ret.add(recdata);
				}
				rset.close();
				stmt.close();
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				objEx.printStackTrace();;
			}
			return ret;
	}

	/***売上データを取ってくる(年)***/
	public ArrayList<String[]> getUriageMonthData(String year){
		ArrayList<String[]> ret = new ArrayList<String[]>();
			try
			{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);

					Statement stmt = objCon.createStatement();
					 sql = "SELECT 年,月,売上件数,金額,仕入金額 FROM "+
							 "(SELECT DATEPART(YEAR, 売上日) as 年 "+
							 ", DATEPART(MONTH, 売上日) as 月 "+
							 ", COUNT(*) as 売上件数 "+
							 ", SUM(売上数 * 売上.販売単価) as 金額 "+
							 ", SUM(売上数 * 仕入基準単価) as 仕入金額 "+
							 "FROM 売上 "+
							 "inner join 商品マスタ "+
							 "on 売上.商品ID = 商品マスタ.商品ID "+
							 "where 売上フラグ = '0' "+
							 "GROUP BY DATEPART(YEAR, 売上日) "+
							 ", DATEPART(MONTH, 売上日)) as 月別売上 "
							 + "where 年 = '"+ year +"' order by 月";


					ResultSet rset = stmt.executeQuery(sql);
					while(rset.next())
					{
						String[] recdata = new String[4];
						recdata[0] = rset.getString("年") +"-"+ rset.getString("月");
						recdata[1] = rset.getString("売上件数");
						recdata[2] = rset.getString("金額");
						recdata[3] = rset.getString("仕入金額");

						ret.add(recdata);
					}
					rset.close();
					stmt.close();
//				}
			}
			catch(Exception objEx)
			{
				//コンソールに「接続エラー内容」を表示
				objEx.printStackTrace();;
			}
			return ret;
	}


//	商品一覧系処理ここから
	public ArrayList<SyouhinBean> All_SyohinData() {

		sql = "select 商品ID,商品名,カテゴリ名,商品マスタ.仕入基準単価,販売単価 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"group by 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価 " +
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


	public ArrayList<SyouhinBean> SyohinList_CategorySearch(String category) {

		sql = "select 商品ID,商品名,カテゴリマスタ.カテゴリ名,商品マスタ.仕入基準単価,販売単価 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"and カテゴリマスタ.カテゴリID = '" + category +"' "+
				"group by 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価 " +
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


	public ArrayList<SyouhinBean> SyohinList_NameSearch(String syouhinname) {



		sql = "select 商品ID,商品名,カテゴリ名,商品マスタ.仕入基準単価,販売単価 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"and 商品名 LIKE '%" + 	syouhinname +"%' "+
				"group by 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価 " +
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

	public ArrayList<SyouhinBean> SyohinList_Name_Category_Search(String syouhinname, String category) {

		sql = "select 商品ID,商品名,カテゴリ名,商品マスタ.仕入基準単価,販売単価 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"and 商品名 LIKE '%" + 	syouhinname +"%' "+
				"and カテゴリマスタ.カテゴリID = '" + 	category +"' "+
				"group by 商品ID,商品名,カテゴリ名,仕入基準単価,販売単価 " +
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
	/*********************ここまで佐藤君***************************************************************/

	/*************ここまで統合分************************************************************************/

	/************ここから追加分***********************************/
	public int insert_Syohin(String s_id,String s_name,String category,String siire_tanka,String h_tanka,String safe_zaiko) {

		//SQLServer用
		sql = "insert into 商品マスタ values("+ s_id +",'"+ s_name +"','"+ category +"',"+ siire_tanka +","+h_tanka+","+safe_zaiko+",'0')";

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



}
