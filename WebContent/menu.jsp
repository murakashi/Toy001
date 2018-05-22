<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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
<input type="submit" value="発注">
</form>
</p>

<p>
<form action="OrderStatus" method="post">
<input type="submit" value="発注状況">
</form>
</p>

<p>
<form action="PayStatus" method="post">
<input type="submit" value="支払状況">
</form>
</p>

<p>
<form action="InsertSyohin" method="post">
<input type="submit" value="商品追加">
</form>
</p>

</center>

</body>
</html>