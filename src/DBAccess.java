import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBAccess {

	String url = "jdbc:postgresql://localhost:5432/kashi";
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

	/******************商品マスタから全件セレクトする**************************/
	public ArrayList<String[]> select_AllSyohin() {

		sql = "select 商品ID,商品名,カテゴリ名,仕入単価,販売単価,危険在庫数 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"order by 商品ID";

		//selectした結果を格納する用
		ArrayList<String[]> syohin_list = new ArrayList<String[]>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String[] recdata = new String[6];//とりま配列に1行ごとの情報を詰める
				recdata[0] = rs.getString("商品ID");
				recdata[1] = rs.getString("商品名");
				recdata[2] = rs.getString("カテゴリ名");
				recdata[3] = rs.getString("仕入単価");
				recdata[4] = rs.getString("販売単価");
				recdata[5] = rs.getString("危険在庫数");
				syohin_list.add(recdata);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin_list;
	}

	/**************削除対象の商品のデータをセレクトする（たぶん不要になる）********/
	public String[] select_Syohin(String s_id) {

		sql = "select * from 商品マスタ where 商品ID = '" + s_id + "'";

		//selectした結果を格納する用
		String[] delete_arr = new String[2];

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				delete_arr[0] = rs.getString("商品ID");
				delete_arr[1] = rs.getString("商品名");
				break;
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return delete_arr;
	}

	/******************商品マスタから検索条件を指定してセレクトする**************************/
	public ArrayList<String[]> search_Syohin(String s_name) {

		sql = "select 商品ID,商品名,カテゴリ名,仕入単価,販売単価,危険在庫数 " +
				"from 商品マスタ inner join カテゴリマスタ " +
				"on 商品マスタ.カテゴリID = カテゴリマスタ.カテゴリID " +
				"where 削除フラグ = '0' " +
				"and 商品名 like'%"+ s_name +"%' " +
				"order by 商品ID";

		//selectした結果を格納する用
		ArrayList<String[]> syohin_list = new ArrayList<String[]>();

		try {
			Statement stmt = objCon.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String[] recdata = new String[6];//とりま配列に1行ごとの情報を詰める
				recdata[0] = rs.getString("商品ID");
				recdata[1] = rs.getString("商品名");
				recdata[2] = rs.getString("カテゴリ名");
				recdata[3] = rs.getString("仕入単価");
				recdata[4] = rs.getString("販売単価");
				recdata[5] = rs.getString("危険在庫数");
				syohin_list.add(recdata);//配列をArrayListに詰める
			}
			rs.close();
			stmt.close();
		} catch (Exception objEx) {
			//コンソールに「接続エラー内容」を表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
		return syohin_list;
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
}
