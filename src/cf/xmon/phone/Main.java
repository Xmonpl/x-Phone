package cf.xmon.phone;

import cf.xmon.phone.commands.*;
import cf.xmon.phone.database.Store;
import cf.xmon.phone.database.StoreMode;
import cf.xmon.phone.database.StoreMySQL;
import cf.xmon.phone.database.StoreSQLITE;
import cf.xmon.phone.listeners.JoinListener;
import cf.xmon.phone.listeners.iDreamShopListener;
import cf.xmon.phone.utils.UserUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private static Store store;
    private static Economy economy;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerDatabase();
        LoadCommand();
        LoadListeners();
        UserUtils.load();
        setupEconomy();
        System.out.println("x-Phone by Xmon | Wczytano");
    }

    @Override
    public void onLoad() {
        System.out.println("x-Phone by Xmon | Trwa wczytywanie.");
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
        System.out.println("x-Phone by Xmon | Trwa wyłączanie.");
    }

    public static Main getInstance(){
        if (instance != null){
            return instance;
        }
        return null;
    }
    public static Economy getEconomy(){
        return economy;
    }
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    protected boolean registerDatabase() {
        switch (StoreMode.getByName(this.getConfig().getString("xmon.database$mode"))) {
            case MYSQL: {
                Main.store = new StoreMySQL(this.getConfig().getString("xmon.database$host"), this.getConfig().getInt("xmon.database$port"), this.getConfig().getString("xmon.database$user"), this.getConfig().getString("xmon.database$pass"), this.getConfig().getString("xmon.database$name"), this.getConfig().getString("xmon.database$tableprefix"));
                break;
            }
            case SQLITE: {
                Main.store = new StoreSQLITE("phone-xmon.db", this.getConfig().getString("xmon.database$tableprefix"));
                break;
            }
            default: {
                System.err.println("Value of databse mode is not valid! Using SQLITE as database!");
                Main.store = new StoreSQLITE("phone-xmon.db", this.getConfig().getString("xmon.database$tableprefix"));
                break;
            }
        }
        final boolean conn = Main.store.connect();
        if (conn) {
            Main.store.update(true, "CREATE TABLE IF NOT EXISTS `{P}users` (" + ((Main.store.getStoreMode() == StoreMode.MYSQL) ? "`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ") + "`name` varchar(32) NOT NULL, `uuid` varchar(32) NOT NULL, `number` varchar(32) NOT NULL, `numery` varchar(655535) NOT NULL);");
            return conn;
        }
        return conn;
    }
    public static Store getStore() {
        return store;
    }

    private void LoadCommand(){
        getCommand("998").setExecutor(new NineNineEightCommand());
        getCommand("997").setExecutor(new NineNineSevenCommand());
        getCommand("999").setExecutor(new NineNineNineCommand());
        getCommand("sms").setExecutor(new SMSCommand());
        //getCommand("idreamadmin").setExecutor(new iDreamCommand());
        getCommand("numery").setExecutor(new NumeryCommand());
        getCommand("anon").setExecutor(new AnonCommand());
    }

    private void LoadListeners(){
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new iDreamShopListener(), this);

    }
}
