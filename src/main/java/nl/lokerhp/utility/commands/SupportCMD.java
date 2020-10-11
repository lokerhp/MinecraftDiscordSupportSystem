package nl.lokerhp.utility.commands;

import nl.lokerhp.utility.Main;
import nl.lokerhp.utility.util.Config;
import nl.lokerhp.utility.util.DiscordWebhookUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class SupportCMD implements CommandExecutor {

    Main main;

    public SupportCMD(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(player.hasPermission("support.request")){
            if(args.length == 0){
                player.sendMessage(ChatColor.DARK_RED + Config.nomessage);
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }

            String message = sb.toString().trim();
            DiscordWebhookUtil webhook = new DiscordWebhookUtil(Config.WebHookUrl);
            webhook.setContent("@here");
            webhook.setAvatarUrl(Config.Logo);
            webhook.addEmbed(new DiscordWebhookUtil.EmbedObject()
                    .setTitle("Server hulp request!")
                    .setDescription(message)
                    .addField("By:", player.getDisplayName(), false)
                    .addField("IP:", main.machineIP + ":" + main.getServer().getPort(), false)
                    .setFooter("Support Message", Config.Logo));
        try {
                webhook.execute(); //Handle exception
                player.sendMessage(ChatColor.DARK_GREEN + Config.supportSended);
            return true;

        } catch (IOException e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.DARK_RED + Config.ERROR);
            return true;
            }
        } else{
            player.sendMessage(ChatColor.DARK_RED + Config.noPerm);
            return true;
        }
    }


}
