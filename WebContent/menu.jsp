<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<% /**********未ログイン時のURL直接入力によるアクセス対策**********************/
   String login_flag = (String)session.getAttribute("login_flag");

   if(login_flag == null){//login_flagがOKでは無い場合
	   RequestDispatcher rd = request.getRequestDispatcher("Login");//ログインページに遷移する
	   rd.forward(request, response);
	   return;
   }
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>メニュー</title>
</head>
<body>

<center>

<h1>メニュー</h1>

<p>
<form action="Syohin" method="post">
<input type="submit" value="商品情報">
</form>
</p>

<p>
<form action="Zaiko" method="post">
<input type="submit" value="在庫情報">
</form>
</p>

<p>
<form action="Order" method="post">
<input type="submit" value="発注・仕入れ">
</form>
</p>

<p>
<form action="Sales" method="post">
<input type="submit" value="売上情報">
</form>
</p>

<p>
<form action="Order" method="post">
<input type="submit" value="発注">
</form>

</center>

</body>
</html>