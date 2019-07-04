package cf.xmon.phone.commands;

import cf.xmon.phone.Main;
import cf.xmon.phone.object.User;
import cf.xmon.phone.utils.ChatUtils;

import cf.xmon.phone.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class SMSCommand implements CommandExecutor {
    private static String kontakt = "-";
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player){
            if (((Player) cs).getItemInHand().getType() == Material.CLAY_BRICK && ((Player) cs).getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.fixColor(Main.getInstance().getConfig().getString("xmon.phone$title")))) {
                if (strings.length < 2) {
                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$usage"));
                }
                if (ChatUtils.isNumeric(strings[0])) {
                    User read = UserUtils.getByNumber(strings[0]);
                    if (read == null){
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$no$correct"));
                    }else{
                        Player read$Player = Bukkit.getPlayer(read.getUuid());
                        if (read$Player != null){
                            if (Main.getEconomy().getBalance(cs.getName()) < Main.getInstance().getConfig().getDouble("xmon.sms$cost")){
                                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$no$money").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.sms$cost"))));
                            }
                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < strings.length; i++) {
                                sb.append(strings[i]).append(" ");
                            }
                            User u = UserUtils.get((Player) cs);
                            if (u.getNumber().equals(strings[0])){
                                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$your$number"));
                            }
                            if (read$Player.getInventory().contains(Material.CLAY_BRICK)){
                                if (u.getNumery().contains(strings[0])){
                                    ArrayList<String> numery = new ArrayList<>();
                                    numery.addAll(Arrays.asList(u.getNumery().split("<12345STOPKA>")));
                                    numery.stream().forEach(x -> {
                                        if (x.contains(strings[0])){
                                            kontakt = x.split(" - ")[1];
                                        }
                                    });
                                    ChatUtils.sendMessage(read$Player, Main.getInstance().getConfig().getString("xmon.sms$receiving$player").replace("{NUMBER}", u.getNumber()).replace("{MESSAGE}", sb.toString()).replace("{CONTACT}", kontakt));
                                    read$Player.playSound(read$Player.getLocation(), Sound.BLOCK_GLASS_PLACE, 100, 100);
                                    ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$success$send").replace("{NUMBER}", strings[0]).replace("{MESSAGE}", sb.toString()));
                                    Main.getEconomy().withdrawPlayer(cs.getName(), Main.getInstance().getConfig().getDouble("xmon.sms$cost"));
                                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$money$withdraw").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.sms$cost"))));
                                }else {
                                    ChatUtils.sendMessage(read$Player, Main.getInstance().getConfig().getString("xmon.sms$receiving$player").replace("{NUMBER}", u.getNumber()).replace("{MESSAGE}", sb.toString()).replace("{CONTACT}", ""));
                                    read$Player.playSound(read$Player.getLocation(), Sound.BLOCK_GLASS_PLACE, 100, 100);
                                    ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$success$send").replace("{NUMBER}", strings[0]).replace("{MESSAGE}", sb.toString()));
                                    Main.getEconomy().withdrawPlayer(cs.getName(), Main.getInstance().getConfig().getDouble("xmon.sms$cost"));
                                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$money$withdraw").replace("{COST}", String.valueOf(Main.getInstance().getConfig().getDouble("xmon.sms$cost"))));
                                }
                            }else{
                                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$receiving$player$no$phone").replace("{NUMBER}", strings[0]));
                            }
                        }else{
                            return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$receiving$player$offline").replace("{NUMBER}", strings[0]));
                        }
                    }
                } else {
                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$no$correct"));
                }
            }else{
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.sms$phone$in$hand"));
            }
        }else{
            return ChatUtils.sendMessage(cs, "&4Błąd: &cKomende może wykonywać jedynie gracz.");
        }
    }
}
