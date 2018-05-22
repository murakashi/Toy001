<%@page import="bean.OrderBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支払詳細</title>
<script language="javascript">

function nyukin(){
	var setDate = document.getElementById("pay_date");

	if (!isDate(setDate.value,"/")){
		if (!isDate(setDate.value,"-")){
			alert("日付エラー");
			return false;
		}
	}

	document.frmMain.submit();

}

function isDate (str, delim) {
	  var arr = str.split(delim);
	  if (arr.length !== 3) return false;
	  const date = new Date(arr[0], arr[1] - 1, arr[2]);
	  if (arr[0] !== String(date.getFullYear()) || arr[1] !== ('0' + (date.getMonth() + 1)).slice(-2) || arr[2] !== ('0' + date.getDate()).slice(-2)) {
	    return false;
	  } else {
	    return true;
	  }
	};

</script>
</head>
<body>

<% ArrayList<OrderBean> pay_list = (ArrayList<OrderBean>)session.getAttribute("pay_list");
%>

<form action="PayStatus" method="post">
<input type="submit" value="戻る">
</form>

<center>

<h1>発注伝票詳細</h1>

<h2>伝票ID→<%= pay_list.get(0).getO_id() %></h2>
<h2>仕入先名→<%= pay_list.get(0).getSiire_name() %></h2>
<h2>発注日→<%= pay_list.get(0).getO_date() %></h2>

<form action="PayFinish" method="post" name="frmMain">
<table border="1">
	<tr>
		<td>商品ID</td>
		<td>商品名</td>
		<td>発注数</td>
		<td>仕入単価</td>
		<td>金額</td>
	</tr>
<%
	for(OrderBean order : pay_list){
%>
	<tr>
		<td><%= order.getS_id() %></td>
		<td><%= order.getS_name() %><input type="hidden" name="s_id" value="<%= order.getS_id() %>"></td>
		<td><%= order.getO_count() %><input type="hidden" name="count" value="<%= order.getO_count() %>"></td>
		<td><%= order.getBaseprice() %><input type="hidden" name="price" value="<%= order.getBaseprice() %>"></td>
		<td><%= order.getKingaku() %><input type="hidden" name="kingaku" value="<%= order.getKingaku() %>"></td>
	</tr>
<%
	}
%>
</table>

<p>支払合計金額→<%= session.getAttribute("sum") %></p>

<br>
入金日<input type="text" name="pay_date" id="pay_date" >
<input type="hidden" name="o_id" value="<%= pay_list.get(0).getO_id() %>">

<input type="button" value="入金" onclick="nyukin();">
</form>


</center>

</body>
</html>