package top.sealevel.jerotesdelight;

import club.someoneice.json.JSON;
import club.someoneice.json.node.ArrayNode;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

public final class ModItems {
  /**
   * The mod's items collection. <br />
   * Create and register the items and tabs. <br />
   * These data will used in debug and making data package in dev.
   */
  static Stack<ItemRecorder> ITEMS = new Stack<>();
  /**
   * The tab collection. Create by {@code onRegisterTab}, and clean the ITEMS.
   */
  private static Set<ResourceLocation> RECORDS;

  private ModItems() {
  }

  /**
   * Register our items and foods.
   */
  static void onRegisterItem(final RegisterEvent event) {
    if (event.getRegistryKey() != ForgeRegistries.ITEMS.getRegistryKey()) {
      return;
    }

    final InputStream stream = ModItems.class
      .getClassLoader().getResourceAsStream("assets/jerotesdelight/items.json5");
    final ArrayNode node;
    try (stream) {
      node = JSON.json5.parse(stream, false).asArrayNodeOrEmpty();
    } catch (IOException e) {
      ModMain.LOGGER.error("Failed to load json5 file!", e);
      return;
    }

    if (node.isEmpty()) {
      ModMain.LOGGER.error("Failed to load json5 file!");
      return;
    }

    node.stream()
      .map(JsonNode::asMapNodeOrEmpty)
      .filter(MapNode::isNotEmpty)
      .map(ItemRecorder::of)
      .map(ITEMS::push)
      .forEach(it ->
        event.register(ForgeRegistries.ITEMS.getRegistryKey(), it.registerName(),
          Lazy.of(() -> createItemByRecorder(it))));

    ModBlocks.registerBlockItems(event);

  }

  /**
   * Register our CreativeModeTabs.
   */
  static void onRegisterTab(final RegisterEvent event) {
    if (event.getRegistryKey() != Registries.CREATIVE_MODE_TAB) {
      return;
    }

    if (Objects.isNull(RECORDS)) {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      ITEMS.stream()
        .map(ItemRecorder::registerName)
        .forEach(builder::add);
      RECORDS = builder.build();
    }

    event.register(Registries.CREATIVE_MODE_TAB,
      ResourceLocation.tryBuild(ModMain.ID, "tab"), () ->
        CreativeModeTab.builder()
          .title(Component.translatable("tabs.%s".formatted(ModMain.ID)))
          .icon(() -> ForgeRegistries.ITEMS.getValue(ResourceLocation.tryBuild(ModMain.ID,
            "bright_medley")).getDefaultInstance())
          .displayItems((idp, output) ->
            RECORDS.stream()
              .map(ForgeRegistries.ITEMS::getValue)
              .filter(Objects::nonNull)
              .map(Item::getDefaultInstance)
              .forEach(output::accept))
          .build());
  }

  /**
   * @param recorder food recorder create by json.
   * @return the food create by {@code ItemRecorder }.
   * @see ItemRecorder
   * @see ModItems#onRegisterItem(RegisterEvent)
   * @see ModUtil#createFood(int, float, boolean, int, ResourceLocation, Set)
   */
  private static Item createItemByRecorder(final ItemRecorder recorder) {
    return ModUtil.createFood(recorder.hunger(), recorder.saturation(), recorder.isDrink(),
      recorder.maxStack(), recorder.crafting(), recorder.effects());
  }
}
