package david.signplugin;

import david.signplugin.commands.SignCommand;
import david.signplugin.commands.UnSignCommand;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveConfig();
        getCommand("sign").setExecutor(new SignCommand());
        getCommand("unsign").setExecutor(new UnSignCommand());
        if(!getConfig().contains("prefix")){
            getConfig().set("prefix", "§7[§eChange Prefix In Config§7] ");
            getConfig().set("enabled", "true");
            saveConfig();
        }
        if(getConfig() == null){
            Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + "§aSignSystem disabled.");
            return;
        }
        if(getConfig().getString("enabled").equalsIgnoreCase("false")){
            Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + "§aSignSystem disabled.");
            return;
        }
        Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + "§aSignSystem succesfully started!");
        Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() +"§eStable Build Version 1.0.0 made by David Gaidas");
        Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + "§7Shootout to my discord: syntax_os (Zebis#3696)");
        Bukkit.getServer().getConsoleSender().sendMessage(getPrefix() + "§cThis is a free-version.");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("§c[----------------------------------------------------]");
                Bukkit.broadcastMessage("§c[§e Sign-System made by David Gaidas (DC: syntax_os)§c ]");
                Bukkit.broadcastMessage("§c[§e By supporting me on discord you can get rid of this text.§c ]");
                Bukkit.broadcastMessage("§c[----------------------------------------------------]");
            }
        }, 0, (long) (20*60*60*0.5));
    }
    public static String getPrefix(){
        return SignPlugin.getPlugin(SignPlugin.class).getConfig().getString("prefix");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
