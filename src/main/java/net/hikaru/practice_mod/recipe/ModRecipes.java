package net.hikaru.practice_mod.recipe;

import net.hikaru.practice_mod.PracticeMod;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void registerModRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(PracticeMod.MOD_ID, FancyRecipe.Serializer.ID),
                FancyRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(PracticeMod.MOD_ID, FancyRecipe.Type.ID),
                FancyRecipe.Type.INSTANCE);
    }
}
