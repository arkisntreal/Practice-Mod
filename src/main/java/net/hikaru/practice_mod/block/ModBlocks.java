package net.hikaru.practice_mod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.block.custom.AssemblerBlock;
import net.hikaru.practice_mod.block.custom.LampBlock;
import net.hikaru.practice_mod.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block FIRSTIUM_BLOCK = registerBlock("firstium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f)
                    .requiresTool()), ModItemGroup.PRACTICE_MOD);

    public static final Block FIRSTIUM_ORE = registerBlock("firstium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f)
                    .requiresTool(), UniformIntProvider.create(3, 7)), ModItemGroup.PRACTICE_MOD);
    public static final Block DEEPSLATE_FIRSTIUM_ORE = registerBlock("deepslate_firstium_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(3.5f)
                    .requiresTool(), UniformIntProvider.create(5, 8)), ModItemGroup.PRACTICE_MOD);

    public static final Block LAMP = registerBlock("lamp",
            new LampBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).strength(4f)
                    .luminance(state -> state.get(LampBlock.LIT) ? 15 : 0)
                    .requiresTool()), ModItemGroup.PRACTICE_MOD);
    public static final Block ASSEMBLER = registerBlock("assembler",
            new AssemblerBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).strength(4.0f)
                    .requiresTool()), ModItemGroup.PRACTICE_MOD);

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(PracticeMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(PracticeMod.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {
        PracticeMod.LOGGER.info("Registering new blocks to Minecraft from " + PracticeMod.MOD_ID);
    }
}
