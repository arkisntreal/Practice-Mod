package net.hikaru.practice_mod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_TUTORIAL = "key.practice_mod.category.tutorial";
    public static final String KEY_DRINK_WATER = "key.practice_mod.drink.water";

    public static KeyBinding drinkingKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (drinkingKey.wasPressed()) {
                client.player.sendMessage(Text.literal("I am")
                        .formatted(Formatting.WHITE)
                        .append(Text.literal(" stupid!")
                                .formatted(Formatting.byColorIndex(11))
                        )
                );
            }
        });
    }

    public static void register() {
        drinkingKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK_WATER,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_TUTORIAL
        ));

        registerKeyInputs();
    }
}
