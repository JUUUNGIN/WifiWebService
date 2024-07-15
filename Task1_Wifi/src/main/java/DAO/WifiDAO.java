package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.WifiInfo;
import util.DataBaseUtil;

public class WifiDAO {
	
	public List<WifiInfo> getWifiInfo() {
        List<WifiInfo> wifiList = new ArrayList<>();

        try {
            Connection conn = DataBaseUtil.getConnection();
            String sql = "SELECT * FROM wifi_info";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WifiInfo wifi = new WifiInfo();
                wifi.setX_SWIFI_MGR_NO(rs.getString("manager_number"));
                wifi.setX_SWIFI_WRDOFC(rs.getString("district"));
                wifi.setX_SWIFI_MAIN_NM(rs.getString("wifi_name"));
                wifi.setX_SWIFI_ADRES1(rs.getString("road_address"));
                wifi.setX_SWIFI_ADRES2(rs.getString("detailed_address"));
                wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("floor"));
                wifi.setX_SWIFI_INSTL_TY(rs.getString("installation_type"));
                wifi.setX_SWIFI_INSTL_MBY(rs.getString("provider"));
                wifi.setX_SWIFI_SVC_SE(rs.getString("service_type"));
                wifi.setX_SWIFI_CMCWR(rs.getString("network_type"));
                wifi.setX_SWIFI_CNSTC_YEAR(rs.getInt("installation_year"));
                wifi.setX_SWIFI_INOUT_DOOR(rs.getString("indoor_outdoor"));
                wifi.setX_SWIFI_REMARS3(rs.getString("wifi_environment"));
                wifi.setLAT(rs.getFloat("x_coordinate"));
                wifi.setLNT(rs.getFloat("y_coordinate"));
                wifi.setWORK_DTTM(rs.getString("work_date"));

                wifiList.add(wifi);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifiList;
    }
}
