package org.kitteh.buddha;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BuddhaMode extends JavaPlugin implements Listener {

    private final HashSet<String> enlightened = new HashSet<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((sender instanceof Player) && sender.hasPermission("buddha.use")) {
            if (BuddhaMode.this.enlightened.contains(sender.getName())) {
                BuddhaMode.this.enlightened.remove(sender.getName());
                sender.sendMessage(ChatColor.YELLOW + "You are no longer enlightened.");
            } else {
                BuddhaMode.this.enlightened.add(sender.getName());
                sender.sendMessage(ChatColor.YELLOW + "You feel enlightened.");
            }
        } else {
            sender.sendMessage("Error: Not sufficiently enlightened.");
        }
        return true;
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            if (this.enlightened.contains(player.getName())) {
                if (event.getDamage() >= player.getHealth()) {
                    event.setDamage(player.getHealth() - 1);
                    if (player.hasPermission("buddha.inform")) {
                        player.sendMessage(ChatColor.YELLOW + "You nearly died!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("buddha.autoenlightened")) {
            this.enlightened.add(event.getPlayer().getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.enlightened.remove(event.getPlayer().getName());
    }

}
