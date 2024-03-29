package net.hikaru.practice_mod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.networking.packet.*;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier DRINKING_ID = new Identifier(PracticeMod.MOD_ID, "drinking");
    public static final Identifier THIRST_SYNC_ID = new Identifier(PracticeMod.MOD_ID, "thirst_sync");

    public static final Identifier EXAMPLE_ID = new Identifier(PracticeMod.MOD_ID, "example");

    public static final Identifier ENERGY_SYNC = new Identifier(PracticeMod.MOD_ID, "energy_sync");
    public static final Identifier FLUID_SYNC = new Identifier(PracticeMod.MOD_ID, "fluid_sync");
    public static final Identifier ITEM_SYNC = new Identifier(PracticeMod.MOD_ID, "item_sync");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(DRINKING_ID, DrinkingC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(THIRST_SYNC_ID, ThirstSyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ENERGY_SYNC, EnergySyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(FLUID_SYNC, FluidSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
