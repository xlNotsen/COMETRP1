package com.cometproject.server.network.messages.incoming.room.trading;

import com.cometproject.server.game.players.components.types.InventoryItem;
import com.cometproject.server.game.rooms.types.components.types.Trade;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class CancelOfferMessageEvent implements IEvent {
    public void handle(Session client, Event msg) {
        int itemId = msg.readInt();
        InventoryItem item = client.getPlayer().getInventory().getFloorItem(itemId);

        if(item == null) {
            item = client.getPlayer().getInventory().getWallItem(itemId);
        }

        Trade trade = client.getPlayer().getEntity().getRoom().getTrade().get(client.getPlayer().getEntity());
        trade.removeItem(trade.getUserNumber(client.getPlayer().getEntity()), item);
    }
}
