package com.amerith.curse.handler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import com.amerith.curse.core.environment.Mist;

/**
 * Command handler for /wrld mist [true|false]
 * Toggles the blinding mist effect (only accessible with cheats enabled).
 */
public class MistCommandHandler extends CommandBase {

    @Override
    public String getCommandName() {
        return "wrld";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/wrld mist <true|false>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        // 2 = ops only (requires cheats/operator level)
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // Check if sender has permission (cheats must be on)
        if (!sender.canUseCommand(this.getRequiredPermissionLevel(), this.getCommandName())) {
            throw new CommandException("You must have cheats enabled to use this command!");
        }

        // Validate argument count
        if (args.length < 2) {
            throw new CommandException("Usage: /wrld mist <true|false>");
        }

        // Check if first arg is "mist"
        if (!args[0].equalsIgnoreCase("mist")) {
            throw new CommandException("Unknown subcommand: " + args[0]);
        }

        // Parse true/false
        String stateStr = args[1].toLowerCase();
        boolean newState;

        if (stateStr.equals("true")) {
            newState = true;
        } else if (stateStr.equals("false")) {
            newState = false;
        } else {
            throw new CommandException("Expected true or false, got: " + args[1]);
        }

        // Update the global mist state
        Mist.mistEnabled = newState;

        // Send feedback to command executor
        String statusColor = newState ? TextFormatting.DARK_PURPLE : TextFormatting.GOLD;
        String status = newState ? "ENABLED 🌫️" : "DISABLED ☀️";
        TextComponentString feedback = new TextComponentString(
            statusColor + "[Amerith's Curse] Mist " + status
        );
        sender.sendMessage(feedback);
    }
}
