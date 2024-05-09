package network.warzone.scaffold;

import com.google.common.base.Preconditions;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import network.warzone.scaffold.commands.ScaffoldCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Scaffold extends JavaPlugin implements TabCompleter {

    private static Scaffold instance;
    public static Scaffold get() {
        return instance;
    }

    private CommandsManager<CommandSender> commands;
    private Map<ScaffoldWorld, Long> locked = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();

        //TODO Disabled large number warning for now but add back later with some changes
        //getServer().getPluginManager().registerEvents(new ScaffoldListener(), this);

        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };

        CommandsManagerRegistration cmds = new CommandsManagerRegistration(this, this.commands);
        cmds.register(ScaffoldCommands.class);
        //cmds.register(XmlCommands.class); WIP XML COMMANDS

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (ScaffoldWorld wrapper : locked.keySet()) {
                if (wrapper.isOpen())
                    wrapper.getWorld().get().setFullTime(locked.get(wrapper));
            }
        }, 1, 20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See server logs.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public void sync(Runnable runnable) {
        getServer().getScheduler().runTask(this, runnable);
    }

    public void async(Runnable runnable) {
        getServer().getScheduler().runTaskAsynchronously(this, runnable);
    }

    public boolean toggleLock(ScaffoldWorld wrapper) {
        Preconditions.checkArgument(wrapper.isOpen(), "World not open.");
        Iterator<ScaffoldWorld> iterator = this.locked.keySet().iterator();
        while (iterator.hasNext()) {
            ScaffoldWorld next = iterator.next();
            if (next.getName().equals(wrapper.getName())) {
                iterator.remove();
                return false;
            }
        }
        locked.put(wrapper, wrapper.getWorld().get().getFullTime());
        return true;
    }

    public void setupConfig() {
        try {
            String path = "plugins/Scaffold/config.properties"; // Path where the file will be stored
            File configFile = new File(path);

            // Check if the file already exists
            if (!configFile.exists()) {
                // Ensure the directory structure is there
                configFile.getParentFile().mkdirs();
                // Create the file
                configFile.createNewFile();

                // Write default properties to the file
                try (FileWriter writer = new FileWriter(configFile)) {
                    writer.write("fileio_username=defaultUsername\n");
                    writer.write("fileio_password=defaultPassword\n");
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to the config file.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not create the config.properties file.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
