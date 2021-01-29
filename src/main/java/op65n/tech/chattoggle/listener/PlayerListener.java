package op65n.tech.chattoggle.listener;

import op65n.tech.chattoggle.ChatTogglePlugin;
import op65n.tech.chattoggle.data.MessageQueue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {

    private final MessageQueue messageQueue;

    public PlayerListener(final ChatTogglePlugin plugin) {
        this.messageQueue = plugin.getMessageQueue();
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.messageQueue.clearUserEntries(event.getPlayer().getUniqueId());
    }

}
