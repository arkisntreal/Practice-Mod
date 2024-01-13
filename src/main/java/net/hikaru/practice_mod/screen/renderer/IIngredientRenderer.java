package net.hikaru.practice_mod.screen.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Modified code from JEI (Just Enough Items)

public interface IIngredientRenderer<T> {
    default void render(MatrixStack stack, T ingredient) {
        render(stack, 0, 0, ingredient);
    }

    List<Text> getTooltip(T ingredient, TooltipContext tooltipFlag);

    default TextRenderer getFontRenderer(MinecraftClient minecraftClient, T ingredient) {
        return minecraftClient.textRenderer;
    }

    default int getWidth() {
        return 16;
    }

    default int getHeight() {
        return 16;
    }

    @Deprecated(forRemoval = true, since = "9.3.0")
    default void render(MatrixStack stack, int xPosition, int yPosition, @Nullable T ingredient) {

    }
}
