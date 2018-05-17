package bean;
import java.util.Date;

public class UriageBean {
//	売上ID
	private int uriageid;
//	商品ID
	private int s_id;
//	売上日
	private Date u_date;
//	売上数
	private int u_count;
//	販売単価
	private int htanka;
//	税込価格
	private int taxprice;
//	売上フラグ
	private String uriageflg;



	public int getUriageid() {
		return uriageid;
	}
	public void setUriageid(int uriageid) {
		this.uriageid = uriageid;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public Date getU_date() {
		return u_date;
	}
	public void setU_date(Date u_date) {
		this.u_date = u_date;
	}
	public int getU_count() {
		return u_count;
	}
	public void setU_count(int u_count) {
		this.u_count = u_count;
	}
	public int getHtanka() {
		return htanka;
	}
	public void setHtanka(int htanka) {
		this.htanka = htanka;
	}
	public int getTaxprice() {
		return taxprice;
	}
	public void setTaxprice(int taxprice) {
		this.taxprice = taxprice;
	}
	public String getUriageflg() {
		return uriageflg;
	}
	public void setUriageflg(String uriageflg) {
		this.uriageflg = uriageflg;
	}


}
