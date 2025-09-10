package top.sealevel.jerotesdelight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

public final class ModEvent {
  private static final Lazy<Optional<Block>> BLACK_PUTTY_BUSH =
    ModUtil.getLazyOptData(ForgeRegistries.BLOCKS, "jerotesvillage:black_pretty_bush");
  private static final Lazy<Item> BLACK_PUTTY_FRUIT = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:black_pretty_fruit")));

  private static final Lazy<Optional<EntityType<?>>> FROST_YETI =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:frost_yeti");
  private static final Lazy<Item> FROST_YETI_BRAIN = Lazy.of(() -> ForgeRegistries.ITEMS.getValue(
    ResourceLocation.tryParse("jerotesdelight:frost_yeti_brain")));

  private static final Lazy<Optional<EntityType<?>>> RUSHER =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:rusher");
  private static final Lazy<Item> RUSHER_MEAT = Lazy.of(() -> ForgeRegistries.ITEMS.getValue(
    ResourceLocation.tryParse("jerotesdelight:rusher_meat")));

  private static final Lazy<Optional<EntityType<?>>> BLUE_SHIMMER_SQUID =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:blue_shimmer_squid");
  private static final Lazy<Item> RAW_BLUE_SHIMMER_SQUID = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:raw_blue_shimmer_squid")));

  private static final Lazy<Optional<EntityType<?>>> THORNY_TORTOISE =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:thorny_tortoise");
  private static final Lazy<Item> RAW_THORNY_TORTOISE = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:raw_thorny_tortoise")));

  private static final Lazy<Optional<EntityType<?>>> SCALY_BEAR =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:scaly_bear");
  private static final Lazy<Item> SCALY_BEAR_MEAT = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:scaly_bear_meat")));

  private static final Lazy<Optional<EntityType<?>>> FLUFFMOUND =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:fluffmound");
  private static final Lazy<Item> FLUFFMOUND_MEAT = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:fluffmound_meat")));

  private static final Lazy<Optional<EntityType<?>>> ARCHNOSED_HORNBEAST =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:archnosed_hornbeast");
  private static final Lazy<Item> ARCHNOSED_HORNBEAST_MEAT = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:archnosed_hornbeast_meat")));

  private static final Lazy<Optional<EntityType<?>>> BEACH_TERROR_BIRD =
    ModUtil.getLazyOptData(ForgeRegistries.ENTITY_TYPES, "jerotesvillage:beach_terror_bird");
  private static final Lazy<Item> BEACH_TERROR_BIRD_MEAT = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:beach_terror_bird_meat")));
  private static final Lazy<Item> BEACH_TERROR_BIRD_LEG = Lazy.of(() -> ForgeRegistries.ITEMS
    .getValue(ResourceLocation.tryParse("jerotesdelight:beach_terror_bird_leg")));

  public static ModEvent INSTANCE = new ModEvent();

  private ModEvent() {
  }

  private static boolean handleFrostYetiDrops(final LivingDropsEvent event,
                                              final LivingEntity entity) {
    if (FROST_YETI.get().isEmpty()) {
      return false;
    }

    if (!entity.getType().equals(FROST_YETI.get().get())) {
      return false;
    }

    if (entity.level().getRandom().nextBoolean()) {
      final ItemEntity itemEntity =
        ModUtil.createItemEntity(FROST_YETI_BRAIN.get().getDefaultInstance(), entity);
      event.getDrops().add(itemEntity);
    }

    return true;
  }


  private static boolean handleBlueShimmerSquid(final LivingDropsEvent event,
                                                final LivingEntity entity) {
    if (BLUE_SHIMMER_SQUID.get().isEmpty()) {
      return false;
    }

    if (!entity.getType().equals(BLUE_SHIMMER_SQUID.get().get())) {
      return false;
    }

    final ItemEntity itemEntity =
      ModUtil.createItemEntity(RAW_BLUE_SHIMMER_SQUID.get().getDefaultInstance(), entity);
    event.getDrops().add(itemEntity);

    return true;
  }

  private static boolean handleBeachTerrorBird(final LivingDropsEvent event,
                                               final LivingEntity entity) {
    if (BEACH_TERROR_BIRD.get().isEmpty()) {
      return false;
    }

    if (!entity.getType().equals(BEACH_TERROR_BIRD.get().get())) {
      return false;
    }

    event.getDrops().add(ModUtil.createItemEntity(
      new ItemStack(BEACH_TERROR_BIRD_MEAT.get()), entity));
    event.getDrops().add(ModUtil.createItemEntity(
      new ItemStack(BEACH_TERROR_BIRD_LEG.get(), 2), entity));

    return true;
  }

  private static boolean handleThornyTortoise(final LivingDropsEvent event,
                                              final LivingEntity entity) {
    if (THORNY_TORTOISE.get().isEmpty()) {
      return false;
    }

    if (!entity.getType().equals(THORNY_TORTOISE.get().get())) {
      return false;
    }

    if (!entity.isBaby()) {
      return false;
    }

    final ItemEntity itemEntity =
      ModUtil.createItemEntity(RAW_THORNY_TORTOISE.get().getDefaultInstance(), entity);
    event.getDrops().add(itemEntity);

    return true;
  }

  private static boolean handleEntityDropDefault(final LivingDropsEvent event,
                                                 final LivingEntity entity,
                                                 final Lazy<Optional<EntityType<?>>> entityType,
                                                 final Lazy<Item> itemDrop) {
    if (entityType.get().isEmpty()) {
      return false;
    }

    if (!entity.getType().equals(entityType.get().get())) {
      return false;
    }

    final Entity entityDamageSource = event.getSource().getEntity();

    final int maxDrops = entityDamageSource instanceof Player player
      ? ModUtil.getPlayerLooting(player)
      : 2;

    final ItemEntity itemEntity = ModUtil.createItemEntity(new ItemStack(itemDrop.get(),
      entity.level().getRandom().nextIntBetweenInclusive(1, maxDrops)), entity);
    event.getDrops().add(itemEntity);

    return true;
  }

  @SubscribeEvent
  public void onBlockBreakByPlayer(BlockEvent.BreakEvent event) {
    if (!(event.getLevel() instanceof ServerLevel world)) {
      return;
    }

    BLACK_PUTTY_BUSH.get().ifPresent(block -> {
      final var player = event.getPlayer();
      if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(Tags.Items.SHEARS)) {
        return;
      }

      if (!event.getState().is(block)) {
        return;
      }

      event.setCanceled(true);

      final var pos = event.getPos();

      world.removeBlock(pos, false);
      world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
      Block.popResource(world, pos, new ItemStack(BLACK_PUTTY_FRUIT.get()));
    });
  }

  @SubscribeEvent
  public void onEntityDropItem(final LivingDropsEvent event) {
    final LivingEntity entity = event.getEntity();

    // Down data.
    if (!handleFrostYetiDrops(event, entity)
      && !handleBlueShimmerSquid(event, entity)
      && !handleBeachTerrorBird(event, entity)
      && !handleEntityDropDefault(event, entity, RUSHER, RUSHER_MEAT)
      && !handleEntityDropDefault(event, entity, THORNY_TORTOISE, RAW_THORNY_TORTOISE)
      && !handleEntityDropDefault(event, entity, SCALY_BEAR, SCALY_BEAR_MEAT)
      && !handleEntityDropDefault(event, entity, FLUFFMOUND, FLUFFMOUND_MEAT)
    ) {
      handleEntityDropDefault(event, entity, ARCHNOSED_HORNBEAST, ARCHNOSED_HORNBEAST_MEAT);
    }
  }
}
