package com.pennywis3.wailablacklist;

import com.pennywis3.wailablacklist.handlers.ConfigHandler;
import com.pennywis3.wailablacklist.handlers.network.ConfigMessage;
import com.pennywis3.wailablacklist.handlers.EventHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Created by Pennywis3 on 19.08.2015.
 */
@Mod (modid = WAILABlacklist.MOD_ID, version = WAILABlacklist.VERSION, name = WAILABlacklist.NAME, dependencies = "required-after:wailaevents")
public class WAILABlacklist {
    public static final String MOD_ID = "waila-blacklist";
    public static final String VERSION = "0.1";
    public static final String NAME = "WAILA Blacklist";

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ConfigHandler.initConfig(event.getSuggestedConfigurationFile());
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
        network.registerMessage(ConfigMessage.Handler.class, ConfigMessage.class, 0, Side.CLIENT);
        EventHandler.register();
    }
}
