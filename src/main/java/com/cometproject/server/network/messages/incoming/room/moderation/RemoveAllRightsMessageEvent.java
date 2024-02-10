package com.cometproject.server.network.messages.incoming.room.moderation;

import com.cometproject.server.game.rooms.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.outgoing.room.permissions.AccessLevelMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.permissions.RemovePowersMessageComposer;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class RemoveAllRightsMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        Room room = client.getPlayer().getEntity().getRoom();

        if(room == null) {
            return;
        }

        if(room.getData().getOwnerId() != client.getPlayer().getId()) {
            return;
        }

        for(Integer id : room.getRights().getAll()) {
            /*Avatar user = room.getAvatars().get(id);

            if(user != null) {
                user.getPlayer().getSession().send(AccessLevelMessageComposer.compose(0));
            }*/

            PlayerEntity playerEntity = room.getEntities().tryGetPlayerEntityNullable(id);

            if (playerEntity == null) {
                playerEntity.getPlayer().getSession().send(AccessLevelMessageComposer.compose(0));
            }

            // Remove rights from the player id
            client.send(RemovePowersMessageComposer.compose(id, room.getId()));
            room.getRights().removeRights(playerEntity.getPlayerId());
        }
    }
}
