package me.adorerose.worms.storage.database;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.storage.DataStorage;

import me.adorerose.worms.storage.file.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

public class SQLStorage extends DataStorage<Connection> {

    public SQLStorage() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public void connect() throws SQLException {
        if (!connected()) {
            Configuration config = WormsPlugin.getInstance().getConfiguration();
            super.handle = DriverManager.getConnection("jdbc:mysql://" +
                    config.MYSQL__HOST + ":" + config.MYSQL__PORT +
                    "?" + "user=" + config.MYSQL__USER + "&" + "password=" + config.MYSQL__PASSWORD +
                    "&useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf8");
        }
    }

    public boolean connected()  {
        try {
            return handle != null && !handle.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void tryConnect(@Nullable Runnable callback) {
        Bukkit.getScheduler().runTaskAsynchronously(WormsPlugin.getInstance(), () -> {
            while (!connected()) {
                try {
                    connect();
                    deploySchema();
                    if (callback != null) callback.run();
                } catch (SQLException ex) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Не удалось подключиться к серверу БД.");
                    ex.printStackTrace();
                }
            }
        });
    }

    public void close() {
        try {
            handle.close();
            handle = null;
        } catch (Exception ignored) { }
    }

    public void execute(String query) throws SQLException {
        connect();
        try (Statement statement = handle.createStatement()) {
            statement.execute(query);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        connect();
        Statement statement = handle.createStatement();
        return statement.executeQuery(query);
    }

    public void deploySchema() throws SQLException {
        connect();
        StringBuilder query = new StringBuilder(64);
        try (InputStream stream = getClass().getResourceAsStream("schema.sql")) {
            try (Scanner scanner = new Scanner(stream)) {
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    if (str.length() == 0) {
                        execute(query.toString());
                        query.delete(0, query.length());
                    }
                    else query.append(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OfflinePlayer getPlayer() {
        try {
            ResultSet result = executeQuery("SELECT `value` FROM `data` WHERE `key` = 'nick' LIMIT 1");
            return result.next() ? Bukkit.getOfflinePlayer(result.getString("value")) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}