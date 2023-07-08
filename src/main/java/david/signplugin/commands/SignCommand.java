package david.signplugin.commands;

import david.signplugin.SignPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        ItemStack item = player.getInventory().getItemInHand();
        if(item.getType() == Material.AIR){
            commandSender.sendMessage(SignPlugin.getPrefix() + "§cYou have to hold an item in your main-hand.");
            return false;
        }
        ItemMeta meta_data = item.getItemMeta();
        if (meta_data.getLore() != null){
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cThe item is already signed!");
            return false;
        }
        if (!(player.hasPermission("sign.sign"))){
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cInsufficient rights! §esign.sign");
            return false;
        }
        ArrayList<String> lore = new ArrayList<>();
        String toOneString = String.join(" ", Arrays.asList(strings).toArray(new String[]{}));
        if (player.hasPermission("sign.sign.colour")) {
            toOneString = toOneString.replace("&1", "§1").replace("&2", "§2").replace("&3", "§3").replace("&4", "§4").replace("&5", "§5").replace("&6", "§6").replace("&7", "§7").replace("&8", "§8").replace("&9", "§9").replace("&a", "§a").replace("&b", "§b").replace("&c", "§c").replace("&d", "§d").replace("&e", "§e").replace("&f", "§f").replace("&k", "§k").replace("&l", "§l").replace("&m", "§m").replace("&n", "§n").replace("&o", "§o").replace("&r", "§r");
        }
        Pattern pattern2 = Pattern.compile("[a-zA-Z0-9@#$]");
        Matcher matcher = pattern2.matcher(toOneString);
        if (!matcher.find()){
            commandSender.sendMessage(SignPlugin.getPrefix() +"§cThe sign needs to contain at least one character!");
            return false;
        }
        lore.add(toOneString);
        String pattern = "dd.MM.yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        lore.add("§7Signed by " + "§a" +((Player) commandSender).getDisplayName() + " §7on " + "§e" +  dateInString);
        meta_data.setLore(lore);
        item.setItemMeta(meta_data);
        player.getInventory().removeItem(item);
        player.getInventory().setItemInMainHand(item);
        commandSender.sendMessage(SignPlugin.getPrefix() +"§aThe item has been signed!");

        return  false;
    }
}

