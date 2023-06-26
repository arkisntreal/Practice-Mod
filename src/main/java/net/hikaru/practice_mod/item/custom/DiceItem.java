package net.hikaru.practice_mod.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiceItem extends Item {
    private int diceSides;

    public DiceItem (Settings settings, Integer diceSideNumber) {
        super(settings);
        this.diceSides = diceSideNumber;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.practice_mod.diceitem.extended_tooltip").formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable("item.practice_mod.diceitem.tooltip").formatted(Formatting.DARK_GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            // Create random integer in range(0, n), n is number of dice sides
            // Output randomized integer in Chat Message
            // Add small cooldown
            outputDiceNumber(user);
            user.getItemCooldownManager().set(this, 5);
        }

        return super.use(world, user, hand);
    }

    private void outputDiceNumber(PlayerEntity player) {
        player.sendMessage(Text.literal("You rolled a " + getDiceNumber(diceSides)));
    }

    private int getDiceNumber(int n) {
        return Random.createLocal().nextBetween(1, n);
    }
}
