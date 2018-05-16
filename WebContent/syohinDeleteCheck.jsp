<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品削除確認</title>
</head>
<body>

<% String[] delete_arr = (String[])session.getAttribute("delete_arr");
%>

%>
<center>

<h1>商品削除確認</h1>

<h3>以下の商品を削除しますか？</h3>

<p>商品ID→<%= delete_arr[0] %></p>

<p>商品ID→<%= delete_arr[1] %></p>

<form action="SyohinDeleteFinish" method="post">
	<input type="submit" value="はい">
	<input type="hidden" name="s_id" value="<%= delete_arr[0] %>">
</form>

<form action="Syohin" method="post">
	<input type="submit" value="キャンセル">
</form>

</center>


</body>
</html>