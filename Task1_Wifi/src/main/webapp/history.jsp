<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="DAO.HistoryDAO" %>
<%@ page import="DTO.History" %>

<!DOCTYPE html>
<html>
<head>
    <style>
        .navbar {
            overflow: hidden;
        }
        .navbar a {
            float: left;
            display: block;
            color: #000000;
            text-align: center;
            padding: 3px;
            text-decoration: none;
        }
        .container {
            padding: 1px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            border: 1px solid;
            font-size: 13px;
        }
        th, td {
            padding: 5px;
            border: 1px solid;
        }
        tr:nth-child(even) { background-color: hsla(120, 60%, 70%, 0.1) }
        th {
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<p style="font-size:30px"><b>위치 히스토리 목록</b></p>
<div class="navbar">
    <a href="index.jsp">홈 |</a>
    <a href="history.jsp">위치 히스토리 목록 |</a>
    <a href="api.jsp">OPEN API 와이파이 정보 가져오기</a>
</div>
<br>
<div class="container">
    <table>
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일자</th>
            <th>비고</th>
        </tr>
        <%
            HistoryDAO historyDAO = new HistoryDAO();
            List<History> historyList = historyDAO.getHistory();

            for (History history : historyList) {
        %>
                <tr>
                    <td><%= history.getId() %></td>
                    <td><%= history.getLatitude() %></td>
                    <td><%= history.getLongitude() %></td>
                    <td><%= history.getTimestamp() %></td>
                    <td><button onclick="this.parentElement.parentElement.style.display='none'">삭제</button></td>
                </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>