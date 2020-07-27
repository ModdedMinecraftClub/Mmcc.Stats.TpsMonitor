package club.moddedminecraft.tpsmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("tpsmod")
public class TpsMod
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static Config CONFIG;

    public TpsMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ConfigReader reader = new ConfigReader();
        try {
            CONFIG = reader.readConfig();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        MinecraftForge.EVENT_BUS.register(new ServerTickEventHandler());
        LOGGER.info("Initialized Mod");
    }
}
