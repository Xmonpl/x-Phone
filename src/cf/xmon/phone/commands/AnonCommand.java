package cf.xmon.phone.commands;

import cf.xmon.phone.Main;
import cf.xmon.phone.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player){
            if (strings.length == 0){
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.anon$usage"));
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < strings.length; i++) {
                sb.append(strings[i]).append(" ");
            }
            if (((Player) cs).getItemInHand().getType() == Material.CLAY_BRICK && ((Player) cs).getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.fixColor(Main.getInstance().getConfig().getString("xmon.phone$title")))) {
                if (Main.getEconomy().getBalance(cs.getName()) < Main.getInstance().getConfig().getDouble("xmon.anon$cost")){
                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.anon$no$money").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.anon$cost"))));
                }
                Bukkit.getOnlinePlayers().stream().forEach(x -> {
                    if (((Player) x).hasPermission("xmon.anon")){
                        if (((Player) x).getInventory().contains(Material.CLAY_BRICK)) {
                            ChatUtils.sendMessage(x, Main.getInstance().getConfig().getString("xmon.anon$message$send").replace("{MESSAGE}", sb.toString()));
                            x.playSound(x.getLocation(), Sound.BLOCK_GLASS_PLACE, 100, 100);
                        }
                    }
                });
                Main.getEconomy().withdrawPlayer(cs.getName(), Main.getInstance().getConfig().getDouble("xmon.anon$cost"));
                ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.anon$money$withdraw").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.anon$cost"))));
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.anon$success$send"));
            }else{
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.anon$phone$in$hand"));
            }
        }else{
            return ChatUtils.sendMessage(cs, "&4Błąd: &cKomende może wykonywać jedynie gracz.");
        }
    }
}
