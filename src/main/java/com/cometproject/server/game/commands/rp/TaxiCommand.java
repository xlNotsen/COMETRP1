package com.cometproject.server.game.commands.rp;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.avatars.effects.UserEffect;
import com.cometproject.server.game.rp.taxi.TaxiCommandTask;
import com.cometproject.server.network.sessions.Session;

import java.util.concurrent.TimeUnit;

/**
 * Created by Matty on 04/04/2014.
 */
public class TaxiCommand extends ChatCommand {
    public static int TAXI_COST = 50;

    @Override
    public void execute(Session client, String[] params) {
        if(params.length != 1) {
            return;
        }

        if (client.getPlayer().getEntity().isTaxi()) {
            return;
        }

        int taxiRoomId = 0;

        try {
            taxiRoomId = Integer.parseInt(params[0]);
        } catch (NumberFormatException ex) {
            return;
        }

        if (client.getPlayer().getData().getCredits() < TAXI_COST) {
            this.sendChat("*I don't have enough money, I need at least 50RP's*", client);
            return;
        }

        client.getPlayer().getEntity().setIsInTeleporter(true);
        client.getPlayer().getEntity().setTaxi(true);

        client.getPlayer().getEntity().applyEffect(new UserEffect(65, 0));
        this.sendChat("*Calls for a Taxi to room " + taxiRoomId + "*", client);

        Comet.getServer().getThreadManagement().executeSchedule(new TaxiCommandTask(this, taxiRoomId, client), 4, TimeUnit.SECONDS);
    }

    @Override
    public String getPermission() {
        return "taxi_command";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.taxi.description");
    }
}
