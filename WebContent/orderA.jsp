<%@page import="bean.SyouhinBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>発注</title>
</head>
<body>

<% ArrayList<SyouhinBean> syohin = (ArrayList<SyouhinBean>)session.getAttribute("syohin"); %>

<form action="Menu" method="post">
<input type="submit" value="メニューに戻る">
</form>

<center>

<h1>発注</h1>

<form action="OrderCount" method="post">

<table border="1">
	<tr>
		<td>チェック</td>
		<td>商品ID</td>
		<td>商品名</td>
		<td>カテゴリ名</td>
		<td>仕入基準単価</td>
		<td>販売単価</td>
		<td>安全在庫数</td>
		</tr>
<%
	for(SyouhinBean syohinBean : syohin){
%>
	<tr>
		<td><input type="checkbox" name="order_check" value="<%= syohinBean.getS_id() %>"></td>
		<td><%= syohinBean.getS_id() %></td>
		<td><%= syohinBean.getS_name() %></td>
		<td><%= syohinBean.getC_id() %></td>
		<td><%= syohinBean.getBaseprice() %></td>
		<td><%= syohinBean.getHtanka() %></td>
		<td><%= syohinBean.getSafezaiko() %></td>
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