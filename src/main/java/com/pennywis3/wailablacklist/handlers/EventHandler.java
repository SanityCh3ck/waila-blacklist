package com.pennywis3.wailablacklist.handlers;

import com.pennywis3.wailablacklist.WAILABlacklist;
import com.pennywis3.wailablacklist.handlers.network.ConfigMessage;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.overlay.RayTracing;
import net.darkhax.wailaevents.event.WailaRenderEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Pennywis3 on 19.08.2015.
 */
public class EventHandler {

    public static EventHandler instance = new EventHandler();

    public static void register(){
        MinecraftForge.EVENT_BUS.register(instance);
        FMLCommonHandler.instance().bus().register(instance);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void preRenderEvent(WailaRenderEvent.Pre event) {
        if (RayTracing.instance().getTarget() == null) return;

        //Dimensions
        if(ConfigHandler.dimList.length > 0) {
            boolean result = ConfigHandler.dimWhitelist;
            for (int i = 0; i < ConfigHandler.dimList.length; i++){
                int curDim = Minecraft.getMinecraft().thePlayer.dimension;
                if(curDim == ConfigHandler.dimList[i]) {
                    result = !result;
                    break;
                }
            }
            event.setCanceled(result);
            if(result) return;
        }

        MovingObjectPosition mop = RayTracing.instance().getTarget();

        //Blocks
        if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            boolean result = ConfigHandler.blockWhitelist;
            Block block = Minecraft.getMinecraft().theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ);
            int meta = Minecraft.getMinecraft().theWorld.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);
            String name = GameData.getBlockRegistry().getNameForObject(block);
            if (ConfigHandler.blockSet.contains(name)) {
                result = !result;
            } else if (ConfigHandler.blockSet.contains(name+":"+meta)) {
                result = !result;
            }
            event.setCanceled(result);
            return;
        }

        //Entities
        if(mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            if (ConfigHandler.entityHideInvisible && mop.entityHit.isInvisible()) {
                event.setCanceled(true);
                return;
            }

            boolean result = ConfigHandler.entityWhitelist;
            String entityString = EntityList.getEntityString(mop.entityHit);
            if(entityString != null && ConfigHandler.entitySet.contains(entityString)){
                result = !result;
            }
            event.setCanceled(result);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        WAILABlacklist.network.sendTo(new ConfigMessage(), (EntityPlayerMP) event.player);
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event){
        ConfigHandler.reset();
    }
}
