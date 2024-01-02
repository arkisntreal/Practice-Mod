package net.hikaru.practice_mod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<FancyCraftingScreenHandler> FANCY_CRAFTING_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        FANCY_CRAFTING_SCREEN_HANDLER = new ScreenHandlerType<>(FancyCraftingScreenHandler::new);
    }
}
