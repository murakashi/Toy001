<%@page import="bean.SyouhinBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>発注エラー</title>
</head>
<body>

<% String[] order_check = (String[])session.getAttribute("order_check"); %>

エラー

<form action="OrderError" method="post">
<input type="hidden" name="order_check" value="<%= order_check %>">
<input type="submit" value="OK">
</form>

</center>
</body>
</html>