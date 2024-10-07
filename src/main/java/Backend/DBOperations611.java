package Backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperations611 {

    public static String listParts611() throws SQLException {
        Connection conn = Connection611.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Parts611");
        StringBuilder partsList = new StringBuilder();

        while (rs.next()) {
            partsList.append("Part No: ").append(rs.getInt("partNo611"))
                    .append(", Description: ").append(rs.getString("descrPart"))
                    .append(", Price: ").append(rs.getBigDecimal("pricePart"))
                    .append(", QOH: ").append(rs.getInt("qoh")).append("\n");
        }
        return partsList.toString();
    }

    public static String listPOs611() throws SQLException {
        Connection conn = Connection611.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM POs611");
        StringBuilder poList = new StringBuilder();

        while (rs.next()) {
            poList.append("PO No: ").append(rs.getInt("poNo611"))
                    .append(", Client ID: ").append(rs.getInt("clientCompID"))
                    .append(", Date: ").append(rs.getDate("dateOfPO"))
                    .append(", Status: ").append(rs.getString("statusPO")).append("\n");
        }
        return poList.toString();
    }

    public static String listPOinfo611(int poNumber) throws SQLException {
        Connection conn = Connection611.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM POs611 WHERE poNo611 = ?");
        stmt.setInt(1, poNumber);
        ResultSet rs = stmt.executeQuery();
        StringBuilder poInfo = new StringBuilder();

        if (rs.next()) {
            poInfo.append("PO No: ").append(rs.getInt("poNo611"))
                    .append(", Client ID: ").append(rs.getInt("clientCompID"))
                    .append(", Date: ").append(rs.getDate("dateOfPO"))
                    .append(", Status: ").append(rs.getString("statusPO")).append("\n");
        } else {
            return "No purchase order found with PO number: " + poNumber;
        }
        return poInfo.toString();
    }

    public static String submitPO611(/* PO object */) {
        // Implement submission logic here
        return "PO submission successful!";
    }
}
