<%@page import="bean.CategoryBean"%>
<%@page import="bean.SyouhinBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
<title>発注</title>
</head>
<body>

<script>
$(function() {
  var page = 0;
  function draw() {
    $('#page').html(page + 1);
    $('tr').hide();
    $('tr:first,tr:gt(' + page * 10 + '):lt(10)').show();
  }
  $('#prev').click(function() {
    if (page > 0) {
      page--;
      draw();
    }
  });
  $('#next').click(function() {
    if (page < ($('tr').size() - 1) / 10 - 1) {
      page++;
      draw();
    }
  });
  draw();
});
</script>

<% ArrayList<SyouhinBean> syohin = (ArrayList<SyouhinBean>)session.getAttribute("syohin");
   ArrayList<CategoryBean> category = (ArrayList<CategoryBean>)session.getAttribute("category");
%>

<form action="Menu" method="post">
<input type="submit" value="メニューに戻る">
</form>

<center>

<h1>発注</h1>

<form action="OrderSearch" method="post">
商品名<input type="text" name="s_name">
カテゴリ<select name="category">
			<option value="未選択">未選択</option>
			<% for(int i=0;i<category.size();i++){ %>
			<option value="<%= category.get(i).getCategoryid() %>"><%= category.get(i).getC_name() %></option>
			<% } %>
		</select>
<input type="checkbox" name="dflg" value="denger">安全在庫数以下のもの
<input type="submit" name="bname" value="検索">
</form>

<br>

<span id="prev">前へ</span>
<span id="page"></span>
<span id="next">次へ</span>

<form action="OrderCount" method="post">

<table border="1">
	<tr>
		<td>チェック</td>
		<td>商品ID</td>
		<td>商品名</td>
		<td>カテゴリ名</td>
		<td>仕入基準単価</td>
		<td>販売単価</td>
		<td>安全在庫数</td>
		</tr>
<%
	for(SyouhinBean syohinBean : syohin){
%>
	<tr>
		<td><input type="checkbox" name="order_check" value="<%= syohinBean.getS_id() %>"></td>
		<td><%= syohinBean.getS_id() %></td>
		<td><%= syohinBean.getS_name() %></td>
		<td><%= syohinBean.getC_id() %></td>
		<td><%= syohinBean.getBaseprice() %></td>
		<td><%= syohinBean.getHtanka() %></td>
		<td><%= syohinBean.getSafezaiko() %></td>
	</tr>
<%
	}
%>
</table>

<br>
<input type="submit" value="発注">

</form>

</center>

</body>
</html>