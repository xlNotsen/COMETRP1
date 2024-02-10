package com.cometproject.server.game.rp.taxi;

import com.cometproject.server.game.GameEngine;
import com.cometproject.server.game.commands.rp.TaxiCommand;
import com.cometproject.server.game.rooms.avatars.effects.UserEffect;
import com.cometproject.server.network.messages.outgoing.messenger.FollowFriendMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.tasks.CometTask;

/**
 * Created by Matty on 04/04/2014.
 */
public class TaxiCommandTask implements CometTask {
    private TaxiCommand cmd;
    private Session session;

    private int taxiRoomId;

    public TaxiCommandTask(TaxiCommand cmd, int taxiRoomId, Session session) {
        this.cmd = cmd;
        this.session = session;

        this.taxiRoomId = taxiRoomId;
    }

    @Override
    public void run() {
        this.session.getPlayer().getEntity().setIsInTeleporter(false);
        this.session.getPlayer().getEntity().setTaxi(false);

        if (!GameEngine.getRP().getTaxiRoomManagement().exists(this.taxiRoomId)) {
            this.cmd.sendChat("*I don't know where that room is* .. *hangs up the phone*", this.session);
            this.session.getPlayer().getEntity().applyEffect(new UserEffect(0, 0));
        }

        int roomId = GameEngine.getRP().getTaxiRoomManagement().get(this.taxiRoomId);

        if (!GameEngine.getRooms().exists(roomId)) {
            this.cmd.sendChat("*I don't know where that room is* .. *hangs up the phone*", this.session);
            this.session.getPlayer().getEntity().applyEffect(new UserEffect(0, 0));
            return;
        }

        if (this.session.getPlayer().getEntity() != null && this.session.getPlayer().getEntity().getRoom().getId() == roomId) {
            this.cmd.sendChat("*I am already here* .. *hangs up the phone*", this.session);
            this.session.getPlayer().getEntity().applyEffect(new UserEffect(0, 0));
            return;
        }

        this.session.getPlayer().getOnEnterRoom().add(new TaxiCommandOnEnter(this.cmd, this.session));
        this.session.send(FollowFriendMessageComposer.compose(roomId));
    }
}
