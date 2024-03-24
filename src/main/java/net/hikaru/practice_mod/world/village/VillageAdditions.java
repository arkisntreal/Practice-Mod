package net.hikaru.practice_mod.world.village;

import fzzyhmstrs.structurized_reborn.impl.FabricStructurePoolRegistry;
import net.hikaru.practice_mod.PracticeMod;
import net.minecraft.util.Identifier;

public class VillageAdditions {
    public static void registerNewVillageStructures() {
        FabricStructurePoolRegistry.registerSimple(
                new Identifier("minecraft:village/plains/houses"),
                new Identifier(PracticeMod.MOD_ID, "plains_assembler"),
                150
        );
    }
}
