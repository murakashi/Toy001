<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<% /**********未ログイン時のURL直接入力によるアクセス対策**********************/
   String login_flag = (String)session.getAttribute("login_flag");

   if(!(login_flag.equals("NG"))){//login_flagがNGでは無い場合
	   RequestDispatcher rd = request.getRequestDispatcher("Login");//ログインページに遷移する
	   rd.forward(request, response);
	   return;//処理を終了する
   }
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログインエラー</title>
</head>
<body>

<center>

<h1>ログインエラー</h1>

<form action="LoginCheck" method="post">
<p>ユーザID　<input type="text" name="u_id"></p>
<p>パスワード<input type="password" name="pass"></p>
<input type="submit" value="ログイン">
</form>

</center>

</body>
</html>