package top.sealevel.jerotesdelight;

import club.someoneice.json.node.ArrayNode;
import club.someoneice.json.node.JsonNode;
import club.someoneice.json.node.MapNode;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public record ItemRecorder(
  ResourceLocation registerName,
  String zhName,
  String enName,
  int hunger,
  float saturation,
  int maxStack,
  boolean isDrink,
  ResourceLocation crafting,
  Set<Lazy<MobEffectInstance>> effects
) {
  /**
   * @param node the MapNode of the node in items.json5.
   * @return the temp item datas wait for register and make language files.
   */
  public static ItemRecorder of(final MapNode node) {
    final String zhName = node.get("zh_CN").toString();
    final String enName = node.get("en_US").toString();
    final String crafting = node.has("crafting") ? node.get("crafting").toString() : "";
    final ResourceLocation registerName = registerNameHandler(enName);
    final int hunger = Integer.parseInt(node.get("hunger").toString());
    final float saturation = Float.parseFloat(node.get("saturation").toString()) / 10;
    final int maxStack = node.has("maxStack")
      ? Integer.parseInt(node.get("maxStack").toString())
      : 64;
    final boolean isDrink = node.has("isDrink")
      && Boolean.parseBoolean(node.get("isDrink").toString());

    final ArrayNode nodeOfEffects = node.get("effects").asArrayNodeOrEmpty();
    final Set<Lazy<MobEffectInstance>> effects = Sets.newHashSet();

    ItemRecorder itemRecorder = new ItemRecorder(registerName, zhName, enName, hunger, saturation,
      maxStack, isDrink, crafting.isEmpty() ? null : ResourceLocation.tryParse(crafting), effects);
    if (nodeOfEffects.isEmpty()) {
      return itemRecorder;
    }

    nodeOfEffects.stream()
      .map(JsonNode::asMapNodeOrEmpty)
      .filter(MapNode::isNotEmpty)
      .forEach((nodeEffect) -> {
        final ResourceLocation name = ResourceLocation.tryParse(nodeEffect.get("name").toString());
        final double time = Double.parseDouble(nodeEffect.get("time").toString());
        final int amplifier = Integer.parseInt(nodeEffect.get("lv").toString());

        final Lazy<MobEffectInstance> effectInstanceLazy = Lazy.of(() -> {
          if (Objects.isNull(name)) {
            return null;
          }

          final MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(name);
          if (Objects.isNull(effect)) {
            return null;
          }

          return new MobEffectInstance(effect, Mth.floor(time * 60) * 20 + 1, amplifier);
        });
        effects.add(effectInstanceLazy);
      });

    return itemRecorder;
  }

  /**
   * @param name the en_us name.
   * @return the name of ResourceLocation.
   */
  private static ResourceLocation registerNameHandler(final String name) {
    final String finalName = name.replaceAll(" ", "_").toLowerCase(Locale.ROOT);
    return ResourceLocation.fromNamespaceAndPath(ModMain.ID, finalName);
  }
}
