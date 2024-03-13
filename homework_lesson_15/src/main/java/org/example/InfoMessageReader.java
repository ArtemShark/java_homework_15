package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoMessageReader {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5436/homework-db";
        String user = "sa";
        String password = "admin";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            while (true) {
                String selectSQL = "SELECT id, message FROM notice WHERE type = 'INFO' AND processed = false";
                try (PreparedStatement pstmtSelect = conn.prepareStatement(selectSQL)) {
                    ResultSet rs = pstmtSelect.executeQuery();

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String message = rs.getString("message");

                        System.out.println(message);

                        String deleteSQL = "DELETE FROM notice WHERE id = ?";
                        try (PreparedStatement pstmtDelete = conn.prepareStatement(deleteSQL)) {
                            pstmtDelete.setInt(1, id);
                            pstmtDelete.executeUpdate();
                        }
                    }
                }

                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
