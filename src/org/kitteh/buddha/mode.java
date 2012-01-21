package org.kitteh.buddha;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class mode extends JavaPlugin {

    private class BuddhaCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if ((sender instanceof Player) && sender.hasPermission("buddha.use")) {
                if (mode.this.enabled.contains(sender.getName())) {
                    mode.this.enabled.remove(sender.getName());
                    sender.sendMessage(ChatColor.YELLOW + "You are no longer enlightened.");
                } else {
                    mode.this.enabled.add(sender.getName());
                    sender.sendMessage(ChatColor.YELLOW + "You feel enlightened.");
                }
            } else {
                sender.sendMessage("Error: Not sufficiently enlightened.");
            }
            return true;
        }

    }

    public HashSet<String> enabled;

    @Override
    public void onDisable() {
        this.getServer().getLogger().info("BuddhaMode disabled");
    }

    @Override
    public void onEnable() {
        this.getCommand("buddha").setExecutor(new BuddhaCommand());
        this.enabled = new HashSet<String>();
        this.getServer().getPluginManager().registerEvents(new listener(this), this);
        this.getServer().getLogger().info("BuddhaMode enabled");
    }

}
