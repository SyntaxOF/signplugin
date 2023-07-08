package david.signplugin.commands;

import david.signplugin.SignPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnSignCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        ItemStack item = player.getInventory().getItemInHand();
        if(item.getType() == Material.AIR){
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cYou have to hold an item in your main-hand.");
            return false;
        }
        ItemMeta meta_data = item.getItemMeta();
        if (meta_data.getLore() == null){
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cThe item has been signed!");
            return false;
        }
        if (!(player.hasPermission("sign.unsign"))){
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cInsufficient rights §esign.unsign");
            return false;
        }
        String lore_owner = meta_data.getLore().get(1);
        Pattern pattern = Pattern.compile("§a(.*?)§");
        Matcher matcher = pattern.matcher(lore_owner);
        if (!(matcher.find())){
            commandSender.sendMessage(SignPlugin.getPrefix() + "§c§lAn intern error has occured :/");
            return false;
        }
        String name = matcher.group(1);
        String player_name = player.getDisplayName();
        name = name.replaceAll("\\s", "");
        if (name.contentEquals(player_name)){
            List<String> lore = meta_data.getLore();
            lore.remove(1);
            lore.remove(0);
            meta_data.setLore(lore);
            item.setItemMeta(meta_data);
            player.getInventory().removeItem(item);
            player.getInventory().setItemInMainHand(item);
            commandSender.sendMessage(SignPlugin.getPrefix() +"§aThe item has been unsigned!");
        }else{
            Bukkit.getConsoleSender().sendMessage(player_name);
            Bukkit.getConsoleSender().sendMessage(name);
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cYou are not the owner of this sign.");
            return false;
        }
        return false;

    }
}
