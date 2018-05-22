<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新商品追加エラー</title>
</head>
<body>

<% String[] message = (String[])session.getAttribute("message"); %>

<%
	for(int i=0;i<message.length;i++){
		if(!(message[i].equals(""))){
%>
			<%= message[i] %><br>

<%
		}
	}
%>

<form action="InsertSyohin" method="post">
<input type="submit" value="OK">
</form>

</body>
</html>