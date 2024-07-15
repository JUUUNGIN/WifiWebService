package survlet;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
////@WebServlet("/DataServlet")
//public class DataServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String apiKey = "724e677769636a6938304d6e744d50"; // 발급받은 API 키
//        String serviceUrl = "http://openapi.seoul.go.kr:8088/" + URLEncoder.encode(apiKey, "UTF-8") + "/json/TbPublicWifiInfo/1/100/";
//
//        URL url = new URL(serviceUrl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
//        }
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//
//        // 서블릿에서 받은 데이터를 JSP로 전달하기 위해 request에 데이터를 저장
//        request.setAttribute("apiData", sb.toString());
//
//        // JSP 페이지로 포워딩
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
//    }
//}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import util.DataBaseUtil;

//@WebServlet("/DataServlet")
public class DataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String apiKey = "724e677769636a6938304d6e744d50"; // 발급받은 API 키
        String serviceUrl = "http://openapi.seoul.go.kr:8088/" + URLEncoder.encode("724e677769636a6938304d6e744d50", "UTF-8")
                + "/json/TbPublicWifiInfo/1/1000/";

        URL url = new URL(serviceUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // 서블릿에서 받은 데이터를 JSP로 전달하기 위해 request에 데이터를 저장
        request.setAttribute("apiData", sb.toString());

        // MySQL 데이터베이스에 데이터 삽입
        insertDataToDatabase(sb.toString());

        // JSP 페이지로 포워딩
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void insertDataToDatabase(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject wifiInfo = jsonObject.getJSONObject("TbPublicWifiInfo");
            JSONArray dataArray = wifiInfo.getJSONArray("row");

            // MySQL 데이터베이스 연결
            Connection conn = DataBaseUtil.getConnection();

            // 데이터 삽입을 위한 SQL 쿼리
            String sql = "INSERT INTO wifi_info (manager_number, district, wifi_name, road_address, "
                    + "detailed_address, floor, installation_type, provider, service_type, network_type, installation_year, "
                    + "indoor_outdoor, wifi_environment, x_coordinate, y_coordinate, work_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            // 각 데이터 항목을 PreparedStatement에 설정
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject data = dataArray.getJSONObject(i);

                pstmt.setString(1, data.getString("X_SWIFI_MGR_NO"));
                pstmt.setString(2, data.getString("X_SWIFI_WRDOFC"));
                pstmt.setString(3, data.getString("X_SWIFI_MAIN_NM"));
                pstmt.setString(4, data.getString("X_SWIFI_ADRES1"));
                pstmt.setString(5, data.getString("X_SWIFI_ADRES2"));
                pstmt.setString(6, data.getString("X_SWIFI_INSTL_FLOOR"));
                pstmt.setString(7, data.getString("X_SWIFI_INSTL_TY"));
                pstmt.setString(8, data.getString("X_SWIFI_INSTL_MBY"));
                pstmt.setString(9, data.getString("X_SWIFI_SVC_SE"));
                pstmt.setString(10, data.getString("X_SWIFI_CMCWR"));
                pstmt.setInt(11, data.getInt("X_SWIFI_CNSTC_YEAR"));
                pstmt.setString(12, data.getString("X_SWIFI_INOUT_DOOR"));
                pstmt.setString(13, data.getString("X_SWIFI_REMARS3"));
                pstmt.setFloat(14, (float) data.getDouble("LAT"));
                pstmt.setFloat(15, (float) data.getDouble("LNT"));
                pstmt.setString(16, data.getString("WORK_DTTM"));

                pstmt.executeUpdate();
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}