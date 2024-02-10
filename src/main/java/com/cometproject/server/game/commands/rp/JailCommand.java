package com.cometproject.server.game.commands.rp;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.entities.types.PlayerEntity;
import com.cometproject.server.game.rp.jail.JailSendTask;
import com.cometproject.server.game.utilities.DistanceCalculator;
import com.cometproject.server.network.sessions.Session;

import java.util.concurrent.TimeUnit;

/**
 * Created by Matty on 05/04/2014.
 */
public class JailCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            this.sendChat("*Misses*", client);
            return;
        }

        String name = params[0];

        Session c = Comet.getServer().getNetwork().getSessions().getByPlayerUsername(name);

        if (c == null) {
            this.sendChat("*Cannot send to jail as this player does not exist*", client);
            return;
        }

        if (c.getPlayer().getEntity().getRoom().getId() != client.getPlayer().getEntity().getRoom().getId()) {
            this.sendChat("*Uh.. can't arrest..*", client);
            return;
        }

        PlayerEntity entity1 = client.getPlayer().getEntity();
        PlayerEntity entity2 = c.getPlayer().getEntity();

        int dist = DistanceCalculator.calculate(entity1.getPosition().getX(), entity1.getPosition().getY(), entity2.getPosition().getX(), entity2.getPosition().getY());

        if (dist > 2) {
            this.sendChatBroadcast("*Misses*", client);
            return;
        }

        entity2.setIsInTeleporter(true);

        this.sendChatBroadcast("*Sends " + entity2.getUsername() + " to jail*", client);
        this.sendChatBroadcast("*I have been sent to jail for committing an act of crime*", c);

        Comet.getServer().getThreadManagement().executeSchedule(new JailSendTask(this, entity1, entity2), 3, TimeUnit.SECONDS);
    }

    @Override
    public String getPermission() {
        return "jail_command";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.jail.description");
    }
}
