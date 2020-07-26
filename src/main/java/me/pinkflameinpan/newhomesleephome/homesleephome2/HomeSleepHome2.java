package me.pinkflameinpan.newhomesleephome.homesleephome2;

import me.pinkflameinpan.newhomesleephome.homesleephome2.commands.teleportHome;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HomeSleepHome2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        //Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        //Startup message(s)
        System.out.println("[HSH2] Started up! Good morning!");

        //Command registration(s)
        Objects.requireNonNull(this.getCommand("hsh")).setExecutor(new teleportHome());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
