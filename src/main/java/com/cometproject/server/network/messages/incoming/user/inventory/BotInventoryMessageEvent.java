package com.cometproject.server.network.messages.incoming.user.inventory;

import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class BotInventoryMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        client.send(BotInventoryMessageComposer.compose(client.getPlayer().getBots().getBots()));
    }
}
