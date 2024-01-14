package net.hikaru.practice_mod.entity.client;

import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.entity.custom.ChomperEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChomperRenderer extends GeoEntityRenderer<ChomperEntity> {
    public ChomperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChomperModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureResource(ChomperEntity animatable) {
        return new Identifier(PracticeMod.MOD_ID, "textures/entity/custom/chomper_texture.png");
    }

    @Override
    public RenderLayer getRenderType(ChomperEntity animatable, float partialTick, MatrixStack poseStack,
                                     VertexConsumerProvider bufferSource, VertexConsumer buffer,
                                     int packedLight, Identifier texture) {
        poseStack.scale(0.8f, 0.8f, 0.8f);

        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
