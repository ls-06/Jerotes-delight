package top.sealevel.jerotesdelight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.List;

public final class ModBlocks {
  private ModBlocks() {
  }

  /**
   * Register the blocks and block-items.
   */
  public static void onRegisterBlock(final RegisterEvent event) {
    if (event.getRegistryKey() != ForgeRegistries.BLOCKS.getRegistryKey()) {
      return;
    }

    pieBlockHandle(event);
    steamedSunCrab(event);
    pinkCactusFlavorlessMelonSoup(event);
  }

  /**
   * Register our pie block.
   */
  private static void pieBlockHandle(final RegisterEvent event) {
    final ResourceLocation SLICE_OF_BRIGHT_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "slice_of_bright_flowers_pie");
    final ResourceLocation SLICE_OF_WONDERLAND_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "slice_of_wonderland_flowers_pie");
    final ResourceLocation SLICE_OF_BITTER_COLD_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "slice_of_bitter_cold_flowers_pie");
    final ResourceLocation SLICE_OF_BLACK_PRETTY_FRUIT_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "slice_of_black_pretty_fruit_pie");

    final ResourceLocation BRIGHT_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "bright_flowers_pie");
    final ResourceLocation WONDERLAND_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "wonderland_flowers_pie");
    final ResourceLocation BITTER_COLD_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "bitter_cold_flowers_pie");
    final ResourceLocation BLACK_PRETTY_FRUIT_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "black_pretty_fruit_pie");

    event.register(ForgeRegistries.BLOCKS.getRegistryKey(), BRIGHT_FLOWERS_PIE, () ->
      new PieBlockHelper(SLICE_OF_BRIGHT_FLOWERS_PIE));
    event.register(ForgeRegistries.BLOCKS.getRegistryKey(), WONDERLAND_FLOWERS_PIE, () ->
      new PieBlockHelper(SLICE_OF_WONDERLAND_FLOWERS_PIE));
    event.register(ForgeRegistries.BLOCKS.getRegistryKey(), BITTER_COLD_FLOWERS_PIE, () ->
      new PieBlockHelper(SLICE_OF_BITTER_COLD_FLOWERS_PIE));
    event.register(ForgeRegistries.BLOCKS.getRegistryKey(), BLACK_PRETTY_FRUIT_PIE, () ->
      new PieBlockHelper(SLICE_OF_BLACK_PRETTY_FRUIT_PIE));
  }

  /**
   * Register the steamed sun crab.
   */
  private static void steamedSunCrab(final RegisterEvent event) {
    final var STEAMED_SUN_CRAB = ResourceLocation.tryBuild(ModMain.ID, "steamed_sun_crab");

    event.register(ForgeRegistries.BLOCKS.getRegistryKey(),
      STEAMED_SUN_CRAB, BlockSteamedSunCrab::new);
  }

  /**
   * Register the pink cactus flavorless melon soup.
   */
  private static void pinkCactusFlavorlessMelonSoup(final RegisterEvent event) {
    final var PINK_CACTUS_FLAVORLESS_MELON_SOUP =
      ResourceLocation.tryBuild(ModMain.ID, "pink_cactus_flavorless_melon_soup");

    event.register(ForgeRegistries.BLOCKS.getRegistryKey(),
      PINK_CACTUS_FLAVORLESS_MELON_SOUP, () ->
        new FeastBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), Lazy.of(() ->
          ForgeRegistries.ITEMS.getValue(ResourceLocation.tryBuild(ModMain.ID,
            "bowl_of_pink_cactus_flavorless_melon_soup"))), true) {
          @Override
          public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
            final List<ItemStack> list = super.getDrops(pState, pParams);
            list.add(Items.BOWL.getDefaultInstance());
            return list;
          }
        }
    );
  }

  /**
   * Register the block items, and push them in ITEMS stack.<br />
   * All register objects must be in time for register, or they will not be register. <br />
   * And create the empty item recorder for push into tabs.
   * <p>DON'T MEGA THESE INTO BLOCKS REGISTER!</p>
   *
   * @see ModUtil#createEmptyRecorder(ResourceLocation)
   */
  static void registerBlockItems(final RegisterEvent event) {
    final ResourceLocation BRIGHT_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "bright_flowers_pie");
    final ResourceLocation WONDERLAND_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "wonderland_flowers_pie");
    final ResourceLocation BITTER_COLD_FLOWERS_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "bitter_cold_flowers_pie");
    final ResourceLocation BLACK_PRETTY_FRUIT_PIE =
      ResourceLocation.tryBuild(ModMain.ID, "black_pretty_fruit_pie");
    final ResourceLocation STEAMED_SUN_CRAB =
      ResourceLocation.tryBuild(ModMain.ID, "steamed_sun_crab");
    final ResourceLocation PINK_CACTUS_FLAVORLESS_MELON_SOUP =
      ResourceLocation.tryBuild(ModMain.ID, "pink_cactus_flavorless_melon_soup");

    event.register(ForgeRegistries.ITEMS.getRegistryKey(), BRIGHT_FLOWERS_PIE, () ->
      new BlockItem(ForgeRegistries.BLOCKS.getValue(BRIGHT_FLOWERS_PIE), new Item.Properties()));
    event.register(ForgeRegistries.ITEMS.getRegistryKey(), WONDERLAND_FLOWERS_PIE, () ->
      new BlockItem(ForgeRegistries.BLOCKS.getValue(WONDERLAND_FLOWERS_PIE), new Item.Properties()));
    event.register(ForgeRegistries.ITEMS.getRegistryKey(), BITTER_COLD_FLOWERS_PIE, () ->
      new BlockItem(ForgeRegistries.BLOCKS.getValue(BITTER_COLD_FLOWERS_PIE), new Item.Properties()));
    event.register(ForgeRegistries.ITEMS.getRegistryKey(), BLACK_PRETTY_FRUIT_PIE, () ->
      new BlockItem(ForgeRegistries.BLOCKS.getValue(BLACK_PRETTY_FRUIT_PIE), new Item.Properties()));

    event.register(ForgeRegistries.ITEMS.getRegistryKey(), STEAMED_SUN_CRAB, () ->
      new BlockItem(ForgeRegistries.BLOCKS.getValue(STEAMED_SUN_CRAB), new Item.Properties()));


    event.register(ForgeRegistries.ITEMS.getRegistryKey(), PINK_CACTUS_FLAVORLESS_MELON_SOUP, () ->
      new BlockItem(ForgeRegistries.BLOCKS.getValue(PINK_CACTUS_FLAVORLESS_MELON_SOUP),
        new Item.Properties()));

    ModItems.ITEMS.push(ModUtil.createEmptyRecorder(BRIGHT_FLOWERS_PIE));
    ModItems.ITEMS.push(ModUtil.createEmptyRecorder(WONDERLAND_FLOWERS_PIE));
    ModItems.ITEMS.push(ModUtil.createEmptyRecorder(BITTER_COLD_FLOWERS_PIE));
    ModItems.ITEMS.push(ModUtil.createEmptyRecorder(BLACK_PRETTY_FRUIT_PIE));
    ModItems.ITEMS.push(ModUtil.createEmptyRecorder(STEAMED_SUN_CRAB));
    ModItems.ITEMS.push(ModUtil.createEmptyRecorder(PINK_CACTUS_FLAVORLESS_MELON_SOUP));
  }
}
