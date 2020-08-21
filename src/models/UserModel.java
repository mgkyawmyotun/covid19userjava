package models;

import java.sql.*;

public class UserModel {
    Statement statement = null;
    Connection connection = null;

    public UserModel() {
        try {
            statement = DB.connection.createStatement();
            connection = DB.connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void createTable() {
        String createString = "" +
                "create table if not exists users \n" +
                "(\n" +
                "\tuser_id int auto_increment primary key,\n" +
                "    username varchar(50) not null,\n" +
                "    password varchar(50) not null\n" +
                ");";
        try {

            statement.executeUpdate(createString);
        } catch (SQLException throwables) {
            System.out.println("Error At User Table Creation");
            throwables.printStackTrace();
        }
    }

    public ResultSet selectAll() {
        String selectAllString = "SELECT * FROM users";
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(selectAllString);
            return resultSet;
        } catch (SQLException throwables) {
            System.out.println("Error At SelectAll Method Users Table");
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUser(String username, String password) {
        String selectOne = "SELECT * FROM users where username = ? and password = ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectOne);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            resultSet.first();
            String s =resultSet.getString(1);
            System.out.println(s);
            return resultSet;
        } catch (SQLException throwables) {
            System.out.println("Error At SelectAll Method Users Table");
            throwables.printStackTrace();
        }
        return resultSet;
    }
}
