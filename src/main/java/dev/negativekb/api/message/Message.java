package dev.negativekb.api.message;

import dev.negativekb.api.util.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Message {

    @Getter
    private final String initial;
    @Getter
    @Setter
    private String message;

    /**
     * Message Constructor (as String)
     *
     * @param msg Message
     */
    public Message(String... msg) {
        String actual = String.join("\n", msg);
        setMessage(actual);
        this.initial = actual;
    }

    /**
     * Replacer
     * <p>
     * Simply replaces object1 with object2.
     * Could be a string, int, double, whatever
     *
     * @param o1 - Object 1
     * @param o2 - Object 2
     * @return - Returns replaced value
     */
    public Message replace(Object o1, Object o2) {
        if (o2 instanceof Integer || o2 instanceof Double || o2 instanceof Long) {
            o2 = Utils.decimalFormat(o2);
        }

        String newMSG = this.message;

        newMSG = newMSG.replaceAll(String.valueOf(o1), String.valueOf(o2));

        setMessage(newMSG);
        return this;
    }

    /**
     * Send Message
     * <p>
     * Sends the message to a CommandSender.
     * Usually a player or console
     *
     * @param sender - Sender
     */
    public void send(CommandSender sender) {
        if (this.message.contains("\n")) {
            String[] msg = getMessage().split("\n");
            for (String s : msg) {
                sender.sendMessage(Utils.color(s));
            }
            this.message = initial;
            return;
        }
        sender.sendMessage(Utils.color(getMessage()));
        setMessage(getInitial());
    }

    /**
     * Send Message to a list of players
     * <p>
     * Simply sends the message to a list of players
     *
     * @param players - List of players
     */
    public void send(List<Player> players) {
        players.forEach(this::send);
    }

    /**
     * Broadcast message
     * <p>
     * Simply broadcasts the message for
     * all to see!
     */
    public void broadcast() {
        if (getMessage().contains("\n")) {
            String[] msg = getMessage().split("\n");
            for (String s : msg) {
                Utils.broadcast(s);
            }
            setMessage(getInitial());
            return;
        }
        Utils.broadcast(getMessage());
        setMessage(getInitial());
    }

}
