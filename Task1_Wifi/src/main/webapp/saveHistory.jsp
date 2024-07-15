<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="DAO.HistoryDAO" %>
<%
    String latitude = request.getParameter("latitude");
    String longitude = request.getParameter("longitude");

    if (latitude != null && longitude != null) {
        try {
            float lat = Float.parseFloat(latitude);
            float lng = Float.parseFloat(longitude);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            HistoryDAO historyDAO = new HistoryDAO();
            historyDAO.saveHistory(lat, lng, timestamp);

            response.sendRedirect("history.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.println("Invalid coordinates format.");
        }
    } else {
        out.println("Coordinates are required.");
    }
%>