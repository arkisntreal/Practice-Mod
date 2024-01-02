package net.hikaru.practice_mod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<FancyCraftingBlockEntity> FANCY_CRAFTING_TABLE;

    public static void registerBlockEntities() {
        FANCY_CRAFTING_TABLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(PracticeMod.MOD_ID, "fancy_crafting_table"),
                FabricBlockEntityTypeBuilder.create(FancyCraftingBlockEntity::new,
                        ModBlocks.FANCY_CRAFTING_TABLE).build(null));
    }
}
