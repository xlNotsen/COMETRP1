package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.network.messages.headers.Composers;
import com.cometproject.server.network.messages.types.Composer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UserInfoMessageComposer {
    public static Composer compose(Player player) {
        Composer msg = new Composer(Composers.UserInfoMessageComposer);

        msg.writeInt(player.getId());
        msg.writeString(player.getData().getUsername());
        msg.writeString(player.getData().getFigure());
        msg.writeString(player.getData().getGender().toUpperCase());
        msg.writeString(player.getData().getMotto());
        msg.writeString(player.getData().getUsername().toLowerCase());
        msg.writeBoolean(true);

        msg.writeInt(8); // ??? (8)
        msg.writeInt(player.getStats().getDailyRespects()); // daily respects!
        msg.writeInt(1); // (3) pet respects I guess

        msg.writeBoolean(true);
        msg.writeString(getDate(player.getData().getLastVisit()));

        msg.writeBoolean(false);
        msg.writeBoolean(false);
        return msg;
    }

    private static String getDate(int ts) {
        Date d = new Date(ts / 1000l);
        SimpleDateFormat df = new SimpleDateFormat("MMM d 'at' HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = df.format(d);

        return formattedDate;
    }
}
