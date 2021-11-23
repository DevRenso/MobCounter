package dev.renso.mobcounter.utils;

import dev.renso.mobcounter.MobCounter;
import org.bukkit.ChatColor;

public class Utils {

    public static String TABLE = MobCounter.getInstance().getConfig().getString("MYSQL.TABLE");

    public static String CC(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
