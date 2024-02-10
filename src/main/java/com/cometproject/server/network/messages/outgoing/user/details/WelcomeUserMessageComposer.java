package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.server.network.messages.headers.Composers;
import com.cometproject.server.network.messages.types.Composer;

public class WelcomeUserMessageComposer {
    public static Composer compose() {
        Composer msg = new Composer(Composers.WelcomeUserMessageComposer);

        msg.writeInt(0);

        return msg;
    }
}
