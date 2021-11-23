package dev.renso.mobcounter.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLManager {

    private Connection connection;

    public MySQLManager(Connection connection) {
        this.connection = connection;
    }

    public void createTable(String table){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+table+"(UUID varchar(64), kills int)");
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private boolean hasPlayer(String table, UUID uuid){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+table+" WHERE (UUID=?)");
            preparedStatement.setString(1, uuid.toString());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(String table, UUID uuid){
        try{
            if(this.hasPlayer(table, uuid)) return;
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO "+table+" VALUE (?,?)");
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, 0);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int getKills(String table, UUID uuid){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+table+" WHERE (UUID=?)");
            preparedStatement.setString(1, uuid.toString());
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getInt("kills");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void setKills(String table, UUID uuid, int kills){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE "+table+" SET kills=? WHERE (UUID=?)");
            preparedStatement.setInt(1, kills);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
