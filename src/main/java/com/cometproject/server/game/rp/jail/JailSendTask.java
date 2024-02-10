package com.cometproject.server.game.rp.jail;

import com.cometproject.server.game.GameEngine;
import com.cometproject.server.game.commands.rp.JailCommand;
import com.cometproject.server.game.rooms.entities.types.PlayerEntity;
import com.cometproject.server.network.messages.outgoing.messenger.FollowFriendMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.tasks.CometTask;

/**
 * Created by Matty on 05/04/2014.
 */
public class JailSendTask implements CometTask {
    private JailCommand cmd;

    private PlayerEntity entity1;
    private PlayerEntity entity2;

    public JailSendTask(JailCommand cmd, PlayerEntity entity1, PlayerEntity entity2) {
        this.cmd = cmd;

        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    @Override
    public void run() {
        entity2.setIsInTeleporter(false);
        entity2.getPlayer().getData().setFigure(GameEngine.getRP().getConfig().getJailClothing());
        entity2.getPlayer().getSession().send(UpdateInfoMessageComposer.compose(true, entity2));

        int jailRoom = GameEngine.getRP().getConfig().getJailRoomId();

        if (!GameEngine.getRooms().exists(jailRoom)) {
            return;
        }

        entity2.getPlayer().getStats().setJailTimeExpires((int)(System.currentTimeMillis() / 1000) + 300);

        entity2.getPlayer().getSession().send(FollowFriendMessageComposer.compose(jailRoom));
    }
}
