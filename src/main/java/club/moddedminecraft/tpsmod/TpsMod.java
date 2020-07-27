package club.moddedminecraft.tpsmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = TpsMod.MODID, name = TpsMod.NAME, version = TpsMod.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
public class TpsMod
{
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    public static Config config;
    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        logger = event.getModLog();
        ConfigReader reader = new ConfigReader();
        config = reader.readConfig();
        logger.info(config.getApiToken());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ServerTickEventHandler());
        logger.info("Initialized Mod");
    }
}
