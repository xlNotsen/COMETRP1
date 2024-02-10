package com.cometproject.server.game.players.types;

/**
 * Created by Matty on 05/04/2014.
 */
public class PlayerHelper {

    public static boolean isJailed(PlayerStatistics stats) {
        int jailTime = stats.getJailTimeExpires();

        long currentUnix = System.currentTimeMillis() / 1000;

        if (jailTime < currentUnix) {
            return false;
        }

        return true;
    }
}
