package net.hikaru.practice_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.hikaru.practice_mod.block.ModBlocks;
import net.hikaru.practice_mod.event.KeyInputHandler;
import net.hikaru.practice_mod.networking.ModMessages;
import net.minecraft.client.render.RenderLayer;

public class PracticeModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EGGPLANT_CROP, RenderLayer.getCutout());

        KeyInputHandler.register();

        ModMessages.registerS2CPackets();
    }
}
