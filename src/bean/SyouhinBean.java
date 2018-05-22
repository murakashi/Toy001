package bean;

public class SyouhinBean {
//	商品ID
	private int s_id;
//	商品名
	private String s_name;
//	カテゴリID
	private String c_id;
//  仕入先ID
	private String siire_id;
//	仕入基準価格
	private int baseprice;
//	販売単価
	private int htanka;
//	安全在庫数
	private int safezaiko;
//	削除フラグ
	private String delflg;
	//在庫残量
		private int zaiko;



	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getC_id() {
		return c_id;
	}
	public void setC_id(String c_id) {
		this.c_id = c_id;
	}
	public int getBaseprice() {
		return baseprice;
	}
	public void setBaseprice(int baseprice) {
		this.baseprice = baseprice;
	}
	public int getHtanka() {
		return htanka;
	}
	public void setHtanka(int htanka) {
		this.htanka = htanka;
	}
	public int getSafezaiko() {
		return safezaiko;
	}
	public void setSafezaiko(int safezaiko) {
		this.safezaiko = safezaiko;
	}
	public String getDelflg() {
		return delflg;
	}
	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}
	public String getSiire_id() {
		return siire_id;
	}
	public void setSiire_id(String siire_id) {
		this.siire_id = siire_id;
	}



	public int getZaiko() {
		return zaiko;
	}
	public void setZaiko(int zaiko) {
		this.zaiko = zaiko;
	}


}
