package op65n.tech.chattoggle.data.entry;

import java.util.UUID;

public final class MessageEntry {

    private final UUID messageID = UUID.randomUUID();
    private final String message;
    private final long timestamp;

    public MessageEntry(final String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return this.message;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public UUID getMessageID() {
        return this.messageID;
    }

    public boolean isExpired() {
        return this.timestamp + 30000 < System.currentTimeMillis();
    }

}
