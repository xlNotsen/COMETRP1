package com.cometproject.server.game.rooms.types.components.bots.player;

import com.cometproject.server.game.players.components.types.InventoryBot;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.bots.Bot;
import com.cometproject.server.network.messages.outgoing.room.avatar.ShoutMessageComposer;
import com.cometproject.server.utilities.RandomInteger;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerBot extends Bot {
    Logger log = Logger.getLogger("Player Bot (" + this.getName() + ")");
    int tickCount = 0;
    int walkTimer = 0;
    int talkTimer = 0;

    public PlayerBot(int virtualId, ResultSet data, Room room) throws SQLException {
        super(virtualId, data, room);
    }

    public PlayerBot(int virtualId, InventoryBot bot, int x, int y, Room room) {
        super(virtualId, bot, x, y, room);
    }

    @Override
    public void tick() {
        if(walkTimer >= 20) {
            // Walk!
            walkTimer = 0;
        }

        if(talkTimer >= 15) {
            int chance = RandomInteger.getRandom(1, 3);

            if(chance == 1) {
                this.getRoom().getEntities().broadcastMessage(ShoutMessageComposer.compose(this.getId(), (tickCount % 2 == 0) ? "Hi!" : "I | Leon", 0, 1));
            }

            talkTimer = 0;
        }


        tickCount++;
        walkTimer++;
        talkTimer++;
    }
}
