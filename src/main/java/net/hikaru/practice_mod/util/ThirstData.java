package net.hikaru.practice_mod.util;

import net.minecraft.nbt.NbtCompound;

public class ThirstData {
    public static int addThirst(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("thirst");
        if (thirst + amount >= 10) {
            thirst = 10;
        } else {
            thirst += amount;
        }

        nbt.putInt("thirst", thirst);

        // Sync the data here

        return thirst;
    }

    public static int removeThirst(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int thirst = nbt.getInt("thirst");
        if (thirst - amount < 0) {
            thirst = 10;
        } else {
            thirst -= amount;
        }

        nbt.putInt("thirst", thirst);

        // Sync the data here

        return thirst;
    }
}
