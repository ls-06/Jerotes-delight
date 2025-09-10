package top.sealevel.jerotesdelight;

import com.google.common.base.Suppliers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.List;

public class BlockSteamedSunCrab extends FeastBlock {
  static final Lazy<Item> SUN_CRAB_LEG =
    Lazy.of(() -> ForgeRegistries.ITEMS.getValue(ResourceLocation.tryBuild(ModMain.ID,
      "sun_crab_leg")));
  static final Lazy<Item> SUN_CRAB_MEAT =
    Lazy.of(() -> ForgeRegistries.ITEMS.getValue(ResourceLocation.tryBuild(ModMain.ID,
      "sun_crab_meat")));

  public BlockSteamedSunCrab() {
    super(Properties.copy(Blocks.CAKE), Suppliers.ofInstance(null), true);
  }

  @Override
  @NotNull
  public ItemStack getServingItem(final BlockState state) {
    return ItemStack.EMPTY;
  }

  @Override
  @NotNull
  protected InteractionResult takeServing(@NotNull final LevelAccessor level,
                                          @NotNull final BlockPos pos,
                                          @NotNull final BlockState state,
                                          @NotNull final Player player,
                                          @NotNull final InteractionHand hand) {
    int servings = state.getValue(this.getServingsProperty());
    if (servings <= 0) {
      level.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
      level.destroyBlock(pos, true);
      return InteractionResult.SUCCESS;
    }

    final ItemStack serving = servings > 2
      ? SUN_CRAB_LEG.get().getDefaultInstance()
      : SUN_CRAB_MEAT.get().getDefaultInstance();


    level.setBlock(pos, state.setValue(this.getServingsProperty(), servings - 1), 3);

    if (!player.getInventory().add(serving)) {
      player.drop(serving, false);
    }

    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS,
      1.0F, 1.0F);

    return InteractionResult.SUCCESS;
  }

  @Override
  @NotNull
  public List<ItemStack> getDrops(@NotNull final BlockState pState,
                                  @NotNull final LootParams.Builder pParams) {
    final List<ItemStack> drops = super.getDrops(pState, pParams);
    drops.add(new ItemStack(Items.BOWL));
    return drops;
  }
}
