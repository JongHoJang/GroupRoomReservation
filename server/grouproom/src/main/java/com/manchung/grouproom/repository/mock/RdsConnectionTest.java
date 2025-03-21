package com.manchung.grouproom.repository.mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RdsConnectionTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://manchung-grouproom-rds.crogq0mcg5m6.ap-northeast-2.rds.amazonaws.com:3306/grouproom_dev";
        String username = "admin";
        String password = "114802gud";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("✅ RDS 연결 성공!");
        } catch (SQLException e) {
            System.err.println("❌ RDS 연결 실패: " + e.getMessage());
        }
    }
}
