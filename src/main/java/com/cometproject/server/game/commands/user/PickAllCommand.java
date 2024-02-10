package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.items.FloorItem;
import com.cometproject.server.game.rooms.items.WallItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.sessions.Session;

public class PickAllCommand extends ChatCommand {
    @Override
    public void execute(Session client, String message[]) {
        Room room = client.getPlayer().getEntity().getRoom();

        if(room == null || !room.getData().getOwner().equals(client.getPlayer().getData().getUsername())) {
            return;
        }

        for(FloorItem item : room.getItems().getFloorItems()) {
            room.getItems().removeItem(item, client);
        }

        for(WallItem item : room.getItems().getWallItems()) {
            room.getItems().removeItem(item, client);
        }
    }

    @Override
    public String getPermission() {
        return "pickall_command";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.pickall.description");
    }
}
