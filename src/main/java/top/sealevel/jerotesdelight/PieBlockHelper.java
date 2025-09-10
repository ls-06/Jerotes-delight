package top.sealevel.jerotesdelight;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.block.PieBlock;


public class PieBlockHelper extends PieBlock {

  /**
   * Create the mod pie block. In default, used the RL to create the pie slice item and hold by
   * lazy.<br />
   *
   * @param pieSlice the pie slice item registerName with RL. It will be created and hold by lazy.
   */
  public PieBlockHelper(ResourceLocation pieSlice) {
    super(Properties.copy(Blocks.CAKE), Lazy.of(() -> ForgeRegistries.ITEMS.getValue(pieSlice)));
  }
}
