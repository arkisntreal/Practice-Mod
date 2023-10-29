package net.hikaru.practice_mod.world.feature;

import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.block.ModBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {
    public static final List<OreFeatureConfig.Target> OVERWORLD_FIRSTIUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FIRSTIUM_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_FIRSTIUM_ORE.getDefaultState()));

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> FIRSTIUM_ORE =
            ConfiguredFeatures.register("firstium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_FIRSTIUM_ORES, 28));

    public static void registerConfiguredFeatures() {
        PracticeMod.LOGGER.debug("Registering the ModConfiguredFeatures for " + PracticeMod.MOD_ID);
    }
}
