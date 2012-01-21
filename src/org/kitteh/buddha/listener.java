package org.kitteh.buddha;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class listener implements Listener {

    private final mode buddha;

    public listener(mode buddha) {
        this.buddha = buddha;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            if (this.buddha.enabled.contains(player.getName())) {
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
            this.buddha.enabled.add(event.getPlayer().getName());
        }
    }
}
