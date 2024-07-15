package org.example;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int a = 10;
        System.out.println(a);

        /*Important notes
         * import package
         * load and register
         * create connection
         * create statement
         * execute statement
         * process the result
         * close the connection
         */
        String url = "jdbc:postgresql://localhost:5000/Testing";
        String user = "postgres";
        String password = "pc";
        Connection dbcon = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        CallableStatement cstmt = null;

        try {
            // Create connection
            dbcon = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected");

            // Create statement
            // 1. Statement
            // Statement: Used for executing simple SQL queries without parameters.
            stmt = dbcon.createStatement();

            // Perform CRUD operations

            // Create
//            String insertSql = "INSERT INTO student (sid, name) VALUES (3, 'John Doe')";
//            stmt.executeUpdate(insertSql);
//            System.out.println("Record inserted");

            // Read
            String selectSql = "SELECT * FROM student";
            rs = stmt.executeQuery(selectSql);
            System.out.println("Reading records:");
            while (rs.next()) {
                int sid = rs.getInt("sid");
                String name = rs.getString("name");
                System.out.println("ID: " + sid + ", Name: " + name);
            }

            // Update
//            String updateSql = "UPDATE student SET name = 'Jane Doe' WHERE sid = 3";
//            stmt.executeUpdate(updateSql);
//            System.out.println("Record updated");

            // Delete
//            String deleteSql = "DELETE FROM student WHERE sid = 2";
//            stmt.executeUpdate(deleteSql);
//            System.out.println("Record deleted");

            /*Important notes
            * There are three type of statement statement , processed statement */

            // 2. PreparedStatement
            // PreparedStatement: Used for executing precompiled SQL queries with or without input parameters.
            // It is more secure and efficient than Statement.
            String insertSql = "INSERT INTO student (sid, name) VALUES (?, ?)";
             pstmt = dbcon.prepareStatement(insertSql);
            pstmt.setInt(1, 4);
            pstmt.setString(2, "pc again");
            pstmt.executeUpdate();
            System.out.println("Record inserted using PreparedStatement");

            // 3. CallableStatement
            // CallableStatement: Used for executing SQL stored procedures.
            // It allows the execution of database stored procedures that can accept input parameters and return output parameters or results.
            // Assuming a stored procedure getStudentName exists in your database
            String callSql = "{call getStudentName(?, ?)}";
            cstmt = dbcon.prepareCall(callSql);
            cstmt.setInt(1, 3);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.execute();
            String studentName = cstmt.getString(2);
            System.out.println("Student Name from CallableStatement: " + studentName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (dbcon != null) dbcon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
