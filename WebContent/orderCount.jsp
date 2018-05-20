<%@page import="bean.SiireBean"%>
<%@page import="bean.SyouhinBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>発注数量入力</title>
</head>
<body>

<% ArrayList<SyouhinBean> syohin = (ArrayList<SyouhinBean>)session.getAttribute("syohin");
   ArrayList<SiireBean> siire_list = (ArrayList<SiireBean>)session.getAttribute("siire_list");
%>

<form action="Order" method="post">
<input type="submit" value="戻る">
</form>

<center>

<h1>仕入先と発注数を入力してください</h1>

<form action="OrderSum" method="post">

仕入先IDを入力してください
<select name="siire_id">
	<option value="未選択">未選択</option>
<%
	for(SiireBean siire : siire_list){
%>
	<option value="<%= siire.getSiire_id() %>"><%= siire.getSiire_name() %></option>
<%
	}
%>
</select>

<br>

<table border="1">
	<tr>
		<td>商品ID</td>
		<td>商品名</td>
		<td>カテゴリ名</td>
		<td>仕入基準単価</td>
		<td>販売単価</td>
		<td>安全在庫数</td>
		<td>数量</td>
		</tr>
<%
	for(SyouhinBean syohinBean : syohin){
%>
	<tr>
		<td><%= syohinBean.getS_id() %></td>
		<td><%= syohinBean.getS_name() %></td>
		<td><%= syohinBean.getC_id() %></td>
		<td><%= syohinBean.getBaseprice() %></td>
		<td><%= syohinBean.getHtanka() %></td>
		<td><%= syohinBean.getSafezaiko() %></td>
		<td>
		<input type="text" name="count">
		<input type="hidden" name="s_id" value="<%= syohinBean.getS_id() %>">
		<input type="hidden" name="s_basePrice" value="<%= syohinBean.getBaseprice() %>">
		</td>
	</tr>
<%
	}
%>
</table>

<br>
<input type="submit" value="発注">

</form>

</center>

</body>
</html>