package net.hikaru.practice_mod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.hikaru.practice_mod.PracticeMod;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static ScreenHandlerType<FancyCraftingScreenHandler> FANCY_CRAFTING_SCREEN_HANDLER =
            new ExtendedScreenHandlerType<>(FancyCraftingScreenHandler::new);

    public static void registerAllScreenHandlers() {
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(PracticeMod.MOD_ID, "fancy_crafting"),
                FANCY_CRAFTING_SCREEN_HANDLER);
    }
}
