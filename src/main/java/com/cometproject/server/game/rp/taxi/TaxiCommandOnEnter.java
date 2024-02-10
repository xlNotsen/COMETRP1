package com.cometproject.server.game.rp.taxi;

import com.cometproject.server.game.commands.rp.TaxiCommand;
import com.cometproject.server.game.rooms.types.misc.OnPlayerEnterRoom;
import com.cometproject.server.network.sessions.Session;

/**
 * Created by Matty on 04/04/2014.
 */
public class TaxiCommandOnEnter implements OnPlayerEnterRoom {
    private TaxiCommand cmd;
    private Session session;

    public TaxiCommandOnEnter(TaxiCommand cmd, Session ses) {
        this.cmd = cmd;
        this.session = ses;
    }

    @Override
    public void execute() {
        this.cmd.sendChat("*Pays the Taxi driver*", this.session);

        this.session.getPlayer().getData().decreaseCredits(TaxiCommand.TAXI_COST);
        this.session.getPlayer().sendBalance();
    }
}
