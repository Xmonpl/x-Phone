package cf.xmon.phone.listeners;

import cf.xmon.phone.object.User;
import cf.xmon.phone.utils.UserUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        UserUtils.get(e.getPlayer());
    }
}
