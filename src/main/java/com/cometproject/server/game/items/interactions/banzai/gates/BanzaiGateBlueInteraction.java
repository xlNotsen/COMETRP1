package com.cometproject.server.game.items.interactions.banzai.gates;

import com.cometproject.server.game.items.interactions.Interactor;
import com.cometproject.server.game.rooms.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.items.RoomItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;
import com.cometproject.server.game.rooms.types.components.games.GameType;

public class BanzaiGateBlueInteraction extends Interactor {
    @Override
    public boolean onWalk(boolean state, RoomItem item, PlayerEntity entity) {
        Room room = entity.getRoom();

        if(room.getGame().getInstance() == null) {
            room.getGame().createNew(GameType.BANZAI);
        }

        if(room.getGame().getInstance().getType() != GameType.BANZAI) {
            return false;
        }

        int id = entity.getPlayer().getId();

        if(room.getGame().getInstance().isTeamed(id)) {
            room.getGame().getInstance().removeFromTeam(id);
        }

        room.getGame().getInstance().getTeams().put(id, GameTeam.BLUE);
        //entity.setGameTeam(GameTeam.BLUE);

        return false;
    }

    @Override
    public boolean onPreWalk(RoomItem item, PlayerEntity avatar) {
        return false;
    }

    @Override
    public boolean onInteract(int request, RoomItem item, PlayerEntity avatar, boolean isWiredTriggered) {
        return false;
    }

    @Override
    public boolean onPlace(RoomItem item, PlayerEntity avatar, Room room) {
        return false;
    }

    @Override
    public boolean onPickup(RoomItem item, PlayerEntity avatar, Room room) {
        return false;
    }

    @Override
    public boolean onTick(RoomItem item, PlayerEntity avatar, int updateState) {
        return false;
    }

    @Override
    public boolean requiresRights() {
        return true;
    }
}