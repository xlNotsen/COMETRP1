package com.cometproject.server.game;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.config.CometSettings;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.players.types.PlayerHelper;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.messenger.FollowFriendMessageComposer;
import com.cometproject.server.network.messages.outgoing.misc.AdvancedAlertMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.tasks.CometTask;
import com.cometproject.server.tasks.CometThreadManagement;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameThread implements CometTask {
    private static final boolean MULTITHREADED_CYCLE = false;
    private static Logger log = Logger.getLogger(GameThread.class.getName());

    private CometThreadManagement threadManagement;

    private ScheduledFuture myFuture;

    private boolean active = false;

    public GameThread(CometThreadManagement mgr) {
        this.threadManagement = mgr;

        int interval = Integer.parseInt(Comet.getServer().getConfig().get("comet.game.thread.interval"));
        this.myFuture = mgr.executePeriodic(this, interval, interval, TimeUnit.SECONDS);
        this.active = true;
    }

    int gameCycleCount = 0;
    int statusCycleCount = 0;

    @Override
    public void run() {
        try {
            if(!this.active) {
                return;
            }

            if(this.gameCycleCount >= 180) {
                if (MULTITHREADED_CYCLE) {
                    this.threadManagement.executeOnce(new CometTask() {
                        @Override
                        public void run() {
                            // Cycle the updater in the background
                            try {
                                cycle();
                            } catch (Exception e) {
                                if(e instanceof InterruptedException) {
                                    return;
                                }

                                log.error("Error during game thread", e);
                            }
                        }
                    });
                } else {
                    this.cycle();
                }
            }

            if (this.statusCycleCount >= 12) {
                Connection connection = Comet.getServer().getStorage().getConnections().getConnection();
                connection.setAutoCommit(true);

                connection.prepareStatement("UPDATE server_status SET "
                        + "active_players = " + Comet.getServer().getNetwork().getSessions().getUsersOnlineCount() + ","
                        + "active_rooms = " + GameEngine.getRooms().getActiveRooms().size() + ","
                        + "server_version = '" + Comet.getBuild() + "'").executeUpdate();

                connection.close();

                this.statusCycleCount = -1;
            }

            this.cycleRP();

            statusCycleCount++;
            gameCycleCount++;
        } catch(Exception e) {
            if(e instanceof InterruptedException) {
                return;
            }

            log.error("Error during game thread", e);
        }
    }

    private final void cycle() throws Exception {
        synchronized (GameEngine.getRooms().getActiveRooms()) {
            for(Room room : GameEngine.getRooms().getActiveRooms()) {
                room.getChatlog().cycle();
                room.getRights().cycle();
            }
        }

        if(CometSettings.quartlyCreditsEnabled) {
            for (Session client : Comet.getServer().getNetwork().getSessions().getSessions().values()) {
                if (client.getPlayer() == null || client.getPlayer().getData() == null) {
                    continue;
                }

                int amountCredits = CometSettings.quartlyCreditsAmount;
                client.getPlayer().getData().increaseCredits(amountCredits);
                client.send(AdvancedAlertMessageComposer.compose(Locale.get("game.received.credits.title"), Locale.get("game.received.credits").replace("{$}", amountCredits + "")));
            }
        }

        gameCycleCount = -1;
    }

    private final void cycleRP() {
        for(Session client : Comet.getServer().getNetwork().getSessions().getSessions().values()) {
            if(client.getPlayer() == null || client.getPlayer().getData() == null) {
                continue;
            }

            // Check if player is in room or stuck
            if (client.getPlayer().getEntity() == null) {
                if (PlayerHelper.isJailed(client.getPlayer().getStats())) {
                    client.send(FollowFriendMessageComposer.compose(GameEngine.getRP().getConfig().getJailRoomId()));
                } else {
                    client.send(FollowFriendMessageComposer.compose(client.getPlayer().getSettings().getHomeRoom()));
                }
            }

            // Check if player is no longer jailed but still in jail / wearing jail clothing
            if (!PlayerHelper.isJailed(client.getPlayer().getStats()) && client.getPlayer().getData().getFigure().equals(GameEngine.getRP().getConfig().getJailClothing())) {
                client.getPlayer().getData().setFigure(client.getPlayer().getData().getPreviousFigure());
                client.send(UpdateInfoMessageComposer.compose(true, client.getPlayer().getEntity()));
                client.send(FollowFriendMessageComposer.compose(client.getPlayer().getSettings().getHomeRoom()));
            }
        }
    }

    public void stop() {
        this.active = false;
        this.myFuture.cancel(false);
    }
}
