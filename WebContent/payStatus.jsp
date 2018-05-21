<%@page import="java.text.DecimalFormat"%>
<%@page import="bean.OrderBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支払状況</title>
<script language="javascript">

function nyukin(oid){
	var form = document.getElementById("Nyukin");
	var dt   = document.getElementById("hid_date");
	var nyukin = document.getElementById("o_id_nyukin");
	var setDate = document.getElementById("pay_date" + oid);
	nyukin.value=oid

	if (!isDate(setDate.value,"/")){
		if (!isDate(setDate.value,"-")){
			alert("日付エラー");
			return false;
		}
	}
	dt.value = setDate.value;
	form.submit();

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

<% ArrayList<OrderBean> order_payList = (ArrayList<OrderBean>)session.getAttribute("order_payList");
%>

<form action="Menu" method="post">
<input type="submit" value="メニューに戻る">
</form>

<center>

<h1>支払状況</h1>

<table border="1">
	<tr>
		<td>伝票ID</td>
		<td>仕入先名</td>
		<td>支払金額</td>
		<td>入金日</td>
		<td>支払</td>
		<td>詳細</td>
	</tr>
<%
	for(OrderBean order : order_payList){
%>
	<tr>
		<td><%= order.getO_id() %></td>
		<td><%= order.getSiire_name() %></td>
		<td><%=  order.getKingaku() %></td>
		<td>
		<input type="text" name="pay_date_tmp" id="pay_date<%= order.getO_id() %>">
		</td>
		<td>
		<form action="PayFinish" method="post">
<%-- 		<input type="hidden" name="o_id" value="<%= order.getO_id() %>"> --%>
		<input type="button" value="入金" onclick="nyukin(<%= order.getO_id() %>);">
		</form>
		</td>
		<td>
		<form action="PayDetail" method="post">
		<input type="hidden" name="o_id" value="<%= order.getO_id() %>">
		<input type="submit" value="詳細">
		<input type="hidden" name="o_id" value="<%= order.getO_id() %>>">
		</form>
		</td>
	</tr>
<%
	}
%>
</table>

</center>

<form id="Nyukin" method="POST" action="PayFinish">
	<input type="hidden" name="pay_date" id="hid_date">
	<input type="hidden" name="o_id" id="o_id_nyukin">
</form>

</body>
</html>