package com.pennywis3.wailablacklist.handlers.network;

import com.pennywis3.wailablacklist.handlers.ConfigHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by Pennywis3 on 20.08.2015.
 */
public class ConfigMessage implements IMessage {

        String message;

        public ConfigMessage() {
            message = ConfigHandler.getString();
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            message = ByteBufUtils.readUTF8String(buf);
        }

        @Override
        public void toBytes(ByteBuf buf) {
            ByteBufUtils.writeUTF8String(buf, message);
        }

        public static class Handler implements IMessageHandler<ConfigMessage, IMessage> {
            @Override
            public IMessage onMessage(ConfigMessage message, MessageContext ctx) {
                ConfigHandler.fromString(message.message);
                return null;
            }
        }
}
