package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModel {
    Statement statement = null;

    public UserModel() {
        try {
            statement = DB.connection.createStatement();
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
}
