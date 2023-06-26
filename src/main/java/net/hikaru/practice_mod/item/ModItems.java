package net.hikaru.practice_mod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.item.custom.DiceItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item RAW_FIRSTIUM = registerItem("raw_firstium",
            new Item(new FabricItemSettings().group(ModItemGroup.PRACTICE_MOD)));
    public static final Item FIRSTIUM_INGOT = registerItem("firstium_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.PRACTICE_MOD)));
    public static final Item FIRST_CORE = registerItem("first_core",
            new Item(new FabricItemSettings().group(ModItemGroup.PRACTICE_MOD)));

    public static final Item D_FOUR = registerItem("d_four",
            new DiceItem(new FabricItemSettings().group(ModItemGroup.PRACTICE_MOD).maxCount(1), 4));
    public static final Item D_SIX = registerItem("d_six",
            new DiceItem(new FabricItemSettings().group(ModItemGroup.PRACTICE_MOD).maxCount(1), 6));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(PracticeMod.MOD_ID, name), item);
    }
    public static void registerModItems() {
        PracticeMod.LOGGER.info("Registering new Item from " + PracticeMod.MOD_ID + " to your minecraft");
    }
}
