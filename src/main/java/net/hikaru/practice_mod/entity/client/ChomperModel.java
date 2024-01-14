package net.hikaru.practice_mod.entity.client;

import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.entity.custom.ChomperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChomperModel extends AnimatedGeoModel<ChomperEntity> {
    @Override
    public Identifier getModelResource(ChomperEntity object) {
        return new Identifier(PracticeMod.MOD_ID, "geo/chomper.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChomperEntity object) {
        return new Identifier(PracticeMod.MOD_ID, "textures/entity/custom/chomper_texture.png");
    }

    @Override
    public Identifier getAnimationResource(ChomperEntity animatable) {
        return new Identifier(PracticeMod.MOD_ID, "animations/chomper.animation.json");
    }
}
