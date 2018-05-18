<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>発注金額合計確認</title>
</head>
<body>

<%
	String siire_name = (String)session.getAttribute("siire_name");
	String siire_kin = (String)session.getAttribute("siire_kin");
%>

<center>

<h1>発注内容確認</h1>

<h2>以下の内容でよろしいですか？</h2>

<p>仕入先名→<%= siire_name %></p>

<p>発注合計金額<%= siire_kin %></p>

<form action="OrderFinish" method="post">
<input type="submit" value="OK">
</form>

<form action="Order" method="post">
<input type="submit" value="キャンセル">
</form>

</center>

</body>
</html>