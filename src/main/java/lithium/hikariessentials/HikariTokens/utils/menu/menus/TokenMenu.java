package lithium.hikariessentials.HikariTokens.utils.menu.menus;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.HikariTokens.utils.TokenUtils;
import lithium.hikariessentials.HikariTokens.utils.menu.TokenMenuUtil;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static lithium.hikariessentials.HikariTokens.utils.TokenUtils.msgPlayer;

public class TokenMenu extends lithium.hikariessentials.HikariTokens.utils.menu.TokenMenu {


    private final HikariMain te = HikariMain.getPlugin(HikariMain.class);

    public TokenMenu(TokenMenuUtil menuUtil) {
        super(menuUtil);
    }

    @Override
    public String getMenuName() {
        return TokenUtils.applyFormat(HikariMain.getConfigManager().getTitleMenu(menuUtil.getOwner()));
    }

    @Override
    public int getSlots() {
        return HikariMain.getConfigManager().getSlotsMenu();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        String exchange = HikariMain.getConfigManager().getTokenMenu().getString("gui.items.exchange.displayname");
        String top = HikariMain.getConfigManager().getTokenMenu().getString("gui.items.top.displayname");

        if (Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName().equals(TokenUtils.applyFormat(exchange))) {
            e.setCancelled(true);
            new ExchangeMenu(HikariMain.getMenuUtil(menuUtil.getOwner())).open();
        } else if (Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName().equals(TokenUtils.applyFormat(top))) {
            e.setCancelled(true);
            new TopMenu(HikariMain.getMenuUtil(menuUtil.getOwner())).open();
        } else if (Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName().equals(TokenUtils.applyFormat("&6Token Guide &7(Commands)"))) {
            e.setCancelled(true);
            menuUtil.getOwner().closeInventory();
            msgPlayer(menuUtil.getOwner(), "&e&nToken Commands&r",
                    "",
                    "&#00fb9a/tokens pay <user> <amount> &7- sende einem Spieler Tokens.",
                    "&#00fb9a/tokens balance &7- zeigt deinen Tokenstand an.",
                    "&#00fb9a/tokens bank &7- zeigt deinen Kontostand auf der Bank an.",
                    "&#00fb9a/tokens exchange &7- Öffnet das austausch menu",
                    "&#00fb9a/tokens top &7- Öffnet die Top-Liste",
                    "&#00fb9a/tokens stats &7- Siehe die Server Statistik",
                    "&#00fb9a/tokens toggle &7- Erlaube/Verbiete das erhalten von Tokens.",
                    "&#00fb9a/tokens help &7- zeigt diese Nachricht.",
                    "");
        } else {
            e.setCancelled(true);
        }
    }

    @Override
    public void setMenuItems() {

        // add config items
        for (String items : Objects.requireNonNull(HikariMain.getConfigManager().getTokenMenu().getConfigurationSection("gui.items")).getKeys(false)) {
            TokenManager tokens = HikariMain.getTokenManager(menuUtil.getOwner());

            String displayname = Objects.requireNonNull(HikariMain.getConfigManager().getTokenMenu().getString("gui.items." + items + ".displayname")).replaceAll("%tokens%", String.valueOf(tokens.getTokens()));
            int slot = HikariMain.getConfigManager().getTokenMenu().getInt("gui.items." + items + ".slot");
            boolean glow = HikariMain.getConfigManager().getTokenMenu().getBoolean("gui.items." + items + ".glow");
            String material = Objects.requireNonNull(HikariMain.getConfigManager().getTokenMenu().getString("gui.items." + items + ".material")).toUpperCase();

            ItemStack item = new ItemStack(Material.valueOf(material), 1);

            for (String lore1 : HikariMain.getConfigManager().getTokenMenu().getStringList("gui.items." + items + ".lore")) {
                nameItem(item, ColorUtils.translateColorCodes(displayname), lore1.replaceAll("%tokens%", String.valueOf(tokens.getTokens())));
            }

            if (glow) {
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            }

            inventory.setItem(slot, item);
        }


        // book item

        if (inventory.getSize() == 27) {
            ItemStack guide = new ItemStack(Material.BOOK, 1);
            ItemMeta guide_meta = guide.getItemMeta();
            assert guide_meta != null;
            guide_meta.setDisplayName(ColorUtils.translateColorCodes("&6Token Guide &7(Commands)"));

            List<String> lore = new ArrayList<>();
            lore.add("&7Hier klicken um Token Befehle zu sehen.");
            // ============================================================================

            for (String l : lore) {
                guide_meta.setLore(Collections.singletonList(TokenUtils.applyFormat(l)));
            }

            guide.setItemMeta(guide_meta);

            inventory.setItem(26, guide);
        } else if (inventory.getSize() == 54) {
            ItemStack guide = new ItemStack(Material.BOOK, 1);
            ItemMeta guide_meta = guide.getItemMeta();
            assert guide_meta != null;
            guide_meta.setDisplayName(ColorUtils.translateColorCodes("&6Token Guide &7(Commands)"));

            List<String> lore = new ArrayList<>();
            lore.add("&7Hier klicken um Token Befehle zu sehen.");
            // ============================================================================

            for (String l : lore) {
                guide_meta.setLore(Collections.singletonList(TokenUtils.applyFormat(l)));
            }

            guide.setItemMeta(guide_meta);

            inventory.setItem(53, guide);
        }

        // fill glass
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta glass_meta = glass.getItemMeta();
        assert glass_meta != null;
        glass_meta.setDisplayName(TokenUtils.applyFormat("&8*"));
        glass.setItemMeta(glass_meta);

        // loop through empty slots and set glass
        for(int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }

    private ItemStack nameItem(final ItemStack item, final String name, final String... lore) { //  Changes String lore to String... Lore
        final ItemMeta item_meta = item.getItemMeta();
        assert item_meta != null;
        item_meta.setDisplayName(name); // You should most likely translate here too unless you do it beforehand.
        /* Setting the lore but translating the color codes as well. */
        item_meta.setLore(Arrays.stream(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));

        item_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item_meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item_meta.addItemFlags(ItemFlag.HIDE_DESTROYS);

        item.setItemMeta(item_meta);
        return item;
    }

    public List<String> wordWrapLore(String string) {
        StringBuilder sb = new StringBuilder(string);

        int i = 0;
        while (i + 35 < sb.length() && (i = sb.lastIndexOf(" ", i + 35)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        return Arrays.asList(sb.toString().split("\n"));

    }

}
