package com.cometproject.server.network.messages.incoming.user.wardrobe;

import com.cometproject.server.game.GameEngine;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.WisperMessageComposer;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class ChangeLooksMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        String gender = msg.readString();
        String figure = msg.readString();

        // Prevent using the jail clothing
        if (figure.equals(GameEngine.getRP().getConfig().getJailClothing())) {
            return;
        }

        if (client.getPlayer().getEntity() == null) {
            return;
        }

        if (!GameEngine.getRP().getClothesStoreManagement().contains(client.getPlayer().getEntity().getRoom().getId())) {
            client.send(WisperMessageComposer.compose(client.getPlayer().getEntity().getVirtualId(), "You must be in a clothes store to change your outfit!"));
            return;
        }

        client.getPlayer().getData().setGender(gender);
        client.getPlayer().getData().setFigure(figure);
        client.getPlayer().getData().save();

        client.getPlayer().getEntity().unIdle();

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(UpdateInfoMessageComposer.compose(client.getPlayer().getEntity()));
        client.send(UpdateInfoMessageComposer.compose(true, client.getPlayer().getEntity()));
    }
}
