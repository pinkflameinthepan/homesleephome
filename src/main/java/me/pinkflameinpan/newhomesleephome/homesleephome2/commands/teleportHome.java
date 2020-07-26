package me.pinkflameinpan.newhomesleephome.homesleephome2.commands;

import me.pinkflameinpan.newhomesleephome.homesleephome2.HomeSleepHome2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class teleportHome implements CommandExecutor {

    //Implement the main class for config options
    private HomeSleepHome2 plugin;
    public teleportHome(){
        plugin = (HomeSleepHome2)HomeSleepHome2.getPlugin(HomeSleepHome2.class);
    }

    ///hsh command to teleport the player
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        int bedDistance = (int) player.getBedSpawnLocation().distance(player.getLocation());

        //Get the config options
        boolean distancecooldown = plugin.getConfig().getBoolean("cooldown");
        int distanceamount = plugin.getConfig().getInt("amount");
        int distancetime = plugin.getConfig().getInt("time");

        if(bedDistance > distanceamount){
            //IF the distance to the bed is greater than x in the config:

            List<String> playercooldown = plugin.getConfig().getStringList("player-cooldown");
            String playername = player.getName();

            if(!playercooldown.contains(playername)){

                player.teleport(player.getBedSpawnLocation());
                player.sendMessage(ChatColor.GREEN + "You have been teleported to your bedspawn outside of " +
                        ChatColor.YELLOW + distanceamount + " blocks" + ChatColor.GREEN + "!");
                playercooldown.add(playername);
                plugin.getConfig().set("player-cooldown", playercooldown);
                plugin.saveConfig();

                //Scheduled task of x time to remove the player from the cooldown list in the config
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        playercooldown.remove(playername);
                        plugin.getConfig().set("player-cooldown", playercooldown);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.GREEN + "You can teleport home outside of " + ChatColor.YELLOW + distanceamount +
                                " blocks " + ChatColor.GREEN + "again!");
                    }
                }, distancetime);

            }else{

                player.sendMessage(ChatColor.RED + "You need to wait before you can teleport home outside of " +
                        ChatColor.YELLOW + distanceamount + " blocks" + ChatColor.RED + " again!");
                return true;

            }
            return true;

        }else if(bedDistance <= distanceamount){
            //IF NOT then IF the distance to the bed is equal to or less than x in the config:

            player.teleport(player.getBedSpawnLocation());
            player.sendMessage(ChatColor.GREEN + "You have been teleported to your bedspawn within " +
                    ChatColor.YELLOW + distanceamount + " blocks" + ChatColor.GREEN + "!");
            return true;

        }else if(bedDistance == 0){
            //IF NOT then IF the distance to the bed is equal to 0:

            player.sendMessage(ChatColor.RED + "You cannot teleport to a bedspawn!");
            return true;

        }

        return true;

    }

}

