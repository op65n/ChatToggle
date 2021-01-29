package op65n.tech.chattoggle;

import op65n.tech.chattoggle.data.MessageQueue;
import op65n.tech.chattoggle.handle.PacketHandler;
import op65n.tech.chattoggle.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatTogglePlugin extends JavaPlugin {

    private final PacketHandler packetHandler = new PacketHandler(this);
    private final MessageQueue messageQueue = new MessageQueue();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        this.packetHandler.registerPacket();
    }

    public MessageQueue getMessageQueue() {
        return this.messageQueue;
    }
}
