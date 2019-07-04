package cf.xmon.phone.object;

import cf.xmon.phone.Main;
import cf.xmon.phone.utils.UserUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
    private UUID uuid;
    private String name;
    private String number;
    private String numery;

    public User(final Player p) {
        new User(p.getUniqueId());
    }

    public User(final UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(uuid).getName();
        this.number = "null";
        this.numery = "null";
        UserUtils.addUser(this);
        this.insert();
    }

    public User(final ResultSet rs) throws SQLException {
        this.uuid = UUID.fromString(rs.getString("uuid"));
        this.name = rs.getString("name");
        this.number = rs.getString("number");
        this.numery = rs.getString("numery");
        UserUtils.addUser(this);
    }
    public String getName() {

        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getNumber(){ return this.number;}
    public void setNumber(String number) {
        this.number = number;
        Main.getStore().update(false, "UPDATE `{P}users` SET `number`='" + this.getNumber() + "' WHERE `name`='" + this.getName() + "'");
    }
    public String getNumery() { return this.numery;}
    public void setNumery(String numery){
        this.numery = numery;
        Main.getStore().update(false, "UPDATE `{P}users` SET `numery`='" + this.getNumery() + "' WHERE `name`='" + this.getName() +"'");
    }
    private void insert() {
        Main.getStore().update(false, "INSERT INTO `{P}users`(`id`, `name`, `uuid`, `number`, `numery`) VALUES (NULL, '"
                + this.getName() + "','" + this.getUuid() + "','" + this.getNumber() + "','" + this.getNumery() + "')");
    }
}
