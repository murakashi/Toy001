<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"type="text/css"href="styles.css">
<title>発注エラー</title>
</head>
<body>
<center>
<%--<% String[] order_check = (String[])session.getAttribute("order_check"); --%>
<p class="example1">仕入先を選択してください。</p>

<form action="OrderError" method="post">
<%--<input type="hidden" name="order_check" value="<%= order_check %>">--%>
<input class="button2" type="submit" value="戻る">
</form>
</center>
</body>
</html>