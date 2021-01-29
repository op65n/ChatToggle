package op65n.tech.chattoggle.handle;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import op65n.tech.chattoggle.ChatTogglePlugin;
import op65n.tech.chattoggle.data.MessageQueue;
import op65n.tech.chattoggle.data.entry.MessageEntry;
import org.bukkit.ChatColor;

import java.util.UUID;

public final class PacketHandler {

    private final ChatTogglePlugin plugin;

    public PacketHandler(final ChatTogglePlugin plugin) {
        this.plugin = plugin;
    }

    public void registerPacket() {
        final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
                    @Override
                    public void onPacketSending(final PacketEvent event) {
                        final UUID identifier = event.getPlayer().getUniqueId();
                        final String eventMessage = validatePacketEvent(event);

                        if (!event.isServerPacket()) return;

                        final MessageQueue queue = PacketHandler.this.plugin.getMessageQueue();
                        final MessageEntry entry = queue.getEntry(identifier, eventMessage);

                        if (entry != null) {
                            event.setCancelled(true);
                            return;
                        }

                        queue.addEntry(identifier, eventMessage);
                    }
                }
        );
    }

    private String validatePacketEvent(final PacketEvent event) {
        final PacketContainer packet = event.getPacket();
        final WrappedChatComponent component = packet.getChatComponents().readSafely(0);
        if (component == null) return null;

        final String json = component.getJson();
        if (json == null) return null;

        final BaseComponent[] baseComponents = ComponentSerializer.parse(json);
        if (baseComponents == null) return null;

        return ChatColor.stripColor(TextComponent.toLegacyText(baseComponents));
    }

}
