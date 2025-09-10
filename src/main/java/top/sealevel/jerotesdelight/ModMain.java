package top.sealevel.jerotesdelight;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModMain.ID)
public class ModMain {
  public static final String ID = "jerotesdelight";

  public static final Logger LOGGER = LogManager.getLogger();

  /**
   * The mod entry.
   */
  public ModMain(FMLJavaModLoadingContext context) {
    var bus = context.getModEventBus();
    bus.register(this);
    MinecraftForge.EVENT_BUS.register(ModEvent.INSTANCE);
  }

  /**
   * Call when {@code RegisterEvent } is going on. We will register our things in mod.
   */
  @SubscribeEvent
  public void registerEvent(RegisterEvent event) {
    ModItems.onRegisterItem(event);
    ModItems.onRegisterTab(event);
    ModBlocks.onRegisterBlock(event);
  }
}
