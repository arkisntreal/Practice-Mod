package net.hikaru.practice_mod;

import net.fabricmc.api.ModInitializer;

import net.hikaru.practice_mod.item.ModItems;
import net.hikaru.practice_mod.block.ModBlocks;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PracticeMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "practice_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger("practice_mod");
	public static final DamageSource PRACTICE_MOD_DAMAGE = new DamageSource("practice_mod_damage");
	public static final Identifier INTERACT_WITH_ASSEMBLER_BLOCK = new Identifier(MOD_ID, "interact_with_assembler_block");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.CUSTOM_STAT, "interact_with_assembler_block", INTERACT_WITH_ASSEMBLER_BLOCK);
		Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_ASSEMBLER_BLOCK, StatFormatter.DEFAULT);

		LOGGER.info("Hi Fabric!");

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}