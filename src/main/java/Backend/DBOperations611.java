package Backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Frontend.PurchaseOrderLine;

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

    public static String submitPO611(int clientID, List<PurchaseOrderLine> poLines) throws SQLException {
        Connection conn = null;
        PreparedStatement psPO = null;
        PreparedStatement psLine = null;
        ResultSet rs = null;

        try {
            conn = Connection611.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Validate Client ID
            psPO = conn.prepareStatement("SELECT clientId611 FROM Clients611 WHERE clientId611 = ?");
            psPO.setInt(1, clientID);
            rs = psPO.executeQuery();
            if (!rs.next()) {
                conn.rollback();
                return "Error: Invalid client ID";
            }

            // Insert Purchase Order into POs611
            String insertPOQuery = "INSERT INTO POs611 (clientCompID, dateOfPO, statusPO) VALUES (?, NOW(), 'Pending')";
            psPO = conn.prepareStatement(insertPOQuery, Statement.RETURN_GENERATED_KEYS);
            psPO.setInt(1, clientID);
            psPO.executeUpdate();

            // Get the generated PO number
            rs = psPO.getGeneratedKeys();
            if (rs.next()) {
                int poNumber = rs.getInt(1);  // Retrieve the generated poNo611


                // Validate each line and insert into Lines611
                for (PurchaseOrderLine line : poLines) {
                    // Check stock for the part
                    psLine = conn.prepareStatement("SELECT qoh FROM Parts611 WHERE partNo611 = ?");
                    psLine.setInt(1, line.getPartNo());
                    rs = psLine.executeQuery();

                    if (!rs.next()) {
                        conn.rollback();
                        return "Error: Part number " + line.getPartNo() + " does not exist.";
                    }
                    int qoh = rs.getInt("qoh");
                    if (qoh < line.getQtyOrdered()) {
                        conn.rollback();
                        return "Error: Not enough stock for part number " + line.getPartNo();
                    }

                    // Insert the line into Lines611 (Trigger will update moneyOwed)
                    String insertLineQuery = "INSERT INTO Lines611 (poNo611, partNo, qtyOrdered, priceOrdered) VALUES (?, ?, ?, ?)";

                    psLine = conn.prepareStatement(insertLineQuery);
                    psLine.setInt(1, poNumber);
                    psLine.setInt(2, line.getPartNo());
                    psLine.setInt(3, line.getQtyOrdered());
                    psLine.setDouble(4, line.getPriceOrdered());
                    psLine.executeUpdate();

                    // Update stock in Parts611
                    String updateStockQuery = "UPDATE Parts611 SET qoh = qoh - ? WHERE partNo611 = ?";
                    psLine = conn.prepareStatement(updateStockQuery);
                    psLine.setInt(1, line.getQtyOrdered());
                    psLine.setInt(2, line.getPartNo());
                    psLine.executeUpdate();
                }

                // Commit transaction and return success message with PO number
                conn.commit();
                return "Purchase Order submitted successfully! PO Number: " + poNumber;
            } else {
                conn.rollback();
                return "Error: Failed to create purchase order.";
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Error submitting PO: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (psPO != null) psPO.close();
            if (psLine != null) psLine.close();
            if (conn != null) conn.close();
        }
    }
}
