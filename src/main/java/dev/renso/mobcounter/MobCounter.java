package dev.renso.mobcounter;

import dev.renso.mobcounter.commands.CheckCommand;
import dev.renso.mobcounter.listeners.DeathListener;
import dev.renso.mobcounter.manager.MySQLConnection;
import dev.renso.mobcounter.manager.MySQLManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobCounter extends JavaPlugin {

    @Getter public static MobCounter instance;
    private MySQLConnection mySQLConnection;
    @Getter private MySQLManager mySQLManager;
    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.mySQLConnection = new MySQLConnection(this.getConfig().getString("MYSQL.HOST"),
                this.getConfig().getInt("MYSQL.PORT"),
                this.getConfig().getString("MYSQL.DATABASE"),
                this.getConfig().getString("MYSQL.USERNAME"),
                this.getConfig().getString("MYSQL.PASSWORD"));
        this.mySQLManager = new MySQLManager(mySQLConnection.getConnection());
        mySQLManager.createTable(this.getConfig().getString("MYSQL.TABLE"));
        new DeathListener();
        new CheckCommand();
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
