package lithium.hikariessentials.HikariTokens.listeners;

import lithium.hikariessentials.HikariTokens.utils.menu.TokenMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.awt.*;
import java.util.Objects;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() != null) {
            InventoryHolder holder = Objects.requireNonNull(e.getClickedInventory()).getHolder();

            if (holder instanceof TokenMenu) {
                e.setCancelled(true);

                if (e.getCurrentItem() == null) {
                    return;
                }

                TokenMenu menu = (TokenMenu) holder;
                menu.handleMenu(e);
            }
        }
    }
}
