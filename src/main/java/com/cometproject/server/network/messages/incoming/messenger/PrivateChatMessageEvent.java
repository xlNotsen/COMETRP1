package com.cometproject.server.network.messages.incoming.messenger;

import com.cometproject.server.game.players.components.types.MessengerFriend;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.outgoing.messenger.InstantChatMessageComposer;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class PrivateChatMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        int userId = msg.readInt();
        String message = msg.readString();

        if(userId == -1) { // TODO: Staff chat!

            return;
        }

        MessengerFriend friend = client.getPlayer().getMessenger().getFriendById(userId);

        if(friend == null) {
            return;
        }

        friend.updateClient();
        Session friendClient = friend.getClient();

        if(friendClient == null) {
            return;
        }

        friendClient.send(InstantChatMessageComposer.compose(message, client.getPlayer().getId()));
    }
}
