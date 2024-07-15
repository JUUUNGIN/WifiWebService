<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="DAO.WifiDAO" %>
<%@ page import="DTO.WifiInfo" %>
<%@ page import="DAO.HistoryDAO" %>
<%@ page import="java.sql.Timestamp" %>


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
    <meta charset="UTF-8">
    <script>
        function getLocation() {
            document.getElementById("latitude").value = "";
            document.getElementById("longitude").value = "";
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        }
        function showPosition(position) {
            document.getElementById("latitude").value = position.coords.latitude;
            document.getElementById("longitude").value = position.coords.longitude;
        }
    </script>
</head>
<body>
<p style="font-size:30px"><b>와이파이 정보 구하기</b></p>
<div class="navbar">
    <a href="index.jsp">홈 |</a>
    <a href="history.jsp">위치 히스토리 목록 |</a>
    <a href="api.jsp">OPEN API 와이파이 정보 가져오기</a>
</div>
<br>
<div class="container">
    <form action="index.jsp" method="GET">
        <label for="latitude">LAT:</label>
        <input type="text" id="latitude" name="latitude" required>
        <label for="longitude">LNT:</label>
        <input type="text" id="longitude" name="longitude" required>
        <button type="button" onclick="getLocation()">내 위치 가져오기</button>
        <button type="submit">근처 WIFI 정보 보기</button>
    </form>
</div>
<br>
<br>
<% if (request.getParameter("latitude") == null || request.getParameter("longitude") == null) { %>
<div class="container">
    <table>
        <tr>
            <th>거리<br>(km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>wifi접속환경</th>
            <th>Y좌표</th>
            <th>X좌표</th>
            <th>작업일자</th>
        </tr>
        <tr>
            <td colspan="17" style="text-align: center;">위치 정보를 입력한 후에 조회해 주세요.</td>
        </tr>
    </table>
</div>
<% } else { %>
<%
   
    
 	WifiDAO wifiDAO = new WifiDAO();
    List<WifiInfo> wifiList = wifiDAO.getWifiInfo();
    
/*     HistoryDAO historyDAO = new HistoryDAO();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    historyDAO.saveHistory("latitude", "longitude", timestamp); */
    
    if (request.getParameter("latitude") != null && request.getParameter("longitude") != null) {
        try {
            float latitude = Float.parseFloat(request.getParameter("latitude"));
            float longitude = Float.parseFloat(request.getParameter("longitude"));

            // Save the history
            HistoryDAO historyDAO = new HistoryDAO();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
            historyDAO.saveHistory(latitude, longitude, timestamp);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    
    
    }  
%>
<div>
    <table>
        <tr>
            <th>거리</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>wifi접속환경</th>
            <th>Y좌표</th>
            <th>X좌표</th>
            <th>작업일자</th>
        </tr>
        <%
        	int count = 0;
            for (WifiInfo wifi : wifiList) {
            	if(count >= 20){
            		break;
            	}
            	count++;
        %>
                <tr>
                    <td>계산중</td>
                    <td><%= wifi.getX_SWIFI_MGR_NO() %></td>
                    <td><%= wifi.getX_SWIFI_WRDOFC() %></td>
                    <td><%= wifi.getX_SWIFI_MAIN_NM() %></td>
                    <td><%= wifi.getX_SWIFI_ADRES1() %></td>
                    <td><%= wifi.getX_SWIFI_ADRES2() %></td>
                    <td><%= wifi.getX_SWIFI_INSTL_FLOOR() %></td>
                    <td><%= wifi.getX_SWIFI_INSTL_TY() %></td>
                    <td><%= wifi.getX_SWIFI_INSTL_MBY() %></td>
                    <td><%= wifi.getX_SWIFI_SVC_SE() %></td>
                    <td><%= wifi.getX_SWIFI_CMCWR() %></td>
                    <td><%= wifi.getX_SWIFI_CNSTC_YEAR() %></td>
                    <td><%= wifi.getX_SWIFI_INOUT_DOOR() %></td>
                    <td><%= wifi.getX_SWIFI_REMARS3() %></td>
                    <td><%= wifi.getLAT() %></td>
                    <td><%= wifi.getLNT() %></td>
                    <td><%= wifi.getWORK_DTTM() %></td>
                </tr>
                <%
            }
        %>
    </table>
    <%
            }
        %>
</div>

</body>
</html>
