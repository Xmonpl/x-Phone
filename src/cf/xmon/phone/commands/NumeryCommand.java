package cf.xmon.phone.commands;

import cf.xmon.phone.Main;
import cf.xmon.phone.object.User;
import cf.xmon.phone.utils.ChatUtils;
import cf.xmon.phone.utils.UserUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumeryCommand implements CommandExecutor {
    private static int i;
    private static String remove = null;
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        if (cs instanceof Player){
            if (((Player) cs).getItemInHand().getType() == Material.CLAY_BRICK && ((Player) cs).getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.fixColor(Main.getInstance().getConfig().getString("xmon.phone$title")))) {
                if (strings.length < 1){
                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$usage"));
                }
                User u = UserUtils.get((Player) cs);
                if (strings[0].equalsIgnoreCase("dodaj")){
                    if (strings.length < 3) {
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$usage$dodaj"));
                    }
                    if (!ChatUtils.isNumeric(strings[1])) {
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$no$number"));
                    }
                    if (u.getNumery().contains(strings[1])){
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$contain$number"));
                    }
                    if (u.getNumery().split("<12345STOPKA>").length >= Main.getInstance().getConfig().getInt("xmon.ksiazka$telefoniczna$limit$kontaktow")){
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$contact$limit"));
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i < strings.length; i++) {
                        sb.append(strings[i]).append(" ");
                    }
                    if (u.getNumery().equals("null")){
                        u.setNumery(strings[1] + " - " + sb.toString() + "<12345STOPKA>");
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$save$compleat"));
                    }else{
                        String old = u.getNumery();
                        u.setNumery(old + strings[1] + " - " + sb.toString() + "<12345STOPKA>");
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$save$compleat"));
                    }
                }else if (strings[0].equalsIgnoreCase("usun")){
                    if (strings.length < 2){
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$usage$usun"));
                    }
                    i = 0;
                    remove = null;
                    ArrayList<String> numery = new ArrayList<>();
                    numery.addAll(Arrays.asList(u.getNumery().split("<12345STOPKA>")));
                    numery.forEach(x ->{
                        if (x.contains(strings[1])){
                            remove = x;
                            i = 1;
                        }
                    });
                    if (i == 0){
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$contact$no$contain"));
                    }else{
                        numery.remove(remove);
                        u.setNumery(numery.toString().replace("[", "").replace("]", ""));
                        return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$delete$compleat"));
                    }
                }else if (strings[0].equalsIgnoreCase("lista")){
                    Arrays.stream(u.getNumery().split("<12345STOPKA>")).forEach(x -> {
                        ChatUtils.sendMessage(cs, x.replace("<12345STOPKA>", "").replace("null", "Brak numerów!"));
                    });
                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$lista$stopka").replace("{SIZE}", String.valueOf(u.getNumery().split("<12345STOPKA>").length)));
                }else{
                    return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$usage"));
                }
            }else{
                return ChatUtils.sendMessage(cs, Main.getInstance().getConfig().getString("xmon.numery$phone$in$hand"));
            }
        }else{
            return ChatUtils.sendMessage(cs, "&4Błąd: &cKomende może wykonywać jedynie gracz.");
        }
    }
}
