package com.cometproject.server.network.messages.incoming.room.moderation;

import com.cometproject.server.game.rooms.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class KickUserMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        int virtualEntityId = msg.readInt();

        Room room = client.getPlayer().getEntity().getRoom();

        if(room == null) {
            return;
        }

        PlayerEntity playerEntity = room.getEntities().tryGetPlayerEntityNullable(virtualEntityId);

        if (playerEntity == null) {
            return;
        }

        boolean isOwner = client.getPlayer().getId() == room.getData().getOwnerId();
        boolean hasRights = room.getRights().hasRights(client.getPlayer().getId());


        if(isOwner || hasRights) {
            if(room.getData().getOwnerId() == playerEntity.getPlayerId()) {
                return;
            }

            playerEntity.leaveRoom(false, true, true);
        }
    }
}
