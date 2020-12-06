package pl.trollcraft.pvp.help.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import pl.trollcraft.pvp.help.Help;

public class WrapperActionBar extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.CHAT;

    public WrapperActionBar() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperActionBar(PacketContainer packet) {
        super(packet, TYPE);
    }

    public void setText(String message) {
        handle.getBytes().write(0, (byte) 2);
        handle.getChatComponents().write(0, WrappedChatComponent.fromText(Help.color(message)));
    }


}
