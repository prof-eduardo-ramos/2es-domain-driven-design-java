package br.fiap.esp.ddd.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ExemploPreparedStatement {
    public static void main(String[] args) throws SQLException {
        String jdbcURL = "jdbc:h2:mem:test";
        Connection connection = DriverManager.getConnection(jdbcURL);
        System.out.println("Connected to H2 in-memory database.");

        String sql = "CREATE TABLE students (ID int primary key, name varchar(50))";
        Statement statement = connection.createStatement();
        statement.execute(sql);
        System.out.println("Created table students.");

        sql = "INSERT INTO students (ID, name) VALUES (?, ?)";
        
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, 1);
        pstmt.setString(2, "Nam Ha Minh");

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Inserted a new row.");
        }
        connection.close();
    }

}