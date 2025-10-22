package br.fiap.esp.ddd.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ExemploResultSet {
    
    private static final String JDBC_URL = "jdbc:h2:~/data/students";

    public static void main(String[] args)  {
        prepareDatabase();

        var query = "SELECT ID, NAME FROM STUDENTS";
        try (
            PreparedStatement pstmt = createConnection(JDBC_URL).prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
        ){
            while (rs.next()) {
                Student student = new Student(rs.getInt(1), rs.getString(2));
                System.out.println(student);
            }
        } catch (SQLException e) {
            System.out.println("Error ocurred fetching students table. Error: " + e.getMessage());
        }

    }

    private static void prepareDatabase() {
        createDatabase();
        addStudents(List.of(
            new Student(null, "Walter White"),
            new Student(null, "Jesse Pinkman"),
            new Student(null, "Gus Fring")
        ));
    }

    static Connection createConnection(String jdbcURL) throws SQLException {
        System.out.println("Connecting to H2 database.");
        return DriverManager.getConnection(jdbcURL);
    }

    static Statement createStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }

    static void createDatabase() {
        if (databaseExists()) {
            return; 
        }

        String strCreateTable = "CREATE TABLE students (ID int AUTO_INCREMENT primary key, name varchar(50))";
        try (
            Connection connection = createConnection(JDBC_URL);
            Statement statement = connection.createStatement();
            ) { 
                statement.execute(strCreateTable);
                System.out.println("Created table students.");
        } catch (Exception e) {
            System.out.println("Error ocurred creating students table. Error: " + e.getMessage());
        }
    }

    static Boolean databaseExists() {
        String sqlDatabaseExists = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'STUDENTS' AND TABLE_SCHEMA = 'PUBLIC'";
        try (
                Connection connection = createConnection(JDBC_URL);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlDatabaseExists);
            ) {     
                return rs.next();
        } catch (Exception e) {
            System.out.println("Error checking if students table exists. Error: " + e.getMessage());
        }
        return false;
    }

    static Integer addStudents(List<Student> students) {
        String sql = "INSERT INTO students (name) VALUES (?)";
        
        try (var connection = createConnection(JDBC_URL)) {
            CallableStatement pstmt = connection.prepareCall(sql);
            int rows = 0;

            for (Student student : students) {
                pstmt.setString(1, student.getName());                
                rows = rows + pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Inserted a new row.");
                }
            }

            return rows;
            
        } catch (Exception e) {
            System.out.println("Error ocurred inserting new row at students table. Error: " + e.getMessage());
        }
        return null;
    }


    private static class Student {

        private Integer id;
        private String name;

        Student(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "[id: " + id + "][name: " + name + "]";
        }
    }
}