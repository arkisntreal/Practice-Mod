package net.hikaru.practice_mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.hikaru.practice_mod.util.IEntityDataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReturnHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("home")
                .then(CommandManager.literal("return")
                        .executes(ReturnHomeCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) {
        IEntityDataSaver player = (IEntityDataSaver)context.getSource().getPlayer();

        int[] homepos = player.getPersistentData().getIntArray("homepos");

        if (homepos.length != 0) {
            int[] playerPos = player.getPersistentData().getIntArray("homepos");
            context.getSource().getPlayer().requestTeleport(playerPos[0], playerPos[1], playerPos[2]);

            context.getSource().sendFeedback(Text.translatable("command.home.feedback"), true);
            return 1;
        }
        else {
            context.getSource().sendFeedback(Text.translatable("command.home.negative"), false);
            return -1;
        }
    }
}
