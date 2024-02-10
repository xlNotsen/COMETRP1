package com.cometproject.server.network.messages.incoming.room.action;

import com.cometproject.server.game.GameEngine;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.outgoing.room.avatar.ShoutMessageComposer;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class ShoutMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        String message = msg.readString();
        int colour = msg.readInt();

        if(!TalkMessageEvent.isValidColour(colour, client)) {
            colour = 0;
        }

        if(client.getPlayer().getEntity().onChat(message)) {
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(ShoutMessageComposer.compose(client.getPlayer().getEntity().getVirtualId(), message, GameEngine.getRooms().getEmotions().getEmotion(message), colour));
        }
    }
}
