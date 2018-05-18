package bean;

import java.util.Date;

public class OrderBean {
	//	伝票ID
	private int o_id;
	//	商品ID
	private int s_id;
	//  仕入先ID
	private String siire_id;
	//	発注数
	private int o_count;
	//	仕入基準価格
	private int baseprice;
	//	発注日
	private Date o_date;
	//	入庫フラグ
	private String nyukoflg;

	//	支払金額
	private int kingaku;

	/**********追加してみた↓********************/
	private String s_name;

	private String siire_name;

	/**********↑追加してみた********************/

	//	伝票ID
	public int getO_id() {
		return o_id;
	}


	public void setO_id(int o_id) {
		this.o_id = o_id;
	}

	//	商品ID
	public int getS_id() {
		return s_id;
	}

	public void setS_id(int s_id) {
		this.s_id = s_id;
	}

	//	発注数
	public int getO_count() {
		return o_count;
	}

	public void setO_count(int o_count) {
		this.o_count = o_count;
	}

	//	仕入基準価格
	public int getBaseprice() {
		return baseprice;
	}

	public void setBaseprice(int baseprice) {
		this.baseprice = baseprice;
	}

	//	発注日
	public Date getO_date() {
		return o_date;
	}

	public void setO_date(Date o_date) {
		this.o_date = o_date;
	}

	//	入庫フラグ
	public String getNyukoflg() {
		return nyukoflg;
	}

	public void setNyukoflg(String nyukoflg) {
		this.nyukoflg = nyukoflg;
	}

	//仕入先ID
	public String getSiire_id() {
		return siire_id;
	}

	public void setSiire_id(String siire_id) {
		this.siire_id = siire_id;
	}

	//商品名
	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	//仕入先名
	public String getSiire_name() {
		return siire_name;
	}


	public void setSiire_name(String siire_name) {
		this.siire_name = siire_name;
	}

	//支払金額
	public int getKingaku() {
		return kingaku;
	}


	public void setKingaku(int kingaku) {
		this.kingaku = kingaku;
	}

}
