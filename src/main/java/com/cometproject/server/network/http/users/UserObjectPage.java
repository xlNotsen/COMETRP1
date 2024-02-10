package com.cometproject.server.network.http.users;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.data.PlayerLoader;
import com.cometproject.server.network.http.ManagementPage;
import com.cometproject.server.network.messages.outgoing.misc.AdvancedAlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class UserObjectPage extends ManagementPage {
    @Override
    public boolean handle(HttpExchange e, String uri) {
        /*for(Session c : Comet.getServer().getNetwork().getSessions().getSessions().values()) {
            c.send(AdvancedAlertMessageComposer.compose("Message From Hotel Management", uri));
        }*/

        String[] parts = uri.split("/");

        if(parts.length < 2) {
            return false;
        }

        int userId = Integer.parseInt(uri.split("/")[parts.length -1]);
        PlayerData data = PlayerLoader.loadDataById(userId);

        if(data == null) {
            Comet.getServer().getNetwork().getManagement().sendResponse("Invalid parameters (expecting integer)", e);
            return false;
        }

        Comet.getServer().getNetwork().getManagement().sendResponse(new Gson().toJson(data), e);

        return true;
    }
}
