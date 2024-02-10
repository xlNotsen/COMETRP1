package com.cometproject.server.game.commands.staff;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.room.avatar.WisperMessageComposer;
import com.cometproject.server.network.sessions.Session;

public class InvisibleCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        client.send(WisperMessageComposer.compose(client.getPlayer().getId(), Locale.get("command.error.disabled")));

        /*if(client.getPlayer().getEntity().getIsInvisible()) {
            client.getPlayer().getEntity().setIsInvisible(false);
            client.send(WisperMessageComposer.compose(client.getPlayer().getId(), Locale.get("command.invisible.disabled")));
        } else {
            client.getPlayer().getEntity().setIsInvisible(true);
            client.send(WisperMessageComposer.compose(client.getPlayer().getId(), Locale.get("command.invisible.enabled")));
        }*/
    }

    @Override
    public String getPermission() {
        return "invisible_command";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.invisible.description");
    }
}
