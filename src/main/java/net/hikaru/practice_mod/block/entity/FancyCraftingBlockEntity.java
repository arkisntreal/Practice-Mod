package net.hikaru.practice_mod.block.entity;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.hikaru.practice_mod.block.custom.FancyCraftingTableBlock;
import net.hikaru.practice_mod.fluid.ModFluids;
import net.hikaru.practice_mod.item.ModItems;
import net.hikaru.practice_mod.networking.ModMessages;
import net.hikaru.practice_mod.recipe.FancyRecipe;
import net.hikaru.practice_mod.screen.FancyCraftingScreenHandler;
import net.hikaru.practice_mod.util.FluidStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Optional;

public class FancyCraftingBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(30000, 32, 32) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            if (!world.isClient()) {
                sendEnergyPacket();
            }
        }
    };

    private void sendEnergyPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        data.writeLong(energyStorage.amount);
        data.writeBlockPos(getPos());

        for(ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.ENERGY_SYNC, data);
        }
    }

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<FluidVariant>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20; // 20k mb
        }

        @Override
        protected void onFinalCommit() {
            markDirty();

            if (!world.isClient()) {
                sendFluidPacket();
            }
        }
    };

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public FancyCraftingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FANCY_CRAFTING_TABLE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return FancyCraftingBlockEntity.this.progress;
                    case 1: return FancyCraftingBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: FancyCraftingBlockEntity.this.progress = value; break;
                    case 1: FancyCraftingBlockEntity.this.maxProgress = value; break;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    private void sendFluidPacket() {
        PacketByteBuf data = PacketByteBufs.create();
        fluidStorage.variant.toPacket(data);
        data.writeLong(fluidStorage.amount);
        data.writeBlockPos(getPos());

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
            ServerPlayNetworking.send(player, ModMessages.FLUID_SYNC, data);
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Fancy Crafting Table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        sendEnergyPacket();
        sendFluidPacket();

        return new FancyCraftingScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("fancy_crafting_table.progress", progress);
        nbt.putLong("fancy_crafting_table.energy", energyStorage.amount);
        nbt.put("fancy_crafting_table.variant", fluidStorage.variant.toNbt());
        nbt.putLong("fancy_crafting_table.fluid", fluidStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("fancy_crafting_table.progress");
        energyStorage.amount = nbt.getLong("fancy_crafting_table.energy");
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound)nbt.get("fancy_crafting_table.variant"));
        fluidStorage.amount = nbt.getLong("fancy_crafting_table.fluid");
    }

    private void resetProgress() {
        this.progress = 0;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(FancyCraftingTableBlock.FACING);

        if (side == Direction.UP || side == Direction.DOWN) {
            return false;
        }

        return switch (localDir) {
            default ->
                side.getOpposite() == Direction.NORTH && slot == 1 ||
                        side.getOpposite() == Direction.EAST && slot == 1 ||
                        side.getOpposite() == Direction.WEST && slot == 0;
            case EAST ->
                    side.rotateYClockwise() == Direction.NORTH && slot == 1 ||
                            side.rotateYClockwise() == Direction.EAST && slot == 1 ||
                            side.rotateYClockwise() == Direction.WEST && slot == 0;
            case SOUTH ->
                    side == Direction.NORTH && slot == 1 ||
                            side == Direction.EAST && slot == 1 ||
                            side == Direction.WEST && slot == 0;
            case WEST ->
                    side.rotateYCounterclockwise() == Direction.NORTH && slot == 1 ||
                            side.rotateYCounterclockwise() == Direction.EAST && slot == 1 ||
                            side.rotateYCounterclockwise() == Direction.WEST && slot == 0;
        };
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(FancyCraftingTableBlock.FACING);

        if(side == Direction.UP) {
            return false;
        }

        if(side == Direction.DOWN) {
            return slot == 2;
        }

        return switch (localDir) {
            default -> side.getOpposite() == Direction.SOUTH && slot == 2 ||
                    side.getOpposite() == Direction.EAST && slot == 2;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYClockwise() == Direction.EAST && slot == 2;
            case SOUTH -> side == Direction.SOUTH && slot == 2 ||
                    side == Direction.EAST && slot == 2;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == 2 ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == 2;
        };
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, FancyCraftingBlockEntity entity) {
        if(world.isClient()) {
            return;
        }

        if (hasEnergyItem(entity)) {
            try (Transaction transaction = Transaction.openOuter()) {
                entity.energyStorage.insert(16, transaction);
                transaction.commit();
            }
        }

        if(hasRecipe(entity) && hasEnoughEnergy(entity) && hasEnoughFluid(entity)) {
            entity.progress++;
            extractEnergy(entity);
            extractFluid(entity);
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }

        if (hasFluidSourceInSlot(entity)) {
            transferFluidToFluidStorage(entity);
        }
    }

    private static void transferFluidToFluidStorage(FancyCraftingBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
            entity.fluidStorage.insert(FluidVariant.of(ModFluids.STILL_SOAP_WATER),
                    FluidStack.convertDropletsToMb(FluidConstants.BUCKET), transaction);
            transaction.commit();
            entity.setStack(0, new ItemStack(Items.BUCKET));
        }
    }

    private static boolean hasFluidSourceInSlot(FancyCraftingBlockEntity entity) {
        return entity.getStack(0).getItem() == ModFluids.SOAP_WATER_BUCKET;
    }

    private static void extractFluid(FancyCraftingBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
                entity.fluidStorage.extract(FluidVariant.of(ModFluids.STILL_SOAP_WATER), 2, transaction);
            transaction.commit();
        }
    }

    private static boolean hasEnoughFluid(FancyCraftingBlockEntity entity) {
        return entity.fluidStorage.amount >= 500;
    }

    private static void extractEnergy(FancyCraftingBlockEntity entity) {
        try (Transaction transaction = Transaction.openOuter()) {
            entity.energyStorage.extract(32, transaction);
            transaction.commit();
        }
    }

    private static boolean hasEnoughEnergy(FancyCraftingBlockEntity entity) {
        return entity.energyStorage.amount >= 32;
    }

    private static boolean hasEnergyItem(FancyCraftingBlockEntity entity) {
        return entity.getStack(0).getItem() == ModItems.RAW_FIRSTIUM;
    }

    private static void craftItem(FancyCraftingBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<FancyRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(FancyRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if(hasRecipe(entity)) {
            entity.removeStack(1, 1);

            entity.setStack(2, new ItemStack(recipe.get().getOutput().getItem(),
                    entity.getStack(2).getCount() + 1));

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(FancyCraftingBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<FancyRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(FancyRecipe.Type.INSTANCE, inventory, entity.getWorld());

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    public void setEnergyLevel(long energyLevel) {
        this.energyStorage.amount = energyLevel;
    }

    public void setFluidLevel(FluidVariant variant, long fluidLevel) {
        this.fluidStorage.variant = variant;
        this.fluidStorage.amount = fluidLevel;
    }
}
