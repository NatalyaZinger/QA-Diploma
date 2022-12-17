package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBHelper {


    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("dbUrl"), System.getProperty("dbUser"), System.getProperty("dbPass"));
    }


    @SneakyThrows
    public static void cleanDB() {
        var deleteOrder = "DELETE FROM order_entity;";
        var deletePayment = "DELETE FROM payment_entity;";
        var deleteCreditRequest = "DELETE FROM credit_request_entity;";
        var runner = new QueryRunner();
        try (
                var conn = getConn()
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
                var conn = getConn()
        ) {
            paymentStatus = runner.query(conn, sql, new ScalarHandler<>());
        }
        return paymentStatus;
    }


    @SneakyThrows
    public static String getCreditPaymentStatusDB() {
        var status = "SELECT status FROM credit_request_entity;";
        var runner = new QueryRunner();
        String creditPaymentStatus;

        try (var conn = getConn()) {
            creditPaymentStatus = runner.query(conn, status, new ScalarHandler<>());
        }

        return creditPaymentStatus;
    }


    @SneakyThrows
    public static long getOrderCount() {
        var sql = "SELECT COUNT(id) as count FROM order_entity;";
        var runner = new QueryRunner();
        long orderCount;

        try (var conn = getConn()) {
            orderCount = runner.query(conn, sql, new ScalarHandler<>());
        }
        return orderCount;
    }


    @SneakyThrows
    public static long getPaymentCount() {
        var sql = "SELECT COUNT(id) as count FROM payment_entity;";
        var runner = new QueryRunner();
        long paymentCount;

        try (var conn = getConn()) {
            paymentCount = runner.query(conn, sql, new ScalarHandler<>());
        }
        return paymentCount;
    }


    @SneakyThrows
    public static long getCreditPaymentCount() {
        var sql = "SELECT COUNT(id) as count FROM credit_request_entity;";
        var runner = new QueryRunner();
        long creditPaymentCount;

        try (var conn = getConn()) {
            creditPaymentCount = runner.query(conn, sql, new ScalarHandler<>());
        }
        return creditPaymentCount;
    }


}
