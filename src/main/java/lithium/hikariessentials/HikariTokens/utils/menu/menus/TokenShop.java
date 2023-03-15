package lithium.hikariessentials.HikariTokens.utils.menu.menus;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.manager.BankManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.HikariTokens.utils.TokenUtils;
import lithium.hikariessentials.HikariTokens.utils.menu.TokenMenuUtil;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class TokenShop extends TokenMenu {


    private final HikariEssentialsToken te = HikariEssentialsToken.getPlugin(HikariEssentialsToken.class);

    public TokenShop(TokenMenuUtil menuUtil) {
        super(menuUtil);
    }

    @Override
    public String getMenuName() {
        return TokenUtils.applyFormat(HikariEssentialsToken.getConfigManager().getTitleShop());
    }

    @Override
    public int getSlots() {
        return HikariEssentialsToken.getConfigManager().getSlotsShop();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        for (String items : Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getConfigurationSection("gui.items")).getKeys(false)) {
            String displayname = HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.items." + items + ".displayname");
            int tokens = HikariEssentialsToken.getConfigManager().getTokenExchange().getInt("gui.items." + items + ".tokens");
            int amount_material = HikariEssentialsToken.getConfigManager().getTokenExchange().getInt("gui.items." + items + ".amount");


            if (Objects.requireNonNull(Objects.requireNonNull(e.getCurrentItem()).getItemMeta()).getDisplayName().equals(TokenUtils.applyFormat(displayname))) {
                if (hasMaterial(player.getInventory(), amount_material)) {
                    e.setCancelled(true);
                    TokenManager ptokens = HikariEssentialsToken.getTokenManager(player);
                    if (!(ptokens.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        // remove material from inventory
                        removeMaterial(player.getInventory(), amount_material);

                        // get and add tokens
                        if (!HikariEssentialsToken.getConfigManager().isBankBalanceShop()) {
                            TokenManager token = HikariEssentialsToken.getTokenManager(player);
                            token.addTokens(tokens);
                        } else {
                            BankManager bank = HikariEssentialsToken.getBankManager(player);
                            bank.addBank(tokens);
                        }

                        // confirmation
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getMessages().getString("m.RECEIVED")).replaceAll("%tokens%", String.valueOf(tokens)).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        if (HikariEssentialsToken.getConfigManager().getTokenExchange().getBoolean("gui.sound.enable")) {
                            player.playSound(player.getLocation(),
                                    Sound.valueOf(Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.sound.success")).toUpperCase()), 1, 1);
                        }
                    } else {
                        player.sendMessage(TokenUtils.applyFormat("&cDu kannst keine Tokens mehr aufnehmen!"));
                    }
                } else {
                    player.sendMessage(TokenUtils.applyFormat(Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getMessages().getString("m.NOT_ENOUGH_MATERIALS")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                    if (HikariEssentialsToken.getConfigManager().getTokenExchange().getBoolean("gui.sound.enable")) {
                        player.playSound(player.getLocation(),
                                Sound.valueOf(Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.sound.failed")).toUpperCase()), 1, 1);
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems() {


        // main items

        for (String items : Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getConfigurationSection("gui.items")).getKeys(false)) {

            String displayname = HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.items." + items + ".displayname");
            int slot = HikariEssentialsToken.getConfigManager().getTokenExchange().getInt("gui.items." + items + ".slot");
            boolean glow = HikariEssentialsToken.getConfigManager().getTokenExchange().getBoolean("gui.items." + items + ".glow");
            String material = Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.items." + items + ".material")).toUpperCase();
            int amount = HikariEssentialsToken.getConfigManager().getTokenExchange().getInt("gui.items." + items + ".amount");

            //List<String> lore = HikariEssentialsToken.getConfigManager().getTokenshop().getStringList("gui.items." + items + ".lore");

            ItemStack item = new ItemStack(Material.valueOf(material), amount);

            for (String lore : HikariEssentialsToken.getConfigManager().getTokenExchange().getStringList("gui.items." + items + ".lore")) {
                nameItem(item, TokenUtils.applyFormat(displayname), lore);
            }

            if (glow) {
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            }

            inventory.setItem(slot, item);
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

    private void removeMaterial(Inventory inventory, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (Material.valueOf(Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.item-exchange")).toUpperCase()) == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }

    private boolean hasMaterial(Inventory inventory, int amount) {
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (Material.valueOf(Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getTokenExchange().getString("gui.item-exchange")).toUpperCase()) == is.getType()
                    && is.getAmount() >= amount)
                return true;
        }

        return false;
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
}
