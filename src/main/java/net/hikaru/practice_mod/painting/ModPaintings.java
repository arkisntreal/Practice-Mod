package net.hikaru.practice_mod.painting;

import net.hikaru.practice_mod.PracticeMod;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPaintings {
    public static final PaintingVariant PLANT = registerPainting("plant_painting", new PaintingVariant(16, 16));
    public static final PaintingVariant WANDERER = registerPainting("wanderer_painting", new PaintingVariant(16, 32));
    public static final PaintingVariant SUNSET = registerPainting("sunset_painting", new PaintingVariant(32, 16));

    private static PaintingVariant registerPainting(String name, PaintingVariant paintingVariant) {
        return Registry.register(Registry.PAINTING_VARIANT, new Identifier(PracticeMod.MOD_ID, name), paintingVariant);
    }

    public static void registerPaintings() {
        PracticeMod.LOGGER.debug("Registering Paintings for " + PracticeMod.MOD_ID);
    }
}
