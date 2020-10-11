package nl.lokerhp.utility;

import nl.lokerhp.utility.commands.SupportCMD;
import nl.lokerhp.utility.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public final class Main extends JavaPlugin {

    public String machineIP = null;
    boolean check = false;
    @Override
    public void onEnable() {
        URL whatismyip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            assert whatismyip != null;
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        try {
            String machineIP = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }        for (int i = 0; i < Config.iplist.length; i++){
            System.out.println(Config.iplist[i]);
            if(machineIP.equals(Config.iplist[i])){
                check = true;
                break;
            } else{
                check = false;
            }
        }
        System.out.println(check);
        System.out.println(machineIP);
        if(check) {
            // Plugin startup logic
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Server running on the " + ChatColor.translateAlternateColorCodes('&', Config.companyname) + ChatColor.DARK_GREEN + "infrastructure");
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "Enabled Plugin");
            getServer().getPluginManager().enablePlugin(this);
            this.getCommand("support").setExecutor(new SupportCMD(this));
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Server not running on the " + ChatColor.translateAlternateColorCodes('&', Config.companyname) + ChatColor.DARK_RED + "infrastructure");
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Disabled Plugin");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Disabled Plugin");
    }
}
