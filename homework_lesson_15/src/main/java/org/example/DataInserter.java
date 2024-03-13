package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Random;

public class DataInserter {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5436/homework-db";
        String user = "sa";
        String password = "admin";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO notice (message, type, processed) VALUES (?, ?, ?)";

            while (true) {

                PreparedStatement pstmt = conn.prepareStatement(sql);


                boolean isInfoType = new Random().nextBoolean();
                String message = isInfoType ?
                        "Новое сообщение от " + LocalDateTime.now() :
                        "Произошла ошибка в " + LocalDateTime.now();
                String type = isInfoType ? "INFO" : "WARN";


                pstmt.setString(1, message);
                pstmt.setString(2, type);
                pstmt.setBoolean(3, false);

                pstmt.executeUpdate();

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

