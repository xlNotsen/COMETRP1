package com.cometproject.server.game.commands.user;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.wired.misc.WiredSquare;
import com.cometproject.server.network.messages.outgoing.misc.AdvancedAlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.items.SendFloorItemMessageComposer;
import com.cometproject.server.network.sessions.Session;

public class BuildCommand extends ChatCommand {

    @Override
    public void execute(Session client, String message[]) {
        if(message.length < 1) {
            client.send(AdvancedAlertMessageComposer.compose("Comet Server", "Current build: <b>" + Comet.getBuild() + "</b>"));
        }

        if(!message[0].equals("visualise")) {
            return;
        }

        String returnText = "";

        for(int x = 0; x < client.getPlayer().getEntity().getRoom().getModel().getSizeX(); x++) {
            String line = "";
            for(int y = 0; y < client.getPlayer().getEntity().getRoom().getModel().getSizeY(); y++) {
                if(client.getPlayer().getEntity().getRoom().getWired().isWiredSquare(x, y))
                    line += "__";
                else
                    line += "  ";
            }

            returnText += line + "<br>";
        }

        client.send(AdvancedAlertMessageComposer.compose("Wired Square Visualiser", returnText));
    }

    @Override
    public String getPermission() {
        return "build_command";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.build.description");
    }
}
