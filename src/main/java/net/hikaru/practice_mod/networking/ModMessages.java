package net.hikaru.practice_mod.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hikaru.practice_mod.PracticeMod;
import net.hikaru.practice_mod.networking.packet.DrinkingC2SPacket;
import net.hikaru.practice_mod.networking.packet.ExampleC2SPacket;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier DRINKING_ID = new Identifier(PracticeMod.MOD_ID, "drinking");
    public static final Identifier THIRST_SYNC_ID = new Identifier(PracticeMod.MOD_ID, "thirst_sync");
    public static final Identifier EXAMPLE_ID = new Identifier(PracticeMod.MOD_ID, "example");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(EXAMPLE_ID, ExampleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(DRINKING_ID, DrinkingC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}