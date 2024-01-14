package net.hikaru.practice_mod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.hikaru.practice_mod.block.entity.FancyCraftingBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ItemStackSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender sender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos position = buf.readBlockPos();

        if (client.world != null && client.world.getBlockEntity(position) instanceof FancyCraftingBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
    }
}
