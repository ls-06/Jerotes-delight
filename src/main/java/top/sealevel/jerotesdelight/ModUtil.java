package top.sealevel.jerotesdelight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ModUtil {
  /**
   * Create the empty item recorder, just for push into ITEMS and make the creative tabs.
   */
  static ItemRecorder createEmptyRecorder(final ResourceLocation id) {
    return new ItemRecorder(id, "", "", 0, 0.0f,
      1, false, null, null);
  }

  /**
   * Get the data by lazy and hold by optional.
   *
   * @param registry the forge registry key
   * @param name     the data's RL name in string
   */
  static <T> Lazy<Optional<T>> getLazyOptData(final IForgeRegistry<T> registry, final String name) {
    return Lazy.of(() -> Optional.ofNullable(registry.getValue(ResourceLocation.tryParse(name))));
  }

  /**
   * Create an item entity to target.
   *
   * @param item   the target item include
   * @param living the target living entity
   * @return an item entity that pos is the target living entity's pos
   */
  static ItemEntity createItemEntity(final ItemStack item, final LivingEntity living) {
    return new ItemEntity(living.level(), living.getX(), living.getY(), living.getZ(), item);
  }

  /**
   * Get the enchantment of looting by player weapon.
   *
   * @param player the target player
   * @return the level of player's weapon enchantment of looting
   */
  static int getPlayerLooting(Player player) {
    final ItemStack itemInHold = player.getMainHandItem();
    if (itemInHold.isEnchanted()) {
      return itemInHold.getEnchantmentLevel(Enchantments.MOB_LOOTING);
    }
    return 0;
  }

  /**
   * The food building method usually called by {@code createItemByRecorder }.
   *
   * @param hunger     the nutrition for the food.
   * @param saturation the saturation for the food.
   * @param drinks     if the food's use anim is drinking, true this.
   * @param maxStack   the item's max stack to.
   * @param effects    the effects will be giving to player when he has eaten the food.
   * @return the food we create.
   */
  static Item createFood(final int hunger, final float saturation, final boolean drinks,
                         final int maxStack, @Nullable final ResourceLocation crafting,
                         final Set<Lazy<MobEffectInstance>> effects) {
    final var builder = new FoodProperties.Builder();
    builder.nutrition(hunger).saturationMod(saturation);
    return new Item(new Item.Properties().food(builder.build()).stacksTo(maxStack)) {
      Lazy<Item> craftingRemaining;

      @Nonnull
      @Override
      public UseAnim getUseAnimation(@Nonnull final ItemStack pStack) {
        return drinks ? UseAnim.DRINK : UseAnim.EAT;
      }

      @Nonnull
      @Override
      public ItemStack finishUsingItem(@Nonnull final ItemStack pStack, @Nonnull final Level pLevel,
                                       @Nonnull final LivingEntity pLivingEntity) {
        effects.stream()
          .map(Lazy::get)
          .map(MobEffectInstance::new)
          .forEach(pLivingEntity::addEffect);

        if (pLivingEntity instanceof Player player && this.hasCraftingRemainingItem(pStack)) {
          final ItemStack item = this.getCraftingRemainingItem(pStack);
          return item;
        }

        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
      }

      @Override
      public boolean hasCraftingRemainingItem(ItemStack stack) {
        return Objects.nonNull(crafting);
      }

      @Override
      public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        if (Objects.isNull(crafting)) {
          return ItemStack.EMPTY;
        }

        if (Objects.isNull(craftingRemaining)) {
          craftingRemaining = Lazy.of(() -> ForgeRegistries.ITEMS.getValue(crafting));
        }

        return craftingRemaining.get().getDefaultInstance();
      }
    };
  }
}
