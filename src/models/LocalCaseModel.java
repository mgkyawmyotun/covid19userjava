package models;

import java.sql.*;

public class LocalCaseModel {


    Statement statement = null;
    Connection connection = null;

    public LocalCaseModel() {
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
                "    lng int(8) not null,\n" +
                "    lat int(8) not null\n" +
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


}


