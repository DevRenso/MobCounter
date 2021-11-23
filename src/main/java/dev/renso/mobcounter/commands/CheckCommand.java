package dev.renso.mobcounter.commands;

import dev.renso.mobcounter.MobCounter;
import dev.renso.mobcounter.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand implements CommandExecutor {

    public CheckCommand(){
        MobCounter.getInstance().getCommand("check").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Utils.CC("&cYou don't execute this command in console."));
            return true;
        }
        Player player = (Player)sender;
        player.sendMessage(Utils.CC("&6Your kills are: "+MobCounter.getInstance().getMySQLManager().getKills(Utils.TABLE, player.getUniqueId())));
        return false;
    }
}
