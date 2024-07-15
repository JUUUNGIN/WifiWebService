<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DTO.WifiInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.WifiDAO" %>


<!DOCTYPE html>
<html>
<head>
<style>
	.navbar {
        text-align: center;
    }
    .navbar a {
        display: inline-block;
        text-align: center;
        padding: 10px; 
        text-decoration: none;
        color: black; 
    }
</style>
<meta charset="UTF-8">

</head>
<body>

 <%
    WifiDAO wifiDAO = new WifiDAO();
    List<WifiInfo> wifiList = wifiDAO.getWifiInfo();
    int count = wifiList.size();
%>

<p style="text-align: center; font-size: 20px;"><b><%= count %> 개의 WIFI 정보를 정상적으로 저장하였습니다.</b></p>

<div class="navbar">
	
    <a href="index.jsp">홈으로 가기</a>
    </div>

</body>
</html>