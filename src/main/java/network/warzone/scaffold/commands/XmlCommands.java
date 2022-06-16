package network.warzone.scaffold.commands;

import com.avaje.ebean.validation.NotNull;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandNumberFormatException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import network.warzone.scaffold.ScaffoldWorld;
import org.bukkit.Bukkit;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.stream.Stream;

public class XmlCommands {

    private static File folder;
    private static File xmlFile;
    private static ScaffoldWorld wrapper;
    private static final String endModule = "</map>";

    @CommandPermissions("scaffold.command.xml")
    @Command(
            aliases = "xml",
            desc = "Base command for xml file creation",
            min = 1, max = 2,
            usage = "<create|print|kit|(...)>"
    )
    public static void xml(CommandContext cmd, CommandSender sender) {
        if (!sender.hasPermission("scaffold.command.xml")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission.");
            return;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        String worldName = world.getName();
        worldName = worldName.substring(worldName.indexOf("/") + 1);
        wrapper = ScaffoldWorld.ofSearch(worldName);
        folder = wrapper.getFolder();
        xmlFile = new File(folder, wrapper.getName() + ".xml");


        System.out.println("[DEBUG] player: " + player);
        System.out.println("[DEBUG] world: " + world);
        System.out.println("[DEBUG] world.getName(): " + world.getName());
        System.out.println("[DEBUG] worldName: " + worldName);
        System.out.println("[DEBUG] wrapper: " + wrapper);
        System.out.println("[DEBUG] folder: " + folder.toString());
        System.out.println("[DEBUG] xmlFile: " + xmlFile);

        if (!wrapper.isCreated()) {
            sender.sendMessage(ChatColor.RED + "Error: World not found.");
            return;
        }

        String arg1 = cmd.getString(0);

        switch (arg1) {
            case "create":
                xmlCreate(cmd, sender);
                break;
            case "print":
                xmlPrint(cmd, sender);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid syntax. Please try again.");
                break;
        }
    }

    private static void xmlCreate(CommandContext cmd, CommandSender sender) {

        if (xmlFile.exists()) {
            sender.sendMessage(ChatColor.RED + "An xml file already exists for world \"" + wrapper.getName() + "\"");
            return;
        }

        try {
            final Yaml yaml = new Yaml();
            PrintWriter writer = new PrintWriter(xmlFile);
            writer.write("<?xml version=\"1.0\"?>" + "\n");
            writer.write("<map proto=\"1.4.0\">" + "\n");
            writer.write("  <name>" + wrapper.getName() + "</name>" + "\n");
            writer.write("  <version>1.0.0</version>" + "\n");
            writer.write("  <objective>Win the game.</objective>" + "\n");
            writer.write("</map>");
            writer.close();
            yaml.dump(xmlFile, writer);
            sender.sendMessage(ChatColor.GREEN + "Successfully created xml template!");
        }
        catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "An IOException error has occurred while writing xml file.");
            e.printStackTrace();
        }
        catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An Exception error has occurred while writing xml file.");
            e.printStackTrace();
        }
    }

    private static void xmlPrint(CommandContext cmd, CommandSender sender)  {

        if (!xmlFile.exists()) {
            sender.sendMessage(ChatColor.RED + "No xml file has been created for world \"" + wrapper.getName() + "\"");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(xmlFile));
            String output = "";
            while (reader.ready()) {
                output += reader.readLine() + "\n";
            }
            reader.close();

            System.out.println("[DEBUG] output: " + output);

            sender.sendMessage(output);
        }
        catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "An IOException error has occurred while writing xml file.");
            e.printStackTrace();
        }
        catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "An Exception error has occurred while writing xml file.");
            e.printStackTrace();
        }

    }

    private static void xmlKit(CommandContext cmd, CommandSender sender) {

        if (!xmlFile.exists()) {
            sender.sendMessage(ChatColor.RED + "No xml file has been created for world \"" + wrapper.getName() + "\"");
            return;
        }

        String arg2 = cmd.getString(1);

        if (arg2 == null) {
            sender.sendMessage(ChatColor.RED + "Too few arguments.");
            sender.sendMessage(ChatColor.RED + "/xml kit <create|delete|load>");
            return;
        }

        switch(arg2) {
            case "create":
                xmlKitCreate(cmd, sender);
                break;
            case "delete":
                break;
            case "load":
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid syntax.");
                sender.sendMessage(ChatColor.RED + "/xml kit <create|delete|load>");
        }


    }

    private static void xmlKitCreate(CommandContext cmd, CommandSender sender) {

    }

    private static boolean xmlExists(String name) {
        return false;
    }

    private static String xmlFileToString() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(xmlFile));
            String output = "";
            while (reader.ready()) {
                output += reader.readLine() + "\n";
            }
            reader.close();
            return output;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getStringIndexOfXml(String target) {

        String xmlFileString = xmlFileToString();
        if (xmlFileString == null) {
            return -1;
        }
        return xmlFileString.indexOf(target);
    }
}
