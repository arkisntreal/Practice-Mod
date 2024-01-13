package net.hikaru.practice_mod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {
    public static BlockEntityType<FancyCraftingBlockEntity> FANCY_CRAFTING_TABLE;

    public static void registerBlockEntities() {
        FANCY_CRAFTING_TABLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(PracticeMod.MOD_ID, "fancy_crafting_table"),
                FabricBlockEntityTypeBuilder.create(FancyCraftingBlockEntity::new,
                        ModBlocks.FANCY_CRAFTING_TABLE).build(null));

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, FANCY_CRAFTING_TABLE);
        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.fluidStorage, FANCY_CRAFTING_TABLE);
    }
}
