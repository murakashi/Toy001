<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品一覧</title>
</head>
<body>

<% ArrayList<String[]> syohin_list = (ArrayList<String[]>)session.getAttribute("syohin_list"); %>

<center>

<h1>商品データ</h1>

<p>
<form action="SyohinSearch" method="post">
商品名<input type="text" name="s_name">
<input type="submit" value="検索">
</form>
</p>

<table border="1">
	<tr>
		<td>商品ID</td>
		<td>商品名</td>
		<td>カテゴリ名</td>
		<td>仕入単価</td>
		<td>販売単価</td>
		<td>危険在庫数</td>
		<td>修正</td>
		<td>削除</td>
	</tr>
<%
	for(String[] syohin_arr : syohin_list){
%>
	<tr>
<%
		for(String s : syohin_arr){

%>
			<td><%= s %></td>
<%
		}
%>
		<td>
		<form action="SyohinFix" method="post">
		<input type="submit" value="修正">
		<input type="hidden" name="s_id" value="<%= syohin_arr[0]%>">
		</form>
		</td>

		<td>
		<form action="SyohinDelete" method="post">
		<input type="submit" value="削除">
		<input type="hidden" name="s_id" value="<%= syohin_arr[0]%>">
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