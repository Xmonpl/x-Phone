package cf.xmon.phone.commands;

import cf.xmon.phone.Main;
import cf.xmon.phone.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class iDreamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player) {
            if (cs.hasPermission("xmon.idream")){
                Villager v = (Villager)((Player) cs).getWorld().spawnEntity(((Player) cs).getLocation(), EntityType.VILLAGER);
                v.setProfession(Villager.Profession.FARMER);
                v.setCareer(Villager.Career.FARMER);
                v.setAI(false);
                v.setSilent(true);
                v.setHealth(10.00);
                v.setCanPickupItems(false);
                v.setCustomName(Main.getInstance().getConfig().getString("xmon.shop$villager$name"));
                v.setCustomNameVisible(true);
                return ChatUtils.sendMessage(cs, "Utworzono!");
            }else{
                return ChatUtils.sendMessage(cs, "&4Błąd: &cKomende może wykonywać jedynie administrator.");
            }
        } else {
            return ChatUtils.sendMessage(cs, "&4Błąd: &cKomende może wykonywać jedynie gracz.");
        }
    }
}