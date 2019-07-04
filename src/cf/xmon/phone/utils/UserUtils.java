package cf.xmon.phone.utils;

import cf.xmon.phone.Main;
import cf.xmon.phone.object.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserUtils {
    private static List<User> users = new ArrayList<User>();

    public static List<User> getUsers() {
        return UserUtils.users;
    }

    public static void addUser(final User u) {
        UserUtils.users.add(u);
    }

    public static void remUser(final User u) {
        UserUtils.users.remove(u);
    }

    public static User get(final Player p) {
        return get(p.getUniqueId());
    }

    public static User get(final UUID uuid) {
        final User u = UserUtils.users.stream().filter(user -> user.getUuid().equals(uuid)).findFirst().orElse(null);
        return (u == null) ? new User(uuid) : u;
    }

    public static User getByNumber(String number){
        User u = UserUtils.users.stream().filter(user -> user.getNumber().equalsIgnoreCase(number)).findFirst().orElse(null);
        return u;
    }

    public static User get(final String name) {
        final User u = UserUtils.users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return (u == null) ? ((Bukkit.getPlayer(name) == null) ? null : new User(Bukkit.getPlayer(name))) : u;
    }

    public static void load() {
        try {
            final ResultSet rs = Main.getStore().query("SELECT * FROM `{P}users`");
            while (rs.next()) {
                final User u = new User(rs);
                UserUtils.users.add(u);
            }
            rs.close();
            Logger.info("Loaded " + UserUtils.users.size() + " players");
        }
        catch (SQLException e) {
            Logger.info("Can not load players Error " + e.getMessage());
            e.printStackTrace();
        }
    }
}
