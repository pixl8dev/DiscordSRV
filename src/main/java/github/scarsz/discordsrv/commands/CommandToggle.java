/*
 * DiscordSRV - https://github.com/DiscordSRV/DiscordSRV
 *
 * Copyright (C) 2016 - 2024 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

package github.scarsz.discordsrv.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.LangUtil;
import github.scarsz.discordsrv.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandToggle {

    @Command(commandNames = { "dctoggle" },
            helpMessage = "Toggles whether you receive Discord chat messages",
            permission = "discordsrv.toggle",
            usageExample = "dctoggle"
    )
    public static void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return;
        }

        Player player = (Player) sender;
        UUID playerUuid = player.getUniqueId();
        
        // Get the current toggle state from the config
        boolean isEnabled = DiscordSRV.getPlugin().getConfig().getBoolean("Players." + playerUuid + ".DiscordChatEnabled", true);
        
        // Toggle the state
        boolean newState = !isEnabled;
        DiscordSRV.getPlugin().getConfig().set("Players." + playerUuid + ".DiscordChatEnabled", newState);
        DiscordSRV.getPlugin().saveConfig();
        
        // Send confirmation message
        String message = newState ? 
            LangUtil.Message.DISCORD_CHAT_ENABLED.toString() : 
            LangUtil.Message.DISCORD_CHAT_DISABLED.toString();
        player.sendMessage(ChatColor.GREEN + message);
    }
} 