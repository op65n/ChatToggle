package op65n.tech.chattoggle.data;

import op65n.tech.chattoggle.data.entry.MessageEntry;

import java.util.*;

public final class MessageQueue {

    private final Map<UUID, List<MessageEntry>> messageQueue = new HashMap<>();

    public void clearUserEntries(final UUID identifier) {
        this.messageQueue.remove(identifier);
    }

    public void addEntry(final UUID identifier, final String message) {
        dumpExpiredEntries(identifier);

        final List<MessageEntry> entries = this.messageQueue.getOrDefault(identifier, new ArrayList<>());
        final MessageEntry messageEntry = new MessageEntry(message);

        entries.add(messageEntry);
        this.messageQueue.put(identifier, entries);
    }

    public MessageEntry getEntry(final UUID identifier, final String message) {
        dumpExpiredEntries(identifier);

        final Optional<MessageEntry> match = messageQueue.getOrDefault(identifier, new ArrayList<>()).stream()
                .filter(entry -> entry.getMessage().equalsIgnoreCase(message))
                .findAny();

        return match.orElse(null);
    }

    private void dumpExpiredEntries(final UUID identifier) {
        final List<MessageEntry> nonExpired = new ArrayList<>();

        this.messageQueue.getOrDefault(identifier, new ArrayList<>()).stream()
                .filter(entry -> !entry.isExpired())
                .forEach(nonExpired::add);

        this.messageQueue.put(identifier, nonExpired);
    }

}
