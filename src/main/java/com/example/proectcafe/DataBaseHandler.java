package com.example.proectcafe;

import java.sql.*;

public class DataBaseHandler {
    Connection connection;
    ResultSet resSet = null;

    public Connection getDBConnection() throws SQLException {
        String connectionStrings = "jdbc:postgresql://localhost:5432/CofeProect";
        connection = DriverManager.getConnection(connectionStrings, "postgres", "admin");
        return connection;
    }

    public boolean insertCoffee(Coffee coffee) throws SQLException {
        String insertUser = "INSERT INTO coffee(имя_кофе, размер_кофе, цена_кофе) VALUES (?, ?, ?)";

        try (PreparedStatement prSt = getDBConnection().prepareStatement(insertUser)) {
            prSt.setString(1, coffee.getCoffename());
            prSt.setString(2, coffee.getCoffesize());
            prSt.setInt(3, coffee.getCoffeprice());
            int rowsAffected = prSt.executeUpdate();
            return rowsAffected > 0; // Возвращаем true, если были затронуты строки
        }
    }

    public ResultSet getCoffee() throws SQLException{
        String getCoffee = "SELECT * FROM coffee";

        PreparedStatement prST = getDBConnection().prepareStatement(getCoffee);
        resSet = prST.executeQuery();
        return resSet;
    }

    public void updateCoffee(Coffee coffee) throws SQLException {
        String updateQuery = "UPDATE coffee SET имя_кофе = ?, размер_кофе = ?, цена_кофе = ? WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(updateQuery);
        prSt.setString(1, coffee.getCoffename());
        prSt.setString(2, coffee.getCoffesize());
        prSt.setInt(3, coffee.getCoffeprice());
        prSt.setInt(4, coffee.getId());
        prSt.executeUpdate();
    }

    public void deleteCoffee(int coffeeId) throws SQLException {
        String deleteQuery = "DELETE FROM coffee WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(deleteQuery);
        prSt.setInt(1, coffeeId);
        prSt.executeUpdate();
    }

    public boolean insertOrders(Orders orders) throws SQLException {
        String insertUser = "INSERT INTO orders (имя_заказа, размер_заказа, количество, сумма, продавец) " +
                "SELECT c.имя_кофе, c.размер_кофе, ? AS количество, ? * c.цена_кофе AS сумма, ? AS продавец " +
                "FROM coffee AS c " +
                "WHERE c.имя_кофе = ? AND c.размер_кофе = ?;";

        try (PreparedStatement prSt = getDBConnection().prepareStatement(insertUser)) {
            prSt.setInt(1, orders.getOrderscount());
            prSt.setInt(2, orders.getOrderscount());
            prSt.setString(3, orders.getOrdersseller());
            prSt.setString(4, orders.getOrdersname());
            prSt.setString(5, orders.getOrderssize());
            int rowsAffected = prSt.executeUpdate();
            return rowsAffected > 0; // Возвращаем true, если были затронуты строки
        }
    }

    public void updateOrders(Orders orders) throws SQLException {
        String updateOrders = "UPDATE orders " +
                "SET количество = ?, сумма = ? * c.цена_кофе, продавец = ? " +
                "FROM coffee AS c " +
                "WHERE orders.имя_заказа = ? AND orders.размер_заказа = ?";

        try (PreparedStatement prSt = getDBConnection().prepareStatement(updateOrders)) {
            prSt.setInt(1, orders.getOrderscount());
            prSt.setInt(2, orders.getOrderscount());
            prSt.setString(3, orders.getOrdersseller());
            prSt.setString(4, orders.getOrdersname());
            prSt.setString(5, orders.getOrderssize());
            prSt.executeUpdate();
        }
    }

    public void deleteOrders(int ordersId) throws SQLException {
        String deleteQuery = "DELETE FROM orders WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(deleteQuery);
        prSt.setInt(1, ordersId);
        prSt.executeUpdate();
    }

    public ResultSet getOrders() throws SQLException{
        String getOrder = "SELECT * FROM orders";

        PreparedStatement prST = getDBConnection().prepareStatement(getOrder);
        resSet = prST.executeQuery();
        return resSet;
    }

    public int getTotalAmount() throws SQLException {
        String query = "SELECT SUM(сумма) AS total FROM orders";
        try (Statement statement = getDBConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        }
        return 0; // Возвращаем 0, если нет данных или произошла ошибка
    }


}
