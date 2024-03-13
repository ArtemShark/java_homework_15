package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarnMessageProcessor {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5436/homework-db";
        String user = "sa";
        String password = "admin";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            while (true) {
                String selectSQL = "SELECT id, message FROM notice WHERE type = 'WARN' AND processed = false";
                try (PreparedStatement pstmtSelect = conn.prepareStatement(selectSQL)) {
                    ResultSet rs = pstmtSelect.executeQuery();

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String message = rs.getString("message");

                        System.out.println(message);

                        String updateSQL = "UPDATE notice SET processed = true WHERE id = ?";
                        try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL)) {
                            pstmtUpdate.setInt(1, id);
                            pstmtUpdate.executeUpdate();
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
