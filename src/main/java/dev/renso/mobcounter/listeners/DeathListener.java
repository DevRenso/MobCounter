package dev.renso.mobcounter.listeners;

import dev.renso.mobcounter.MobCounter;
import dev.renso.mobcounter.manager.MySQLManager;
import dev.renso.mobcounter.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeathListener implements Listener {

    private MySQLManager mySQLManager;

    public DeathListener(){
        this.mySQLManager = MobCounter.getInstance().getMySQLManager();
        Bukkit.getPluginManager().registerEvents(this, MobCounter.getInstance());
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(this.isHostile(e.getEntity().getType()) && e.getEntity().getKiller() != null && e.getEntity().getKiller().getType().equals(EntityType.PLAYER)){
            Player killer = e.getEntity().getKiller();
            mySQLManager.setKills(Utils.TABLE, killer.getUniqueId(), mySQLManager.getKills(Utils.TABLE, killer.getUniqueId())+1);
            int kills = mySQLManager.getKills(Utils.TABLE, killer.getUniqueId());
            MobCounter.getInstance().getConfig().getConfigurationSection("KILL-REWARDS").getKeys(false).forEach(rewards ->{
                ConfigurationSection rewardsSection = MobCounter.getInstance().getConfig().getConfigurationSection("KILL-REWARDS."+rewards);
                if(kills == Integer.valueOf(rewards)){
                    rewardsSection.getStringList("COMMANDS").forEach(m -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), m.replace("%player%", killer.getName())));
                }
            });
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        mySQLManager.createPlayer(Utils.TABLE, e.getPlayer().getUniqueId());
    }

    private boolean isHostile(EntityType entity){
        return (entity.equals(EntityType.SKELETON) ||
                entity.equals(EntityType.BLAZE) ||
                entity.equals(EntityType.CAVE_SPIDER) ||
                entity.equals(EntityType.SPIDER) ||
                entity.equals(EntityType.ENDERMAN) ||
                entity.equals(EntityType.PIG_ZOMBIE) ||
                entity.equals(EntityType.ZOMBIE));
    }

}
