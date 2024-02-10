package com.cometproject.server.game.rp.jail;

import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.rooms.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.misc.OnPlayerEnterRoom;
import com.cometproject.server.network.messages.outgoing.room.avatar.WisperMessageComposer;

/**
 * Created by Matty on 05/04/2014.
 */
public class JailOnEnterRoom implements OnPlayerEnterRoom {
    private Player player;

    public JailOnEnterRoom(Player entity) {
        this.player = entity;
    }

    @Override
    public void execute() {
        //this.player.getSession().send(WisperMessageComposer.compose(player.getEntity().getVirtualId(), "*I have been sentenced to jail, remaining jail time: Unknown*"));
    }
}
