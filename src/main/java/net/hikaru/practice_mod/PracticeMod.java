package net.hikaru.practice_mod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.hikaru.practice_mod.block.entity.ModBlockEntities;
import net.hikaru.practice_mod.command.ModCommands;
import net.hikaru.practice_mod.entity.ModEntities;
import net.hikaru.practice_mod.entity.custom.ChomperEntity;
import net.hikaru.practice_mod.event.AttackEntityHandler;
import net.hikaru.practice_mod.event.ModPlayerEventCopyFrom;
import net.hikaru.practice_mod.event.PlayerTickHandler;
import net.hikaru.practice_mod.fluid.ModFluids;
import net.hikaru.practice_mod.item.ModItems;
import net.hikaru.practice_mod.block.ModBlocks;
import net.hikaru.practice_mod.networking.ModMessages;
import net.hikaru.practice_mod.painting.ModPaintings;
import net.hikaru.practice_mod.recipe.ModRecipes;
import net.hikaru.practice_mod.screen.ModScreenHandlers;
import net.hikaru.practice_mod.util.ModLootTableModifiers;
import net.hikaru.practice_mod.villager.ModVillagers;
import net.hikaru.practice_mod.world.feature.ModConfiguredFeatures;
import net.hikaru.practice_mod.world.gen.ModOreGeneration;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

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
		ModConfiguredFeatures.registerConfiguredFeatures();

		Registry.register(Registry.CUSTOM_STAT, "interact_with_assembler_block", INTERACT_WITH_ASSEMBLER_BLOCK);
		Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_ASSEMBLER_BLOCK, StatFormatter.DEFAULT);
		LOGGER.info("Hi Fabric!");

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModFluids.registerModFluids();
		ModPaintings.registerPaintings();

		ModVillagers.registerVillagers();
		ModVillagers.registerTrades();

		ModLootTableModifiers.modifyLootTables();
		ModOreGeneration.generateOres();

		ModMessages.registerC2SPackets();

		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerAllScreenHandlers();

		ModRecipes.registerModRecipes();

		FabricDefaultAttributeRegistry.register(ModEntities.CHOMPER, ChomperEntity.setAttributes());
		GeckoLib.initialize();

		ModCommands.registerCommands();
		ServerPlayerEvents.COPY_FROM.register(new ModPlayerEventCopyFrom());

		AttackEntityCallback.EVENT.register(new AttackEntityHandler());
	}
}