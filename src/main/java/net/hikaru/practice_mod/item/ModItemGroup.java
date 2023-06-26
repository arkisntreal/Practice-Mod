package net.hikaru.practice_mod.item;

import net.hikaru.practice_mod.PracticeMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup PRACTICE_MOD = FabricItemGroupBuilder.build(
            new Identifier(PracticeMod.MOD_ID, "practice_mod_group"), () -> new ItemStack(ModItems.FIRST_CORE));
}
