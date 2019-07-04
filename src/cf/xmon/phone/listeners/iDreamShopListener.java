package cf.xmon.phone.listeners;

import cf.xmon.phone.Main;
import cf.xmon.phone.object.User;
import cf.xmon.phone.utils.ChatUtils;
import cf.xmon.phone.utils.ItemBuilder;
import cf.xmon.phone.utils.UserUtils;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class iDreamShopListener implements Listener {
    @EventHandler
    public void onVillageOpenShop(NPCRightClickEvent e) {
        if (e.getNPC().getName().equalsIgnoreCase(Main.getInstance().getConfig().getString("xmon.shop$villager$name"))){
            e.setCancelled(true);
            villageShop(e.getClicker().getPlayer());
        }
    }
    /*
    @EventHandler
    public void onHit(EntityDamageEvent e) {
        if (e.getEntityType() != EntityType.VILLAGER) return;
        if (e.getEntityType() == EntityType.VILLAGER) {
            if (e.getEntity().getCustomName().equals("iDream")) {
                e.setCancelled(true);
            }
        }
    }
     */
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = p.getInventory();
        if (e.getInventory().getName().equalsIgnoreCase(Main.getInstance().getConfig().getString("xmon.shop$villager$name"))) {
            if (e.getSlot() == 4) {
                e.setCancelled(true);
                String number = "-";
                number = String.valueOf(ChatUtils.generateRandomIntIntRange(10000, 99999));
                User u = UserUtils.get(p);
                if (u.getNumber().equals("null")) {
                    if (UserUtils.getByNumber(number) == null) {
                        if (Main.getEconomy().getBalance(p.getPlayer()) < Main.getInstance().getConfig().getDouble("xmon.phone$cost")) {
                            ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$no$money").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.phone$cost"))));
                        } else {
                            u.setNumber(number);
                            p.getInventory().addItem(new ItemBuilder(Material.CLAY_BRICK).setTitle(ChatUtils.fixColor(Main.getInstance().getConfig().getString("xmon.phone$title"))).addLore(ChatUtils.fixColor("&2Koszt SMS'u - " + Main.getInstance().getConfig().getInt("xmon.sms$cost") + "$")).addLore(ChatUtils.fixColor("&2Twój numer telefonu to: " + u.getNumber())).build());
                            Main.getEconomy().withdrawPlayer(p.getName(), Main.getInstance().getConfig().getDouble("xmon.phone$cost"));
                            ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$buy").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.phone$cost"))));
                            ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$success$buy"));
                        }
                    } else {
                        ChatUtils.sendMessage(p, "&4Błąd: &cSpróbuj kupić ponownie!");
                    }
                } else {
                    if (p.getInventory().contains(Material.CLAY_BRICK)){
                        ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$contain$phone"));
                    }else {
                        if (Main.getEconomy().getBalance(p.getPlayer()) < Main.getInstance().getConfig().getDouble("xmon.phone$cost")) {
                            ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$no$money").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.phone$cost"))));
                        } else {
                            p.getInventory().addItem(new ItemBuilder(Material.CLAY_BRICK).setTitle(ChatUtils.fixColor(Main.getInstance().getConfig().getString("xmon.phone$title"))).addLore(ChatUtils.fixColor("&2Koszt SMS'u - " + Main.getInstance().getConfig().getInt("xmon.sms$cost") + "$")).addLore(ChatUtils.fixColor("&2Twój numer telefonu to: " + u.getNumber())).build());
                            Main.getEconomy().withdrawPlayer(p.getName(), Main.getInstance().getConfig().getDouble("xmon.phone$cost"));
                            ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$buy").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.phone$cost"))));
                            ChatUtils.sendMessage(p, Main.getInstance().getConfig().getString("xmon.shop$success$buy"));
                        }
                    }
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    private static InventoryView villageShop(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "iDream");
        ItemBuilder glass = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setTitle("( )");
        ItemBuilder phone = new ItemBuilder(Material.CLAY_BRICK).setTitle("Telefon - Koszt: " + Main.getInstance().getConfig().getInt("xmon.phone$cost") + "$").addLore(ChatUtils.fixColor("&2Zakup telefon, aby móc pisać SMS'y oraz wzywać pomoc!")).addLore(ChatUtils.fixColor("&2Koszt SMS'u - " + Main.getInstance().getConfig().getInt("xmon.sms$cost") + "$")).addLore(ChatUtils.fixColor("&2Twój numer telefonu to: <Numer Telefonu>"));
        inv.setItem(4, phone.build());
        inv.setItem(0, glass.build());
        inv.setItem(1, glass.build());
        inv.setItem(2, glass.build());
        inv.setItem(3, glass.build());
        inv.setItem(5, glass.build());
        inv.setItem(6, glass.build());
        inv.setItem(7, glass.build());
        inv.setItem(8, glass.build());
        return p.openInventory(inv);
    }
}
