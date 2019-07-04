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

public class NineNineSevenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player){
            if (strings.length == 0){
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.997$usage"));
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < strings.length; i++) {
                sb.append(strings[i]).append(" ");
            }
            if (((Player) cs).getItemInHand().getType() == Material.CLAY_BRICK && ((Player) cs).getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.fixColor(Main.getInstance().getConfig().getString("xmon.phone$title")))) {
                Bukkit.getOnlinePlayers().stream().forEach(x -> {
                    if (x.hasPermission("xmon.997")) {
                        if (((Player) x).getInventory().contains(Material.CLAY_BRICK)) {
                            ChatUtils.sendMessage((Player) x, Main.getInstance().getConfig().getString("xmon.997$sendto").replace("{MESSAGE}", sb.toString()).replace("{CORDS}", Main.getInstance().getConfig().getString("xmon.service$cords$template")
                                    .replace("{x}", String.valueOf(((Player) cs).getLocation().getBlockX()))
                                    .replace("{y}", String.valueOf(((Player) cs).getLocation().getBlockY()))
                                    .replace("{z}", String.valueOf(((Player) cs).getLocation().getBlockZ()))));
                            x.playSound(x.getLocation(), Sound.BLOCK_GLASS_PLACE, 100, 100);
                        }
                    }
                });
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.997$confirm"));
            }else{
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.997$phone$in$hand"));
            }
        }else{
            return ChatUtils.sendMessage(cs, "&4Błąd: &cKomende może wykonywać jedynie gracz.");
        }
    }
}

