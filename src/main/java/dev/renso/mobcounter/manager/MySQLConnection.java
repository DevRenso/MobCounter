package dev.renso.mobcounter.manager;

import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    @Getter private Connection connection;
    private String host, database, username, password;
    private int port;


    public MySQLConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        try {
            synchronized(this) {
                if(connection != null && !connection.isClosed()){
                    Bukkit.getConsoleSender().sendMessage("no connect");
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection("jdbc:mysql://"+this.host+":"+this.port+"/"+this.database,this.username,this.password);
                Bukkit.getConsoleSender().sendMessage("connected");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
