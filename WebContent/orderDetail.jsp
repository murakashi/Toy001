<%@page import="java.util.ArrayList"%>
<%@page import="bean.OrderBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>発注伝票詳細</title>
</head>
<body>

<% ArrayList<OrderBean> order_list = (ArrayList<OrderBean>)session.getAttribute("order_list"); %>

<form action="OrderStatus" method="post">
<input type="submit" value="戻る">
</form>

<center>

<h1>発注伝票詳細</h1>

<h2>伝票ID→<%= order_list.get(0).getO_id() %></h2>
<h2>仕入先名→<%= order_list.get(0).getSiire_name() %></h2>

<form action="OrderUpdate" method="post">
<table border="1">
	<tr>
		<td>商品ID</td>
		<td>商品名</td>
		<td>発注数</td>
	</tr>
<%
	for(OrderBean order : order_list){
%>
	<tr>
		<td><%= order.getS_id() %></td>
		<td><%= order.getS_name() %><input type="hidden" name="s_id" value="<%= order.getS_id() %>"></td>
		<td><%= order.getO_count() %><input type="hidden" name="count" value="<%= order.getO_count() %>"></td>
	</tr>
<%
	}
%>
</table>
<input type="hidden" name="o_id" value="<%= order_list.get(0).getO_id() %>">
<input type="submit" value="入庫">
</form>

</center>

</body>
</html>