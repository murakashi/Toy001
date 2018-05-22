<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新商品追加内容確認</title>
</head>
<body>



以下の内容の商品を追加します

商品ID→<%= session.getAttribute("s_id") %>
商品名→<%= session.getAttribute("s_name") %>
カテゴリ→<%= session.getAttribute("category") %>
仕入基準単価→<%= session.getAttribute("siire_tanka") %>
販売単価→<%= session.getAttribute("h_tanka") %>
安全在庫数→<%= session.getAttribute("safe_zaiko") %>

<form action="InsertSyohinFinish" method="post">
<input type="submit" value="登録">
</form>

<form action="InsertSyohin" method="post">
<input type="submit" value="キャンセル">
</form>

</body>
</html>