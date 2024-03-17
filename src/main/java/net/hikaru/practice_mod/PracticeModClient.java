package net.hikaru.practice_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.hikaru.practice_mod.block.ModBlocks;
import net.hikaru.practice_mod.block.entity.ModBlockEntities;
import net.hikaru.practice_mod.block.entity.client.FancyCraftingBlockEntityRenderer;
import net.hikaru.practice_mod.entity.ModEntities;
import net.hikaru.practice_mod.entity.client.ChomperRenderer;
import net.hikaru.practice_mod.event.KeyInputHandler;
import net.hikaru.practice_mod.fluid.ModFluids;
import net.hikaru.practice_mod.networking.ModMessages;
import net.hikaru.practice_mod.client.ThirstHudOverlay;
import net.hikaru.practice_mod.screen.FancyCraftingScreen;
import net.hikaru.practice_mod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class PracticeModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EGGPLANT_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOGWOOD_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOGWOOD_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUTTERCUPS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BUTTERCUPS, RenderLayer.getCutout());

        KeyInputHandler.register();

        ModMessages.registerS2CPackets();

        HudRenderCallback.EVENT.register(new ThirstHudOverlay());

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_SOAP_WATER, ModFluids.FLOWING_SOAP_WATER,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0xA1E038D0
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_SOAP_WATER, ModFluids.FLOWING_SOAP_WATER);

        HandledScreens.register(ModScreenHandlers.FANCY_CRAFTING_SCREEN_HANDLER, FancyCraftingScreen::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.FANCY_CRAFTING_TABLE, FancyCraftingBlockEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.CHOMPER, ChomperRenderer::new);
    }
}
