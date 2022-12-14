package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;


public class DBHelper {


    @SneakyThrows
    public static void cleanDB() {
        var deleteOrder = "DELETE FROM order_entity;";
        var deletePayment = "DELETE FROM payment_entity;";
        var deleteCreditRequest = "DELETE FROM credit_request_entity;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(System.getProperty("dbUrl"), System.getProperty("dbUser"), System.getProperty("dbPass"));
        ) {
            runner.update(conn, deleteOrder);
            runner.update(conn, deletePayment);
            runner.update(conn, deleteCreditRequest);
        }
    }


    @SneakyThrows
    public static String getPaymentStatusDB() {
        var sql = "SELECT status FROM payment_entity;";
        var runner = new QueryRunner();
        String paymentStatus;

        try (
                var conn = DriverManager.getConnection(System.getProperty("dbUrl"), System.getProperty("dbUser"), System.getProperty("dbPass"));
        ) {
            paymentStatus = runner.query(conn, sql, new ScalarHandler<>());
        }
        return paymentStatus;
    }


    @SneakyThrows
    public static long getOrderCount() {
        var sql = "SELECT COUNT(id) as count FROM order_entity;";
        var runner = new QueryRunner();
        long orderCount;

        try (
                var conn = DriverManager.getConnection(System.getProperty("dbUrl"), System.getProperty("dbUser"), System.getProperty("dbPass"));
        ) {
            orderCount = runner.query(conn, sql, new ScalarHandler<>());
        }
        return orderCount;
    }


}
