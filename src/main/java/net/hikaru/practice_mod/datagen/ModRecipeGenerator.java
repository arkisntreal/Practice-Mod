package net.hikaru.practice_mod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hikaru.practice_mod.block.ModBlocks;
import net.hikaru.practice_mod.item.ModItems;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    ModRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, List.of(ModBlocks.FIRSTIUM_ORE), ModItems.FIRSTIUM_INGOT,
                3f, 300, "firstium");

        offerReversibleCompactingRecipes(exporter, ModItems.FIRSTIUM_INGOT, ModBlocks.FIRSTIUM_BLOCK);

        ShapedRecipeJsonBuilder.create(ModItems.D_FOUR)
                .pattern("#EE")
                .pattern("EE ")
                .input('#', Items.WHITE_WOOL)
                .input('E', ModItems.FIRSTIUM_INGOT)
                .criterion(RecipeProvider.hasItem(Items.WHITE_WOOL),
                        RecipeProvider.conditionsFromItem(Items.WHITE_WOOL))
                .criterion(RecipeProvider.hasItem(ModItems.FIRSTIUM_INGOT),
                        RecipeProvider.conditionsFromItem(ModItems.FIRSTIUM_INGOT))
                .offerTo(exporter, new Identifier(RecipeProvider.getRecipeName(ModItems.D_FOUR)));
    }
}
