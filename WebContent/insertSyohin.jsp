<%@page import="bean.CategoryBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新商品追加</title>
</head>
<body>

<%
	int s_id = (int)session.getAttribute("s_id");
	ArrayList<CategoryBean> category = (ArrayList<CategoryBean>)session.getAttribute("category");
%>

<form action="InsertSyohinCheck" method="post">
商品ID<input type="text" name="s_id">
商品名<input type="text" name="s_name">
カテゴリ<select name="category">
			<option value="未選択">未選択</option>
			<% for(int i=0;i<category.size();i++){ %>
			<option value="<%= category.get(i).getCategoryid() %>"><%= category.get(i).getC_name() %></option>
			<% } %>
		</select>
仕入基準単価<input type="text" name="siire_tanka">
販売単価<input type="text" name="h_tanka">
安全在庫数<input type="text" name="safe_zaiko">
<input type="submit" value="登録">
</form>

</body>
</html>