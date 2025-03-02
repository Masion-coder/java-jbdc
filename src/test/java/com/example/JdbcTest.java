package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import com.example.module.User;

public class JdbcTest {

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        // 1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 获取连接
        String url = "jdbc:mysql://localhost:3306/web01";
        String user = "root";
        String password = "123456";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            // 3. 获取SQL执行对象
            try (Statement statement = connection.createStatement()) {

                // 4. 执行SQL
                int i = statement.executeUpdate("update user set age = 25 where id = 1");; // DML,返回值是影响的行数
                System.out.println("i = " + i);

                // 5. 释放资源
            }
        }
    }

    @Test
    public void testSelect() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/web01";
        String user = "root";
        String password = "123456";

        String query = "SELECT id, username, password, name, age FROM user WHERE username = ? AND password = ?"; // ? 占位符，预编译SQL

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "daqiao");
            stmt.setString(2, "123456");

            ResultSet rs = stmt.executeQuery(); // 封装查询结果

            while (rs.next()) {
                User userObj = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getInt("age"));

                System.out.println(userObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
