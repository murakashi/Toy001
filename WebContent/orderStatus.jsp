<%@page import="bean.OrderBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>発注状況</title>
</head>
<body>

<% ArrayList<OrderBean> order_list = (ArrayList<OrderBean>)session.getAttribute("order"); %>

<form action="Menu" method="post">
<input type="submit" value="メニューに戻る">
</form>

<center>

<h1>発注状況</h1>

<table border="1">
	<tr>
		<td>伝票ID</td>
		<td>仕入先名</td>
		<td>発注日</td>
		<td>詳細</td>
		<td>入庫</td>
	</tr>
<%
	for(OrderBean order : order_list){
%>
	<tr>
		<td><%= order.getO_id() %></td>
		<td><%= order.getSiire_name() %></td>
		<td><%= order.getO_date() %></td>
		<td>
		<form action="OrderDetail" method="post">
		<input type="submit" value="詳細">
		<input type="hidden" name="o_id" value="<%= order.getO_id() %>">
		</form>
		</td>
		<td>
		<form action="OrderUpdate" method="post">
		<input type="submit" value="入庫">
		<input type="hidden" name="o_id" value="<%= order.getO_id() %>">
		</form>
		</td>
	</tr>
<%
	}
%>
</table>

</center>


</body>
</html>